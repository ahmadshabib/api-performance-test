package tests

import io.gatling.javaapi.core.CoreDsl.*

class SpikeTest : BaseSimulation() {

    private val computerSearch = scenario("ComputerSearch").exec(search(ids))
    private val computerSearchSpike = scenario("ComputerSearchSpike").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(1000).during(60)),
            computerSearchSpike.injectOpen(nothingFor(20), rampUsers(5000).during(10)),
        ).protocols(httpProtocol)
    }
}
