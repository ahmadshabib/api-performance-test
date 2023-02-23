package tests

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import config.Config
import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import jodd.util.MapEntry

/**
 * docs: https://gatling.io/docs/gatling/reference/current/core/injection/
 *
 */
abstract class BaseSimulation : Simulation() {

    internal val ids =
        CoreDsl.csv("computer_ids.csv").readRecords().flatMap { it.entries }.map { it.value }.asSequence()
            .shuffled() as Sequence<String>


    private val config = ConfigLoaderBuilder.default()
        .addResourceSource("/configs.yaml")
        .build()
        .loadConfigOrThrow<Config>()

    val httpProtocol = http.baseUrl(config.url)
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

    fun search(
        ids: Sequence<String>
    ) =
        exec(
            http("search")
                .get("/computers/")
                .queryParamSeq {
                    ids.toList().take(1).map { MapEntry("p", it) }
                }
                .check(status().shouldBe(200))
        )

}