package com.apurebase.kgraphql.integration

import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.integration.OrderDirection.ASCENDING
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface Criteria <T: Enum<T>> {
    val order: Order<T>?
}

data class Order<T: Enum<T>>(
    val limit: Int? = 100,
    val offset: Int? = 0,
    val ordering: List<OrderElement<T>>? = null
)

data class OrderElement<T: Enum<T>>(
    val field: T,
    val direction: OrderDirection? = ASCENDING
)

enum class OrderDirection(val order: String) {
    ASCENDING("ASC"),
    DESCENDING("DESC")
}


// Specific
data class MyTypeCriteria(
    val search: String? = null,
    override val order: Order<MyTypeSort>? = null
): Criteria<MyTypeSort>

enum class MyTypeSort {
    FIRST_NAME,
    LAST_NAME
}


class JeggyWishSetup {

    @Test
    @Disabled
    fun `Complex input types with generics`() {
        val schema = KGraphQL.schema {

            enum<OrderDirection>()

            query("get") {
                resolver { criteria: MyTypeCriteria ->
                    listOf("Whatever")
                }
            }
        }


        schema.executeBlocking("""
            {
                get(criteria: {
                    order: {
                        ordering: [
                            { field: FIRST_NAME, direction: ASCENDING },
                            { field: LAST_NAME, direction: DESCENDING }
                        ]
                    }
                })
            }
        """).let(::println)

    }

}
