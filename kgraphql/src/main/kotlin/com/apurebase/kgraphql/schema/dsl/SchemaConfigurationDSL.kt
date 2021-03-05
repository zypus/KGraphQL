package com.apurebase.kgraphql.schema.dsl

import com.apurebase.kgraphql.configuration.PluginConfiguration
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.apurebase.kgraphql.configuration.SchemaConfiguration
import com.apurebase.kgraphql.schema.execution.Executor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlin.reflect.KClass

open class SchemaConfigurationDSL {
    var useDefaultPrettyPrinter: Boolean = false
    var useCachingDocumentParser: Boolean = true
    @Deprecated("Don't use this")
    var objectMapper: ObjectMapper = jacksonObjectMapper()

    var configJson: JsonBuilder.() -> Unit = {}

    var documentParserCacheMaximumSize: Long = 1000L
    var acceptSingleValueAsArray: Boolean = true
    var coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
    var wrapErrors: Boolean = true
    var executor: Executor = Executor.Parallel
    var timeout: Long? = null

    private val plugins: MutableMap<KClass<*>, Any> = mutableMapOf()

    fun install(plugin: PluginConfiguration) {
        val kClass = plugin::class
        require(plugins[kClass] == null)
        plugins[kClass] = plugin
    }


    internal fun update(block: SchemaConfigurationDSL.() -> Unit) = block()
    internal fun build(): SchemaConfiguration {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, acceptSingleValueAsArray)
        return SchemaConfiguration(
            useCachingDocumentParser,
            documentParserCacheMaximumSize,
            objectMapper,
            configJson,
            useDefaultPrettyPrinter,
            coroutineDispatcher,
            wrapErrors,
            executor,
            timeout,
            plugins
        )
    }
}
