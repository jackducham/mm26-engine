package mech.mania.engine

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn
import mech.mania.engine.server.communication.infra.model.InfraProtos.InfraStatus
import mech.mania.engine.server.communication.infra.model.InfraProtos.InfraPlayer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.io.*
import java.net.*
import java.util.concurrent.*
import java.util.logging.Logger
import kotlin.test.assertTrue


/*
 * Follow this blog post:
 * https://medium.com/@MelvinBlokhuijzen/spring-websocket-endpoints-integration-testing-180357b4f24c
 */

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTests {

    /** Port to launch the Game server on */
    private val port = 8080

    /** URL that visualizer will connect to */
    private var VISUALIZER_URL: String = "ws://localhost:$port/visualizer"

    /** URL that infra will send new/reconnect player messages to */
    private var INFRA_NEW_URL: String = "http://localhost:$port/infra/player/new"
    private var INFRA_RECONNECT_URL: String = "http://localhost:$port/infra/player/reconnect"

    private var LOGGER = Logger.getLogger(ServerTests::class.toString())


    /**
     * Set up the testing environment by initializing variables and starting the game server.
     */
    @Before
    fun setup() {
        // start game server
        val args: Array<String> = arrayOf("$port")
        Main.setup(args)

        // launch the actual game in another thread
        // so the test doesn't wait for the server to close before starting
        GlobalScope.launch {
            Main.runGame()
        }
    }

    /**
     * Clean up afterwards by sending a HTTP GET request to /infra/endgame.
     */
    @After
    fun cleanup() {
        // end game server - send HTTP request to server
        // https://stackoverflow.com/questions/46177133/http-request-in-kotlin
        val url = URL("http://localhost:$port/infra/endgame")
        try {
            val bytes = url.readBytes()
            val statusObj = InfraStatus.parseFrom(bytes)
            LOGGER.info("Response upon sending endgame signal: ${statusObj.message}")
            Thread.sleep(5000)
        } catch (e: Exception) {
            // if the server has already closed, then ignore
            LOGGER.info("Server has already closed")
        }
        Main.resetState()
    }

    /**
     * Helper function that creates player servers with random names + ip addresses, sends POST
     * requests to the game to add those players to the game.
     */
    private fun connectNPlayers(n: Int, f: (turn: PlayerTurn) -> PlayerDecision,
                                onReceive: (turn: PlayerTurn) -> Unit,
                                onSend: (decision: PlayerDecision) -> Unit) {
        val playerNames: ArrayList<String> = ArrayList()
        val playerAddrs: ArrayList<String> = ArrayList()

        for (i in 0 until n) {
            // find a free port
            var socket: ServerSocket?
            try {
                socket = ServerSocket(0)
                socket.close()
            } catch (e: Exception) {
                LOGGER.warning("No more free ports found: " + e.message)
                return
            }

            val randomPort: Int = socket.localPort

            HttpServer.create(InetSocketAddress(randomPort), 0).apply {
                createContext("/server") { exchange: HttpExchange ->
                    exchange.responseHeaders["Content-Type"] = "application/octet-stream"

                    // read in input from server
                    // once the turn is parsed, use that turn to call a passed in function
                    val turn = PlayerTurn.parseFrom(exchange.requestBody)
                    onReceive(turn)

                    // calculate what to do with turn
                    val decision: PlayerDecision = f(turn)
                    val size: Long = decision.toByteArray().size.toLong()

                    // send back response
                    exchange.sendResponseHeaders(200, size)
                    decision.writeTo(exchange.responseBody)
                    onSend(decision)
                }
                start()
            }

            val playerName = java.util.UUID.randomUUID().toString()
            val playerAddr = "http://localhost:$randomPort/server"
            LOGGER.fine("Creating player \"$playerName\" with IP address $playerAddr")

            playerNames.add(playerName)
            playerAddrs.add(playerAddr)
        }

        for (i in 0 until n) {
            with (URL(INFRA_NEW_URL).openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/octet-stream")

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
    fun canReceivePlayerTurn() {
        // wait for an actual object to end the test
        val completableFuture: CompletableFuture<Boolean> = CompletableFuture()

        connectNPlayers(1, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            completableFuture.complete(true)
        })

        assertTrue(completableFuture.get(30, TimeUnit.SECONDS), "CompletableFuture final value: ${completableFuture.get()}")
    }


    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceiveMultiplePlayerTurns() {
        // wait for an actual object to end the test
        val latch = CountDownLatch(5)

        connectNPlayers(5, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        assertTrue(latch.await(30, TimeUnit.SECONDS), "Latch final value: ${latch.count}")
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceiveMultipleTurns() {
        // wait for an actual object to end the test
        val latch = CountDownLatch(5)

        connectNPlayers(1, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        assertTrue(latch.await(30, TimeUnit.SECONDS), "Latch final value: ${latch.count}")
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceiveMultiplePlayers() {
        // wait for an actual object to end the test
        val latch = CountDownLatch(25)

        connectNPlayers(5, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        assertTrue(latch.await(30, TimeUnit.SECONDS), "Latch final value: ${latch.count}")
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceiveManyPlayers() {
        // wait for an actual object to end the test
        val latch = CountDownLatch(5000)

        connectNPlayers(1000, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        assertTrue(latch.await(30, TimeUnit.SECONDS), "Latch final value: ${latch.count}")
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceiveManyPlayersLongTime() {
        // wait for an actual object to end the test
        val latch = CountDownLatch(5000)

        connectNPlayers(100, {
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }, {
            // pass
        }, {
            latch.countDown()
        })

        assertTrue(latch.await(100, TimeUnit.SECONDS), "Latch final value: ${latch.count}")
    }
}