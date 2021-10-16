package io.uvera.helmetreactivespringbootstarter.exception

import org.springframework.core.NestedRuntimeException

class HelmetException(msg: String? = null, cause: Throwable? = null) : NestedRuntimeException(msg, cause)
