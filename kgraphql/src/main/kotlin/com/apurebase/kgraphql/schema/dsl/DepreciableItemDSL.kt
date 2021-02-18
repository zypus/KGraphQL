package com.apurebase.kgraphql.schema.dsl


abstract class DepreciableItemDSL : ItemDSL() {

    @PublishedApi
    internal var isDeprecated = false

    @PublishedApi
    internal var deprecationReason: String? = null

    infix fun deprecate(reason: String?){
        isDeprecated = true
        deprecationReason = reason
    }
}
