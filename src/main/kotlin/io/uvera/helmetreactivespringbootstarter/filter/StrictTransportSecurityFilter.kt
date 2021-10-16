package io.uvera.helmetreactivespringbootstarter.filter

import io.uvera.helmetreactivespringbootstarter.exception.HelmetException
import io.uvera.helmetreactivespringbootstarter.properties.HelmetReactiveProperties
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
class StrictTransportSecurityFilter(private val props: HelmetReactiveProperties) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            return parseHeaderValues(props).doOnNext {
                set("Strict-Transport-Security", it)
            }.flatMap {
                chain.filter(exchange)
            }
        }

    private fun parseHeaderValues(props: HelmetReactiveProperties): Mono<String> {
        val result = mutableListOf<String>()
        if (props.strictTransportSecurityMaxAge > 0L) {
            result.add("max-age=${props.strictTransportSecurityMaxAge}")
        } else {
            return Mono.error(HelmetException("Strict transport security max age must be greater than 0"))
        }

        if (props.strictTransportSecurityIncludeSubDomains) {
            result.add("includeSubDomains")
        }

        if (props.strictTransportSecurityPreload) {
            result.add("preload")
        }

        return Mono.just(result.joinToString("; "))
    }
}
