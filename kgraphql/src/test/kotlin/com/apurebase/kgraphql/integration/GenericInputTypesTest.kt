package com.apurebase.kgraphql.integration

import com.apurebase.kgraphql.KGraphQL
import org.junit.jupiter.api.Test
import kotlin.reflect.typeOf

interface CriteriaV2<OE: OrderElement> {
    val order: CriteriaOrder<OE>?
}

open class CriteriaOrder<OE: OrderElement>(
    val limit: Int? = 100,
    val offset: Int? = 0,
    val ordering: List<OE>? = listOf()
)

interface OrderElement {
    val field: String
    val direction: OrderDirection?
}

enum class OrderDirection(val order: String) {
    ASC("ASC"), ASCENDING("ASC"),
    DESC("DESC"), DESCENDING("DESC")
}

data class PersonCriteria(
    val name: String? = null,
    override val order: CriteriaOrder<PersonCriteriaOrder>? = null
): CriteriaV2<PersonCriteriaOrder>

sealed class PersonCriteriaOrder(override val field: String, override val direction: OrderDirection?): OrderElement {
    class PersonOrder(direction: OrderDirection? = null): PersonCriteriaOrder("", direction)
}

data class Person(val id: Int, val name: String)

class GenericInputTypesTest {

    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T> test() {
        val a = typeOf<T>()
    }

    @Test
    fun `Have generic types for input`() {
        val schema = KGraphQL.schema {
            type<Person>()
            enum<OrderDirection>()
            inputType<PersonCriteria>()

            query("people") {
                resolver { criteria: PersonCriteria ->
                    listOf<Person>()
                }
            }
        }

        schema.executeBlocking("{people{id,name}}").let(::println)
    }

}
