package com.apurebase.kgraphql

import com.apurebase.kgraphql.schema.dsl.SchemaConfigurationDSL

class KtorConfigurationDSL {
    /**
     * This adds support for opening the graphql route within the browser
     */
    var playground: Boolean = false

    var endpoint: String = "graphql"

    var webSocket: Boolean = true


    internal fun build() = KtorGraphQLConfiguration(
        playground = playground,
        endpoint = endpoint,
        webSocket = webSocket
    )
}

fun SchemaConfigurationDSL.ktor(block: KtorConfigurationDSL.() -> Unit) {
    val plugin = KtorConfigurationDSL().apply(block).build()
    install(plugin)
}
