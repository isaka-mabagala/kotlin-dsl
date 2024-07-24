package org.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.model.*

fun main() {
    val port = 8080
    println("http://localhost:$port")

    embeddedServer(CIO, port) {
        configureRouting()
    }.start(wait = true)
}

fun Application.configureRouting() {
    routing {
        staticResources("/task-ui", "task-ui")
        tasks()
    }
}

fun Route.tasks() {
    route("/tasks") {
        get {
            val tasks = TaskRepository.allTasks()
            call.respondText(
                contentType = ContentType.parse("text/html"),
                text = tasks.tasksAsTable()
            )
        }
        get("/by-priority/{priority}") {
            val priorityText = call.parameters["priority"]

            if (priorityText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                val priority = Priority.valueOf(priorityText)
                val tasks = TaskRepository.tasksByPriority(priority)

                if (tasks.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = tasks.tasksAsTable()
                )
            } catch(ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        post {
            val formContent = call.receiveParameters()

            val params = Triple(
                formContent["name"] ?: "",
                formContent["description"] ?: "",
                formContent["priority"] ?: ""
            )

            if (params.toList().any { it.isEmpty() }) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            try {
                val priority = Priority.valueOf(params.third)
                TaskRepository.addTask(
                    Task(
                        params.first,
                        params.second,
                        priority
                    )
                )

                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}