package tests

import io.gatling.javaapi.core.CoreDsl.*

class SmokeTest : BaseSimulation() {

    private val computerSearch = scenario("ComputerSearch").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(100).during(10)),
        ).protocols(httpProtocol)
    }
}

