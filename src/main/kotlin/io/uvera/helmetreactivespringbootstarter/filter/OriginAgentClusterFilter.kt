package io.uvera.helmetreactivespringbootstarter.filter

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@ConditionalOnProperty(prefix = "spring.helmet.reactive", name = ["enable-origin-agent-cluster"])
@Order(1)
@Component
class OriginAgentClusterFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            set("Origin-Agent-Cluster", "?1")
            return chain.filter(exchange)
        }
}
