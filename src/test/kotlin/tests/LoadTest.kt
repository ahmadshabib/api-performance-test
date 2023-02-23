package tests

import io.gatling.javaapi.core.CoreDsl.*

class LoadTest : BaseSimulation() {

    private val computerSearch = scenario("ComputerSearch").exec(search(ids))

    init {
        setUp(
            computerSearch.injectOpen(rampUsers(1000).during(60)),
        ).protocols(httpProtocol)
    }
}

