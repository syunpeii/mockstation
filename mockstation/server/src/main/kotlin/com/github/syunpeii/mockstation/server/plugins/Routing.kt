package com.github.syunpeii.mockstation.server.plugins

import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.TestCaseStatus
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Mockstation Server is running!", ContentType.Text.Plain)
        }

        route("/api") {
            route("/testcases") {
                get {
                    // Sample response
                    val testCases = listOf(
                        TestCase(
                            id = "1",
                            title = "Sample Test Case",
                            description = "This is a sample test case from server",
                            status = TestCaseStatus.PENDING,
                            createdAt = Clock.System.now(),
                            updatedAt = Clock.System.now()
                        )
                    )
                    call.respond(testCases)
                }

                get("/{id}") {
                    val id = call.parameters["id"] ?: return@get call.respondText(
                        "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                    val testCase = TestCase(
                        id = id,
                        title = "Sample Test Case $id",
                        description = "This is a sample test case from server",
                        status = TestCaseStatus.PENDING,
                        createdAt = Clock.System.now(),
                        updatedAt = Clock.System.now()
                    )
                    call.respond(testCase)
                }

                post {
                    // TODO: Receive TestCase from request body and process
                    call.respond(HttpStatusCode.Created, "TestCase created")
                }
            }
        }
    }
}
