package ru.k2.outstaff.support

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class UpperCaseDeserializer : JsonDeserializer<List<String>>() {

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): List<String> {
        val list = mutableListOf<String>()

        val codec = parser?.codec
        val node = codec?.readTree<JsonNode>(parser)
        val elements = node?.elements()
        while(elements!!.hasNext()){
            list.add(elements.next().asText()?.toUpperCase()!!)
        }
        return list
    }
}