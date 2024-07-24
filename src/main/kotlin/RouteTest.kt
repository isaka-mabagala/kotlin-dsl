package org.example

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1

fun main() {
    val port = 8080
    println("http://localhost:$port")

    embeddedServer(CIO, port) {
        routing {
            helloWord()
            showUser()
            showUserByQueryParameter()
            nestedRoutes()
        }
    }.start(wait = true)
}

fun Route.helloWord() {
    get("/") {
        call.respondHtml {
            body {
                h1 { +"Hello World Route" }
            }
        }
    }
}

fun Route.showUser() {
    get("/user/{name}") {
        val name = call.parameters["name"]?.uppercase()

        call.respondHtml {
            body {
                h1 { +"Hello $name your welcome!" }
            }
        }
    }
}

fun Route.showUserByQueryParameter() {
    get("/user") {
        val name = call.queryParameters["username"]?.uppercase()

        call.respondHtml {
            body {
                h1 { +"Hello $name welcome back!" }
            }
        }
    }
}

fun Route.nestedRoutes() {
    route("/nested") {
        get("/route1") {
            call.respondHtml {
                body {
                    h1 { +"This is route 1" }
                }
            }
        }
        get("/route2") {
            call.respondHtml {
                body {
                    h1 { +"This is route 2" }
                }
            }
        }
    }
}