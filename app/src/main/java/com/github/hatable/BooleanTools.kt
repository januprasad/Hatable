package com.github.hatable

infix fun <T> Boolean.then(param: T): T? = if (this) param else null
