package org.example

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.title

fun main() {
    val port = 8080
    println("http://localhost:$port")

    embeddedServer(CIO, port) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        //title { +"" }
                    }
                    body {
                        h1 { +"Hello World" }
                    }
                }
            }
        }
    }.start(wait = true)
}
