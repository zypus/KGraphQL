package com.apurebase.kgraphql

import com.apurebase.kgraphql.configuration.PluginConfiguration

class KtorGraphQLConfiguration(
    val playground: Boolean,
    val endpoint: String,
    val webSocket: Boolean
): PluginConfiguration
