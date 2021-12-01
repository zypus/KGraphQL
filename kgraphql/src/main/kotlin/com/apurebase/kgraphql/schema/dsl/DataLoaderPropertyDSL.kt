package com.apurebase.kgraphql.schema.dsl

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.model.FunctionWrapper
import com.apurebase.kgraphql.schema.model.InputValueDef
import com.apurebase.kgraphql.schema.model.PropertyDef
import nidomiro.kdataloader.BatchLoader
import nidomiro.kdataloader.TimedAutoDispatcherDataLoaderOptions
import nidomiro.kdataloader.factories.TimedAutoDispatcherDataLoaderFactory
import kotlin.reflect.KType

class DataLoaderPropertyDSL<T, K, R>(
    val name: String,
    val returnType: KType,
    private val block : DataLoaderPropertyDSL<T, K, R>.() -> Unit
): LimitedAccessItemDSL<T>(), ResolverDSL.Target {

    internal var dataLoader: BatchLoader<K, R>? = null
    internal var prepareWrapper: FunctionWrapper<K>? = null

    private val inputValues = mutableListOf<InputValueDef<*>>()

    fun loader(block: BatchLoader<K, R>) {
        dataLoader = block
    }

    fun prepare(block: suspend (T) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E> prepare(block: suspend (T, E) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W> prepare(block: suspend (T, E, W) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q> prepare(block: suspend (T, E, W, Q) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q, A> prepare(block: suspend (T, E, W, Q, A) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q, A, S> prepare(block: suspend (T, E, W, Q, A, S) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q, A, S, B> prepare(block: suspend (T, E, W, Q, A, S, B) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q, A, S, B, U> prepare(block: suspend (T, E, W, Q, A, S, B, U) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun <E, W, Q, A, S, B, U, C> prepare(block: suspend (T, E, W, Q, A, S, B, U, C) -> K) {
        prepareWrapper = FunctionWrapper.on(block, true)
    }

    fun accessRule(rule: (T, Context) -> Exception?){
        val accessRuleAdapter: (T?, Context) -> Exception? = { parent, ctx ->
            if (parent != null) rule(parent, ctx) else IllegalArgumentException("Unexpected null parent of kotlin property")
        }
        this.accessRuleBlock = accessRuleAdapter
    }

    fun toKQLProperty(): PropertyDef.DataLoadedFunction<T, K, R> {
        block()
        requireNotNull(prepareWrapper)
        requireNotNull(dataLoader)

        return PropertyDef.DataLoadedFunction(
            name = name,
            description = description,
            accessRule = accessRuleBlock,
            deprecationReason = deprecationReason,
            isDeprecated = isDeprecated,
            inputValues = inputValues,
            returnType = returnType,
            prepare = prepareWrapper!!,
            loader = TimedAutoDispatcherDataLoaderFactory(
                { TimedAutoDispatcherDataLoaderOptions() },
                mapOf(),
                dataLoader!!,
                null,
            )
        )
    }

    override fun addInputValues(inputValues: Collection<InputValueDef<*>>) {
        this.inputValues.addAll(inputValues)
    }

    override fun setReturnType(type: KType) {
        // NOOP
    }

}
