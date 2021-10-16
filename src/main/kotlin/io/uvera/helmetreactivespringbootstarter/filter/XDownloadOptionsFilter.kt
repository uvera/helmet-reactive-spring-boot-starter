package io.uvera.helmetreactivespringbootstarter.filter

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@ConditionalOnProperty(prefix = "spring-helmet.reactive", name = ["enable-x-download-options"])
@Order(1)
@Component
class XDownloadOptionsFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            set("X-Download-Options", "noopen")
            return chain.filter(exchange)
        }
}
