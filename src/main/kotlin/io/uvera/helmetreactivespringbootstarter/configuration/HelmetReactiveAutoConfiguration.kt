package io.uvera.helmetreactivespringbootstarter.configuration

import io.uvera.helmetreactivespringbootstarter.filter.*
import io.uvera.helmetreactivespringbootstarter.properties.HelmetReactiveProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    HelmetReactiveProperties::class,
    CrossOriginEmbedderPolicyFilter::class,
    CrossOriginOpenerPolicyFilter::class,
    CrossOriginResourcePolicyFilter::class,
    OriginAgentClusterFilter::class,
    ReferrerPolicyFilter::class,
    StrictTransportSecurityFilter::class,
    XContentTypeOptionsFilter::class,
    XDnsPrefetchControlFilter::class,
    XDownloadOptionsFilter::class,
    XFrameOptionsFilter::class,
    XPermittedCrossDomainPoliciesFilter::class,
    XPoweredByFilter::class,
    XXssProtectionFilter::class,
)
class HelmetReactiveAutoConfiguration
