package com.apurebase.kgraphql.schema

import com.apurebase.kgraphql.schema.dsl.operations.SubscriptionDSL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

interface PubSubEngine<S: Any> {
    suspend fun publish(payload: S)
    suspend fun <R: S> subscribe(trigger: KClass<in R>, onMessage: (R) -> Unit): Number
    suspend fun unsubscribe(subId: Int)
    fun <R: S, T> SubscriptionDSL.iterator(kClass: KClass<in R>, block: (R) -> T)
}

class PubSub<S: Any> : PubSubEngine<S>, CoroutineScope {

    override val coroutineContext = Job()

    private var subCounter = AtomicInteger(0)
    private val subscriptions = mutableListOf<Subscriber<S>>()

    private val ch: Channel<S> = Channel()
    private val lCh: Channel<Subscriber<S>> = Channel()

    inner class Subscriber<R: S>(
        val id: Int,
        val kClass: KClass<in R>,
        val fn: (R) -> Unit
    )

    init {
        launch {
            for (msg in ch) {
                subscriptions.filter { it.kClass == msg::class }.map {
                    it.fn(msg)
                }
            }
        }
        launch {
            for (newListener in lCh) {
                subscriptions.add(newListener)
            }
        }
    }

    override suspend fun publish(payload: S) = ch.send(payload)

    @Suppress("UNCHECKED_CAST")
    override suspend fun <R: S> subscribe(trigger: KClass<in R>, onMessage: (R) -> Unit): Number {
        val new = Subscriber(
            id = subCounter.incrementAndGet(),
            kClass = trigger,
            fn = onMessage
        )
        lCh.send(new as Subscriber<S>)

        return new.id
    }

    override suspend fun unsubscribe(subId: Int) {
        TODO("Not yet implemented")
    }

    override fun <R : S, T> SubscriptionDSL.iterator(kClass: KClass<in R>, block: (R) -> T) {

    }

}
