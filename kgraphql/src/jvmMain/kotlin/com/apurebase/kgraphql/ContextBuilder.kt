package com.apurebase.kgraphql

import kotlin.reflect.KClass


public class ContextBuilder(block: ContextBuilder.() -> Unit) {

    internal val components : MutableMap<Any, Any> = mutableMapOf()

    init {
        block()
    }

    public fun <T: Any> inject(kClass: KClass<T>, component: T) {
        if (components[kClass] == null){
            components[kClass] = component
        } else {
            throw IllegalArgumentException("There's already object of type $kClass in this context -> ${components[kClass]}")
        }
    }

    public inline infix fun <reified T: Any> inject(component : T) {
        inject(T::class, component)
    }

    public inline operator fun <reified T: Any> T.unaryPlus(){
        inject(T::class, this)
    }
}

public fun context(block: ContextBuilder.() -> Unit): Context {
    return Context(ContextBuilder(block).components)
}

