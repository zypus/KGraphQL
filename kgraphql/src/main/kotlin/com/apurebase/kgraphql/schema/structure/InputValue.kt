package com.apurebase.kgraphql.schema.structure

import com.apurebase.kgraphql.schema.introspection.__InputValue
import com.apurebase.kgraphql.schema.model.InputValueDef


class InputValue<T : Any>(
        valueDef: InputValueDef<T>,
        override val type: Type,
        //TODO: Replace with GraphQL compliant representation
        override val defaultValue: String?
) : __InputValue {

    override val name: String = valueDef.name
    val internalName: String = valueDef.internalName ?: valueDef.name
    val isNullable: Boolean = valueDef.isNullable

    override val description: String? = valueDef.description

    val default: T? = valueDef.defaultValue
}