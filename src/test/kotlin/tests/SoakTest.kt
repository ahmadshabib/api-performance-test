package tests

import io.gatling.javaapi.core.CoreDsl.*

class SoakTest : BaseSimulation() {

    private val computerSearch = scenario("ComputerSearch").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(100000).during(28800)),
        ).protocols(httpProtocol)
    }
}
