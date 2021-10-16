package io.uvera.helmetreactivespringbootstarter.filter

import io.uvera.helmetreactivespringbootstarter.configuration.ContentSecurityPolicyValues
import io.uvera.helmetreactivespringbootstarter.exception.ReactiveHelmetException
import io.uvera.helmetreactivespringbootstarter.properties.HelmetReactiveProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@ConditionalOnProperty(prefix = "spring-helmet.reactive", name = ["enable-content-security-policy"])
@Order(1)
@Component
class ContentSecurityPolicyFilter(private val props: HelmetReactiveProperties) : WebFilter {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            parseProps(props).doOnNext {
                set(parseHeaderName(props), it)
            }.doOnError {
                logger.warn("Error occurred: " + it.localizedMessage)
            }
            return chain.filter(exchange)
        }

    private fun parseProps(props: HelmetReactiveProperties): Mono<String> {
        val directives =
            if (props.contentSecurityPolicyUseDefault) ContentSecurityPolicyValues.defaultValues.toMutableMap() else mutableMapOf()
        props.contentSecurityPolicyDirectives.forEach { (key, value) ->
            directives[key] = value
        }

        if (directives.isEmpty()) return Mono.error(ReactiveHelmetException("Directives provided are empty."))

        val result = directives
            .mapKeys { it.key.dashify() }
            .mapTo(mutableListOf()) { (entry, value) ->
                val directiveValue = value.joinToString(separator = "", prefix = " ")
                if (directiveValue.isEmpty()) {
                    entry
                } else {
                    "${entry}${directives}"
                }
            }
        return Mono.just(result.joinToString(separator = ";"))
    }

    private fun parseHeaderName(props: HelmetReactiveProperties): String =
        if (props.contentSecurityPolicyReportOnly) "Content-Security-Policy-Report-Only"
        else "Content-Security-Policy"

    val dashifyRegex: Regex = "/[A-Z]/g".toRegex()

    private fun String.dashify() = this.replace(dashifyRegex) {
        "-${it.value.lowercase()}"
    }
}
