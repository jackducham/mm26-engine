package mech.mania.engine

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

fun main(args: Array<String>) {
    HttpServer.create(InetSocketAddress(8000), 0).apply {
        createContext("/test") {
            val response = "This is the response"
            it.sendResponseHeaders(200, response.length.toLong())
            val os = it.responseBody
            os.write(response.toByteArray())
            os.close()
        }
        executor = null // creates a default executor
        start()
    }
}