package io.uvera.helmetreactivespringbootstarter.filter

import io.uvera.helmetreactivespringbootstarter.configuration.ContentSecurityPolicyValues
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

    val headerName by lazy {
        parseHeaderName(props)
    }

    val parsedProps by lazy {
        parseProps(props)
    }


    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        with(exchange.response.headers) {
            return parsedProps.doOnNext {
                set(headerName, it)
            }.doOnError {
                logger.warn("Error occurred: " + it.localizedMessage)
            }.flatMap {
                chain.filter(exchange)
            }
        }

    private fun parseProps(props: HelmetReactiveProperties): Mono<String> {
        val directives =
            if (props.contentSecurityPolicy.useDefault) ContentSecurityPolicyValues.defaultValues.toMutableMap() else mutableMapOf()
        props.contentSecurityPolicy.directives
            .forEach { (key, value) ->
                val newKey = key.dashify()
                directives[newKey] = value
            }

        val result = directives
            .mapKeys { it.key.dashify() }
            .map { (entry, value) ->
                val directiveValue = value.joinToString(separator = " ")
                if (directiveValue.isBlank()) {
                    entry
                } else {
                    "$entry $directiveValue"
                }
            }
        return Mono.just(result.joinToString(separator = ";"))
    }

    private fun parseHeaderName(props: HelmetReactiveProperties): String =
        if (props.contentSecurityPolicy.reportOnly) "Content-Security-Policy-Report-Only"
        else "Content-Security-Policy"

    val dashifyRegex: Regex = "[A-Z]".toRegex()

    private fun String.dashify() = this.replace(dashifyRegex) {
        "-${it.value.lowercase()}"
    }
}
