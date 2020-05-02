package com.apurebase.kgraphql.schema

interface PubSubEngine {
    fun publish(triggerName: String, payload: Any)
    suspend fun subscribe(triggerName: String, onMessage: () -> Unit, options: Any): Number
    fun unsubscribe(subId: Int)
}
