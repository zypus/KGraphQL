package com.apurebase.kgraphql.schema.dsl.types

import com.apurebase.kgraphql.schema.dsl.DepreciableItemDSL
import com.apurebase.kgraphql.schema.model.InputValueDef
import kotlin.reflect.KClass
import kotlin.reflect.KType


class InputValueDSL<T : Any>(val kClass: KClass<T>, val kType: KType? = null) : DepreciableItemDSL() {

    lateinit var name : String

    var internalName : String? = null
    var defaultValue : T? = null
    var isNullable: Boolean = false

    fun toKQLInputValue() : InputValueDef<T> = InputValueDef(
            kClass = kClass,
            name = name,
            internalName = internalName ?: name,
            defaultValue = defaultValue,
            isNullable = isNullable,
            isDeprecated = isDeprecated,
            description = description,
            deprecationReason = deprecationReason,
            kType = kType
    )
}
