package com.apurebase.kgraphql

import com.apurebase.kgraphql.schema.Schema
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder


public class KGraphQL {
    public companion object {
        public fun schema(init: SchemaBuilder.() -> Unit): Schema {
            return SchemaBuilder()
                .apply(init)
                .build()
        }
    }
}
