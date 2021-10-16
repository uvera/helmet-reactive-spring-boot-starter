@file:Suppress("unused")

package io.uvera.helmetreactivespringbootstarter.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "spring.helmet.reactive")
@ConstructorBinding
class HelmetReactiveProperties(
    val enableCrossOriginEmbedderPolicy: Boolean = true,
    val enableCrossOriginOpenerPolicy: Boolean = true,
    val enableCrossOriginResourcePolicy: Boolean = true,
    val enableOriginAgentCluster: Boolean = true,
    val enableReferrerPolicy: Boolean = true,
    val enableStrictTransportSecurity: Boolean = true,
    val enableDoNotSniffMimetype: Boolean = true,
    val enableXDnsPrefetchControl: Boolean = true,
    val enableXDownloadOptions: Boolean = true,
    val enableXFrameOptions: Boolean = true,
    val enableXPermittedCrossDomainPolicies: Boolean = true,
    val removeXPoweredBy: Boolean = true,
    val disableXXssProtection: Boolean = true,


    val crossOriginResourcePolicy: CrossOriginResourcePolicy = CrossOriginResourcePolicy.SAME_ORIGIN,
    val crossOriginOpenerPolicy: CrossOriginOpenerPolicy = CrossOriginOpenerPolicy.SAME_ORIGIN,
    val referrerPolicy: List<ReferrerPolicy> = listOf(),
    val strictTransportSecurityMaxAge: Long = 15552000L,
    val strictTransportSecurityIncludeSubDomains: Boolean = true,
    val strictTransportSecurityPreload: Boolean = false,
    val xDnsPrefetchControl: XDnsPrefetchControl = XDnsPrefetchControl.OFF,
    val xFrameOptions: XFrameOptions = XFrameOptions.SAME_ORIGIN,
    val xPermittedCrossDomainPolicies: XPermittedCrossDomainPolicies = XPermittedCrossDomainPolicies.NONE,
)

enum class CrossOriginOpenerPolicy(val value: String) {
    SAME_ORIGIN("same-origin"),
    SAME_ORIGIN_ALLOW_POPUPS("same-origin-allow-popups"),
    UNSAFE_NONE("unsafe-none")
}

enum class CrossOriginResourcePolicy(val value: String) {
    SAME_ORIGIN("same-origin"),
    SAME_SITE("same-site"),
    CROSS_ORIGIN("cross-origin")
}

enum class ReferrerPolicy(val value: String) {
    NO_REFERRER("no-referrer"),
    NO_REFERRER_WHEN_DOWNGRADE("no-referrer-when-downgrade"),
    SAME_ORIGIN("same-origin"),
    ORIGIN("origin"),
    STRICT_ORIGIN("strict-origin"),
    ORIGIN_WHEN_CROSS_ORIGIN("origin-when-cross-origin"),
    STRICT_ORIGIN_WHEN_CROSS_ORIGIN("strict-origin-when-cross-origin"),
    UNSAFE_URL("unsafe-url"),
    EMPTY_STRING(""),
}

enum class XDnsPrefetchControl {
    ON, OFF
}

enum class XFrameOptions(val value: String) {
    DENY("DENY"),
    SAME_ORIGIN("SAMEORIGIN")
}

enum class XPermittedCrossDomainPolicies(val value: String) {
    NONE("none"),
    MASTER_ONLY("master-only"),
    BY_CONTENT_TYPE("by-content-type"),
    ALL("all")
}
