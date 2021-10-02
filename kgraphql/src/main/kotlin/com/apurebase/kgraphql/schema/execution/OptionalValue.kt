package com.apurebase.kgraphql.schema.execution

sealed class OptionalValue<out T: Any> {
  data class Defined<T: Any>(val value: T?): OptionalValue<T>()
  object Undefined: OptionalValue<Nothing>()
}
