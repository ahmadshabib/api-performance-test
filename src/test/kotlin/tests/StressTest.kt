package tests

import io.gatling.javaapi.core.CoreDsl.*

class StressTest : BaseSimulation() {

    private val computerSearch = scenario("ComputerSearch").exec(search(ids))
    private val computerSearchSpike = scenario("ComputerSearchSpike").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(30000).during(300)),
            computerSearchSpike.injectOpen(nothingFor(20), rampUsers(10000).during(200)),
        ).protocols(httpProtocol)
    }
}

