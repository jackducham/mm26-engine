package mech.mania.engine

import com.google.protobuf.InvalidProtocolBufferException
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mech.mania.engine.domain.model.CharacterProtos
import mech.mania.engine.domain.model.InfraProtos.InfraPlayer
import mech.mania.engine.domain.model.InfraProtos.InfraStatus
import mech.mania.engine.domain.model.PlayerProtos.PlayerTurn
import mech.mania.engine.domain.model.VisualizerProtos
import mech.mania.engine.entrypoints.Main
import mech.mania.engine.service_layer.InfraRESTHandler
import mech.mania.engine.service_layer.VisualizerWebSocket
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.net.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.logging.Logger
import kotlin.test.assertTrue
import kotlin.test.fail


/*
 * Follow this blog post:
 * https://medium.com/@MelvinBlokhuijzen/spring-websocket-endpoints-integration-testing-180357b4f24c
 */

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [InfraRESTHandler::class, VisualizerWebSocket::class])
class ServerIntegrationTests {

    /** Port to launch the Game server on */
    private val infraPort = Config.getProperty("infraPort")  // match infraPort from config.properties
    private val visualizerPort = Config.getProperty("visualizerPort") // match visualizerPort from config.properties

    /** URL that visualizer will connect to */
    private var visualizerUrl: String = "ws://localhost:$visualizerPort/visualizer"

    /** URL that infra will send new/reconnect player messages to */
    private var infraNewUrl: String = "http://localhost:$infraPort/infra/player/new"
    private var infraReconnectUrl: String = "http://localhost:$infraPort/infra/player/reconnect"

    private var logger = Logger.getLogger(ServerIntegrationTests::class.toString())


    /**
     * Set up the testing environment by initializing variables and starting the game server.
     */
    @Before
    fun setup() {
        // start game server with AWS turned off (without --enableInfra flag)
        //  and with default ports (from config.properties)
        val args: Array<String> = arrayOf("--infraPort", infraPort, "--visualizerPort", visualizerPort)

        // launch the actual game in another thread
        // so the test doesn't wait for the server to close before starting
        GlobalScope.launch {
            Main.main(args)
        }

        Thread.sleep(8000)
    }

    /**
     * Clean up afterwards by sending a HTTP GET request to /infra/endgame.
     */
    @After
    fun cleanup() {
        // end game server - send HTTP request to server (password not needed because infra is not enabled)
        // https://stackoverflow.com/questions/46177133/http-request-in-kotlin
        val url = URL("http://localhost:$infraPort/infra/endgame?password=none")
        try {
            val bytes = url.readBytes()
            val statusObj = InfraStatus.parseFrom(bytes)
            logger.info("Response upon sending endgame signal: ${statusObj.message}")
        } catch (e: Exception) {
            // if the server has already closed, then ignore
            logger.info("Exception in closing the server: ${e.message}")
        }

        // Wait for server to truly shut down
        Thread.sleep(8000);
    }

    /**
     * Helper function that creates player servers with random names + ip addresses, sends POST
     * requests to the game to add those players to the game.
     */
    private fun connectNPlayers(n: Int, f: (turn: PlayerTurn) -> CharacterProtos.CharacterDecision,
                                onReceive: (turn: PlayerTurn) -> Unit,
                                onSend: (decision: CharacterProtos.CharacterDecision) -> Unit) {
        val playerNames: ArrayList<String> = ArrayList()
        val playerAddrs: ArrayList<String> = ArrayList()

        for (i in 0 until n) {
            var validPort = false
            while (!validPort) {
                try {
                    // find a free port
                    var socket: ServerSocket?
                    try {
                        socket = ServerSocket(0)
                        socket.close()
                    } catch (e: Exception) {
                        logger.warning("No more free ports found: " + e.message)
                        return
                    }

                    val randomPort: Int = socket.localPort

                    HttpServer.create(InetSocketAddress(randomPort), 0).apply {
                        createContext("/server") { exchange: HttpExchange ->
                            // read in input from server
                            // once the turn is parsed, use that turn to call a passed in function
                            val turn = PlayerTurn.parseFrom(exchange.requestBody)
                            onReceive(turn)

                            // calculate what to do with turn
                            val decision: CharacterProtos.CharacterDecision = f(turn)
                            val size: Long = decision.toByteArray().size.toLong()

                            // send back response
                            exchange.sendResponseHeaders(200, size)
                            decision.writeTo(exchange.responseBody)
                            onSend(decision)
                        }
                        start()
                    }
                    validPort = true

                    val playerName = java.util.UUID.randomUUID().toString()
                    val playerAddr = "http://localhost:$randomPort/server"
                    logger.fine("Creating player \"$playerName\" with IP address $playerAddr")

                    playerNames.add(playerName)
                    playerAddrs.add(playerAddr)

                } catch (e: Exception) {
                    // invalid port
                    validPort = false
                }
            }
        }

        for (i in 0 until n) {
            with (URL(infraNewUrl).openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/octet-stream")

                // this isn't working?
                InfraPlayer.newBuilder()
                        .setPlayerIp(playerAddrs[i])
                        .setPlayerName(playerNames[i])
                        .build()
                        .writeTo(outputStream)
                connect()

                outputStream.flush()
                outputStream.close()

                InfraStatus.parseFrom(inputStream.readBytes())
                inputStream.close()
                disconnect()
            }
        }
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun testReceiveSendPlayerDecisions() {
        val players = 400
        val turns = 20

        val timePerTurn = Integer.parseInt(Config.getProperty("millisBetweenTurns"))

        // wait for an actual object to end the test
        val latch = CountDownLatch(turns * players)

        connectNPlayers(players, {
            CharacterProtos.CharacterDecision.newBuilder()
                    .setDecisionType(CharacterProtos.DecisionType.ATTACK)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        try {
            val result: Boolean = latch.await((turns * timePerTurn).toLong(), TimeUnit.MILLISECONDS)
            assertTrue(result, "Test failed: latch final value: ${latch.count}; perhaps the number of players could be lowered?")
        } catch (e: NullPointerException) {
            fail("Test failed with exception: $e")
        }
    }

    /**
     * Helper function which creates a visualizer instance
     * @param duration: The number of turns (GameChanges) this visualizer should process
     * @param onVisualizerInitial: A function to call on receipt of a VisualizerInitial
     * @param onVisualizerTurn: A function to call on receipt of a VisualizerTurn
     */
    fun createVisualizer(duration: Int,
                         onVisualizerInitial: (visualizerInitial: VisualizerProtos.VisualizerInitial) -> Unit,
                         onVisualizerTurn: (visualizerTurn: VisualizerProtos.VisualizerTurn) -> Unit) {
        // Create WebSocket client
        val client = HttpClient {
            install(WebSockets)
        }
        GlobalScope.launch {
            client.ws(
                    method = HttpMethod.Get,
                    host = "localhost",
                    port = Integer.parseInt(visualizerPort),
                    path = "/visualizer"
            ) {
                // Receive first frame
                when (val frame = incoming.receive()) {
                    is Frame.Binary -> {
                        try {
                            val visualizerInitial = VisualizerProtos.VisualizerInitial.parseFrom(frame.readBytes())
                            //logger.info("Received GameState for turn " + gameState.stateId)
                            onVisualizerInitial(visualizerInitial)
                        }
                        catch(e: InvalidProtocolBufferException){
                            fail("Expected VisualizerInitial but encountered exception: $e")
                        }
                    }
                }

                // Receive subsequent frames
                repeat(duration) {
                    when (val frame = incoming.receive()) {
                        is Frame.Binary -> {
                            try{
                                val visualizerTurn = VisualizerProtos.VisualizerTurn.parseFrom(frame.readBytes())
//                                logger.info("Received GameChange with " +
//                                        gameChange.characterStatChangesCount + " changes")
                                onVisualizerTurn(visualizerTurn)
                            }
                            catch(e: InvalidProtocolBufferException){
                                fail("Expected VisualizerTurn but encountered exception: $e")
                            }
                        }
                    }
                }
            }
        }

    }

    @Test
    fun testBasicVisualizerConnection(){
        val timePerTurn = Integer.parseInt(Config.getProperty("millisBetweenTurns")).toLong()
        val turns = 1
        val latch = CountDownLatch(turns)

        // Create WebSocket client
        createVisualizer(turns, {}, {latch.countDown()})

        // Wait for 1 extra turn in case connection happens between turns
        val result: Boolean = latch.await((turns+1) * timePerTurn, TimeUnit.MILLISECONDS)
        assertTrue(result, "Test failed: latch final value: ${latch.count}; If value is 1, try re-running test.")
    }

    @Test
    fun testMultipleVisualizerConnections(){
        val timePerTurn = Integer.parseInt(Config.getProperty("millisBetweenTurns")).toLong()
        val turns = 10
        val visualizers = 100
        val latch = CountDownLatch(turns * visualizers)

        // Create WebSocket client
        for(visualizer in 1..visualizers) {
            createVisualizer(turns, {}, { latch.countDown() })
        }

        // Wait for 1 extra turn in case connection happens between turns
        val result: Boolean = latch.await((turns + 1) * timePerTurn, TimeUnit.MILLISECONDS)
        assertTrue(result, "Test failed: latch final value: ${latch.count}; If value is $visualizers, try re-running test.")
    }

    @Test
    fun testMultipleVisualizerConnectionsWithPlayers(){
        val timePerTurn = Integer.parseInt(Config.getProperty("millisBetweenTurns")).toLong()
        val turns = 10
        val players = 100
        val visualizers = 100
        val latch = CountDownLatch(turns * (visualizers + players))

        connectNPlayers(players, {
            CharacterProtos.CharacterDecision.newBuilder()
                    .setDecisionType(CharacterProtos.DecisionType.ATTACK)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        // Create WebSocket client
        for(visualizer in 1..visualizers) {
            createVisualizer(turns, {}, { latch.countDown() })
        }

        // Wait for 1 extra turn in case connection happens between turns
        val result: Boolean = latch.await((turns + 1) * timePerTurn, TimeUnit.MILLISECONDS)
        assertTrue(result, "Test failed: latch final value: ${latch.count}; " +
                "If value is ${visualizers + players}, try re-running test.")
    }
}