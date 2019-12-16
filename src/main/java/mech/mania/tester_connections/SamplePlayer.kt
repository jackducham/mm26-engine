package mech.mania.tester_connections

import mech.mania.tester_connections.model.PlayerDecisionProtos
import mech.mania.tester_connections.model.PlayerTurnProtos
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.*
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.handler.BinaryWebSocketHandler

class PlayerWebSocketHandler : BinaryWebSocketHandler() {

    private val sessionList = HashMap<WebSocketSession, Int>()
    private var decision = PlayerDecisionProtos.PlayerDecision.getDefaultInstance()

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    public override fun handleBinaryMessage(session: WebSocketSession?, message: BinaryMessage?) {
        val turn = PlayerTurnProtos.PlayerTurn.parseFrom(message?.payload)
        if (turn.increment == 1) {
            decision = PlayerDecisionProtos.PlayerDecision.newBuilder()
                    .setIncrement(1)
                    .build()
        }
        broadcast(decision)
    }

    private fun emit(session: WebSocketSession, msg: PlayerDecisionProtos.PlayerDecision) = session.sendMessage(BinaryMessage(msg.toByteArray()))
    private fun broadcast(msg: PlayerDecisionProtos.PlayerDecision) = sessionList.forEach { emit(it.key, msg) }
}

@Configuration @EnableWebSocket
open class WSConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(PlayerWebSocketHandler(), "/player").withSockJS()
    }
}


@SpringBootApplication
open class SamplePlayer

fun main(args: Array<String>) {
    runApplication<SamplePlayer>(*args)
}
