package mech.mania.engine

import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import java.net.URISyntaxException
import java.net.URL
import java.util.concurrent.*

/*
 * Follow this blog post:
 * https://medium.com/@MelvinBlokhuijzen/spring-websocket-endpoints-integration-testing-180357b4f24c
 */

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTests {

    /** Port to launch the Game server on */
    private val port = 8080

    /** URL that player connect to */
    private var PLAYER_URL: String = "ws://localhost:$port/player"

    /** URL that visualizer will connect to */
    private var VISUALIZER_URL: String = "ws://localhost:$port/visualizer"

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
     * Clean up afterwards by sending a HTTP GET request to /api/v1/infra/endgame.
     */
    @After
    fun cleanup() {
        // end game server - send HTTP request to server
        // https://stackoverflow.com/questions/46177133/http-request-in-kotlin
        val url = URL("http://localhost:$port/api/v1/infra/endgame")
        try {
            println("Response upon sending endgame signal: ${url.readText()}")
            Thread.sleep(1000)
        } catch (e: Exception) {
            // if the server has already closed, then ignore
            println("Server has already closed.")
        }
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceivePlayerTurn() {
        // wait for an actual object to end the test
        val completableFuture: CompletableFuture<PlayerTurn> = CompletableFuture()

        StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                val playerTurn = PlayerTurn.parseFrom(message.payload)
                completableFuture.complete(playerTurn)
            }
        }, PLAYER_URL)

        val playerTurn: PlayerTurn = completableFuture.get(10, TimeUnit.SECONDS)
        assertNotNull(playerTurn)
    }

    /**
     * Test to see if the game state can be updated
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canUpdateGameState() {
        // wait for an actual object to end the test
        val countDownLatch = CountDownLatch(1)

        StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                val playerTurn = PlayerTurn.parseFrom(message.payload)

                // once the game understands that we are in the game, then the test is over
                if (playerTurn.playerName == "Joe") {
                    countDownLatch.countDown()
                } else {
                    val playerDecision = PlayerDecision.newBuilder()
                            .setPlayerName("Joe")
                            .setIncrement(1)
                            .build()

                    session.sendMessage(BinaryMessage(playerDecision.toByteArray()))
                }
            }
        }, PLAYER_URL)

        assert(countDownLatch.await(10, TimeUnit.SECONDS))
    }

//    /**
//     * Test to see if the game actually ends
//     */
//    @Test
//    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
//    fun canGameEnd() {
//        // wait until afterConnectionClosed to countDown to finish the test
//        val countDownLatch = CountDownLatch(1)
//
//        StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
//            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
//                val playerTurn = PlayerTurnProtos.PlayerTurn.parseFrom(message.payload)
//                val playerDecision = PlayerDecisionProtos.PlayerDecision.newBuilder()
//                        .setTurn(playerTurn.turn)
//                        .setPlayerName("Joe")
//                        .setIncrement(1)
//                        .build()
//
//                session.sendMessage(BinaryMessage(playerDecision.toByteArray()))
//            }
//
//            override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
//                countDownLatch.countDown()
//            }
//        }, URL)
//
//        assert(countDownLatch.await(10, TimeUnit.SECONDS))
//    }

    /**
     * Test to see if the game actually ends with multiple players sending stuff every turn
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canGameEndMultiplePlayers() {
        val n = 100

        // wait until afterConnectionClosed to countDown to finish the test
        val countDownLatch = CountDownLatch(n)

        for (i in 0 until n) {
            StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
                override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                    val playerTurn = PlayerTurn.parseFrom(message.payload)
                    val playerDecision = PlayerDecision.newBuilder()
                            .setTurn(playerTurn.turn)
                            .setPlayerName("Joe%02d".format(i))
                            .setIncrement(1)
                            .build()

                    session.sendMessage(BinaryMessage(playerDecision.toByteArray()))
                }

                override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
                    countDownLatch.countDown()
                }
            }, PLAYER_URL)
        }

        assert(countDownLatch.await(10, TimeUnit.SECONDS))
    }

    /**
     * Test to see if the Visualizer can connect with the proper protobuf
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun visualizerCanConnect() {
        // wait until afterConnectionClosed to countDown to finish the test
        val countDownLatch = CountDownLatch(1)

        StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                val turn = VisualizerChange.parseFrom(message.payload)
                if (turn.changeNumber == 3L) {
                    countDownLatch.countDown()
                }
            }
        }, VISUALIZER_URL)

        assert(countDownLatch.await(5, TimeUnit.SECONDS))
    }
}