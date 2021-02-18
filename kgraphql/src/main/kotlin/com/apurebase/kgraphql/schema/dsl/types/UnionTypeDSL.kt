package com.apurebase.kgraphql.schema.dsl.types

import com.apurebase.kgraphql.schema.dsl.ItemDSL
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class UnionTypeDSL : ItemDSL() {

    @PublishedApi
    internal val possibleTypes = mutableSetOf<Pair<KClass<*>, KType>>()

    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T : Any>type(kClass : KClass<T>){
        possibleTypes.add(kClass to typeOf<T>())
    }

    inline fun <reified T : Any>type(){
        type(T::class)
    }
}
