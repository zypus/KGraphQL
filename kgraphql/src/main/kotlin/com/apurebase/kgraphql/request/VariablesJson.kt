package com.apurebase.kgraphql.request

import com.apurebase.kgraphql.ExecutionException
import com.apurebase.kgraphql.GraphQLError
import com.apurebase.kgraphql.getIterableElementType
import com.apurebase.kgraphql.isIterable
import com.apurebase.kgraphql.schema.model.ast.NameNode
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.getContextual
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

/**
 * Represents already parsed variables json
 */
interface VariablesJson {

    fun <T : Any> get(kClass: KClass<T>, kType: KType, key : NameNode) : T?

    class Empty : VariablesJson {
        override fun <T : Any> get(kClass: KClass<T>, kType: KType, key: NameNode): T? {
            return null
        }
    }

    class Defined(val j: Json, val json: JsonObject) : VariablesJson {

//        constructor(objectMapper: ObjectMapper, json : String) : this(objectMapper, objectMapper.readTree(json))

        /**
         * map and return object of requested class
         */
        override fun <T : Any>get(kClass: KClass<T>, kType: KType, key : NameNode) : T? {
            require(kClass == kType.jvmErasure) { "kClass and KType must represent same class" }

            val v = json[key.value]!!
            return try {
                when {
                    kType.isIterable() -> {
                        val singleSerializer = j.serializersModule.getContextual(kType.arguments.first().type!!.classifier as KClass<*>)!!
                        j.decodeFromJsonElement(ListSerializer(singleSerializer), v) as T?
                    }
                    else -> j.decodeFromJsonElement(j.serializersModule.getContextual(kType.classifier as KClass<T>)!!, v)
                }
            } catch(e : Exception) {
                throw if (e is GraphQLError) e
                else ExecutionException("Failed to coerce $v as $kType", key, e)
            }
        }
    }

    fun KType.toTypeReference(): JavaType {
        return if(jvmErasure.isIterable()) {
            val elementType = getIterableElementType()
                ?: throw ExecutionException("Cannot handle collection without element type")

            TypeFactory.defaultInstance().constructCollectionType(List::class.java, elementType.jvmErasure.java)
        } else {
            TypeFactory.defaultInstance().constructSimpleType(jvmErasure.java, emptyArray())
        }
    }
}
