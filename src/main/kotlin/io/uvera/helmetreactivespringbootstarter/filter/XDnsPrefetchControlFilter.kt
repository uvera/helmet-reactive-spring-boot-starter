package io.uvera.helmetreactivespringbootstarter.filter

import io.uvera.helmetreactivespringbootstarter.properties.HelmetReactiveProperties
import io.uvera.helmetreactivespringbootstarter.properties.XDnsPrefetchControl.OFF
import io.uvera.helmetreactivespringbootstarter.properties.XDnsPrefetchControl.ON
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@ConditionalOnProperty(prefix = "spring-helmet.reactive", name = ["enable-x-dns-prefetch-control"])
@Order(1)
@Component
class XDnsPrefetchControlFilter(private val props: HelmetReactiveProperties) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            when (props.xDnsPrefetchControl) {
                ON -> set("X-DNS-Prefetch-Control", "on")
                OFF -> set("X-DNS-Prefetch-Control", "off")
            }
            return chain.filter(exchange)
        }
}
