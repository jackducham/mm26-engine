package mech.mania.engine

import junit.framework.TestCase.assertNotNull
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

import java.net.URISyntaxException
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

import kotlinx.coroutines.*
import mech.mania.engine.logging.GameLogger
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.AbstractMessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.*
import org.springframework.util.MimeTypeUtils
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.lang.reflect.Type
import java.net.URI
import java.util.*

/*
 * Follow this blog post:
 * https://medium.com/@MelvinBlokhuijzen/spring-websocket-endpoints-integration-testing-180357b4f24c
 */

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTests {

    /** Port to launch the Game server on */
    private val port = 8080

    /** URL to connect to, will be initialized in setup */
    private var URL: String = "ws://localhost:$port/player"

    /** Promised PlayerTurn from websocket */
    private var completableFuture: CompletableFuture<PlayerTurnProtos.PlayerTurn>? = null

    /**
     * Set up the testing environment by initializing variables and starting the game server.
     */
    @Before
    fun setup() {
        // initialize local variables
        completableFuture = CompletableFuture()

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
        println("Response upon sending endgame signal: ${url.readText()}")
        Thread.sleep(1000)
    }

    /**
     * Test to see if the endpoint works and can be connected to via websocket
     */
    @Test
    @Throws(URISyntaxException::class, InterruptedException::class, ExecutionException::class, TimeoutException::class)
    fun canReceivePlayerTurn() {
        val wsClient = StandardWebSocketClient()
        wsClient.doHandshake(object : BinaryWebSocketHandler() {
            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                val playerTurn = PlayerTurnProtos.PlayerTurn.parseFrom(message.payload)
                completableFuture?.complete(playerTurn)
            }
        }, URL).get()

        val playerTurn: PlayerTurnProtos.PlayerTurn = completableFuture!!.get(1000, TimeUnit.SECONDS)
        assertNotNull(playerTurn)
    }
}