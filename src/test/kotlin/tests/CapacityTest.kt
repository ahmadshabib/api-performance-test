package tests

import io.gatling.javaapi.core.CoreDsl.*

class CapacityTest : BaseSimulation() {

private val computerSearch = scenario("ComputerSearch").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(5000).during(60)),
        ).protocols(httpProtocol)
    }
}

