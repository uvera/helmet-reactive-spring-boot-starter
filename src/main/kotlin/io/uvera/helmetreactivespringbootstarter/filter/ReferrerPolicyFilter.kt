package io.uvera.helmetreactivespringbootstarter.filter

import io.uvera.helmetreactivespringbootstarter.properties.HelmetReactiveProperties
import io.uvera.helmetreactivespringbootstarter.properties.ReferrerPolicy
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@ConditionalOnProperty(prefix = "spring-helmet.reactive", name = ["enable-referrer-policy"])
@Order(1)
@Component
class ReferrerPolicyFilter(private val props: HelmetReactiveProperties) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            return parseHeaderValues(props.referrerPolicy)
                .doOnNext {
                    set("Referrer-Policy", it)
                }.flatMap {
                    chain.filter(exchange)
                }
        }

    private fun parseHeaderValues(referrerPolicy: List<ReferrerPolicy>): Mono<String> {
        val value = referrerPolicy.joinToString(separator = ",") { it.value }
        return Mono.just(value)
    }
}
