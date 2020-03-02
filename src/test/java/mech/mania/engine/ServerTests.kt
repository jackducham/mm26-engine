package mech.mania.engine

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mech.mania.engine.server.communication.infra.model.InfraProtos
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.io.DataOutputStream
import java.net.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.logging.Logger


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

    private var LOGGER = Logger.getLogger("ServerTests")

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
            val statusObj = InfraProtos.InfraStatus.parseFrom(bytes)
            LOGGER.info("Response upon sending endgame signal: ${statusObj.message}")
            Thread.sleep(1000)
        } catch (e: Exception) {
            // if the server has already closed, then ignore
            LOGGER.info("Server has already closed")
        }
    }

    /**
     * Helper function that creates player servers with random names + ip addresses, sends POST
     * requests to the game to add those players to the game.
     */
    private fun connectNPlayers(n: Int, f: (turn: PlayerTurn) -> PlayerDecision) {
        val playerNames: ArrayList<String> = ArrayList()
        val playerAddrs: ArrayList<String> = ArrayList()

        for (i in 0.until(n)) {
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
            LOGGER.info("Attempting to connect on port $randomPort")

            val server = HttpServer.create(InetSocketAddress(randomPort), 0).apply {
                createContext("/server") { exchange: HttpExchange ->
                    // read in input from server
                    // once the turn is parsed, use that turn to call a passed in function
                    val turn = PlayerTurn.parseFrom(exchange.requestBody.readAllBytes())

                    // calculate what to do with turn
                    val decision = f(turn)

                    // send back response
                    val os = exchange.responseBody
                    os.write(decision.toByteArray())
                    os.close()
                }
                start()
            }

            val playerName = java.util.UUID.randomUUID().toString()
            val playerAddr = "${server.address.address}".substring(1) + ":" + server.address.port
            LOGGER.info("Creating player \"$playerName\" with IP address $playerAddr")

            playerNames.add(playerName)
            playerAddrs.add(playerAddr)
        }

        for (i in 0.until(n)) {
            val con = URL(INFRA_NEW_URL).openConnection() as HttpURLConnection

            // optional default is GET
            con.requestMethod = "POST"
            con.doOutput = true

            val bytes = InfraProtos.InfraPlayer.newBuilder()
                    .setPlayerIp(playerAddrs[i])
                    .setPlayerName(playerNames[i])
                    .build()
                    .toByteArray()

            con.outputStream.write(bytes)
            con.outputStream.flush()
            con.outputStream.close()
            con.connectTimeout = 1000
            con.connect()
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

        connectNPlayers(5) {
            completableFuture.complete(true)
            PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }

        assert(completableFuture.get(20, TimeUnit.SECONDS))
    }
}