package xyz.chengqian.basesdk.utils.base

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import xyz.cq.clog.CLog
import java.io.IOException


/**
 * qian.cheng create on 2018/2/23.
 * content :
 */

object UtilsJSON {


    private val mapper = ObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    fun toJsonString(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }

    @Throws(IOException::class)
    fun <T> parse(json: String, c: Class<T>): T {
        return mapper.readValue(json, c)
    }
}
