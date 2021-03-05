package com.apurebase.kgraphql.configuration

import com.apurebase.kgraphql.schema.execution.Executor
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.JsonBuilder
import kotlin.reflect.KClass

data class SchemaConfiguration(
        //document parser caching mechanisms
        val useCachingDocumentParser: Boolean,
        val documentParserCacheMaximumSize: Long,
        //jackson features
        @Deprecated("moved to kotlin-serialization")
        val objectMapper: ObjectMapper,

        val configJson: JsonBuilder.() -> Unit,


        val useDefaultPrettyPrinter: Boolean,
        //execution
        val coroutineDispatcher: CoroutineDispatcher,

        val wrapErrors: Boolean,

        val executor: Executor,
        val timeout: Long?,
        val plugins: MutableMap<KClass<*>, Any>
) {
        @Suppress("UNCHECKED_CAST")
        operator fun <T: Any> get(type: KClass<T>) = plugins[type] as T?
}
