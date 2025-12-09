package com.github.syunpeii.mockstation.server.plugins

import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.TestCaseStatus
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
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
                            updatedAt = Clock.System.now(),
                        ),
                    )
                    call.respond(testCases)
                }

                get("/{id}") {
                    val id = call.parameters["id"] ?: return@get call.respondText(
                        "Missing id",
                        status = HttpStatusCode.BadRequest,
                    )
                    val testCase = TestCase(
                        id = id,
                        title = "Sample Test Case $id",
                        description = "This is a sample test case from server",
                        status = TestCaseStatus.PENDING,
                        createdAt = Clock.System.now(),
                        updatedAt = Clock.System.now(),
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
