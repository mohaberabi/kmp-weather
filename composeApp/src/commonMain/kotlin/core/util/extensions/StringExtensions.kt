package core.util.extensions

import core.domain.model.AppLang


inline fun <reified T : Enum<T>> String.toEnum(): T = enumValueOf<T>(this)