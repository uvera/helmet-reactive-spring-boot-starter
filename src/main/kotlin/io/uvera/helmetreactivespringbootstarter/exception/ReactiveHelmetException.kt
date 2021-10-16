package io.uvera.helmetreactivespringbootstarter.exception

import org.springframework.core.NestedRuntimeException

class ReactiveHelmetException(msg: String? = null, cause: Throwable? = null) : NestedRuntimeException(msg, cause)
