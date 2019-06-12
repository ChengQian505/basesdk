package xyz.chengqian.basesdk.http.retrofit

import android.text.TextUtils
import java.io.IOException
import okhttp3.Request
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.cq.clog.CLog

/**
 * @author 程前 created on 2018/12/21.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
open class Printer protected constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {
        private val LINE_SEPARATOR = System.getProperty("line.separator")
        private val DOUBLE_SEPARATOR: String
        private val OMITTED_RESPONSE: Array<String>
        private val OMITTED_REQUEST: Array<String>
        private val OOM_OMITTED: String

        private fun isEmpty(line: String): Boolean {
            return TextUtils.isEmpty(line) || "\n" == line || "\t" == line || TextUtils.isEmpty(line.trim { it <= ' ' })
        }

        fun printJsonRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val requestBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + bodyToString(request)
            val stringBuffer=StringBuffer()
            stringBuffer.append("\n┌────── Request ────────────────────────────────────────────────────────────────────────")
            logLines(stringBuffer, arrayOf("URL: " + request.url()), false)
            logLines( stringBuffer,getRequest(request), true)
            logLines(stringBuffer, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), true)
            stringBuffer.append("\n└───────────────────────────────────────────────────────────────────────────────────────")
            CLog.log().i(builder.getTag(true),stringBuffer.toString())
        }

        fun printJsonResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean, code: Int, headers: String, bodyString: String, segments: List<String>, message: String, responseUrl: String) {
            val responseBody = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(bodyString)
            val urlLine = arrayOf("URL: $responseUrl", "\n")
            val response = getResponse(headers, chainMs, code, isSuccessful, segments, message)
            val stringBuffer=StringBuffer()
            stringBuffer.append("\n┌────── Response ───────────────────────────────────────────────────────────────────────")
            logLines( stringBuffer,urlLine, true)
            logLines(stringBuffer, response, true)
            logLines(stringBuffer, responseBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), true)
            stringBuffer.append("\n└───────────────────────────────────────────────────────────────────────────────────────")
            CLog.log().i(builder.getTag(true),stringBuffer.toString())
        }

        fun printFileRequest(builder: LoggingInterceptor.Builder, request: Request) {
            val stringBuffer=StringBuffer()
            stringBuffer.append("\n┌────── Request ────────────────────────────────────────────────────────────────────────")
            logLines( stringBuffer,arrayOf("URL: " + request.url()), false)
            logLines( stringBuffer,getRequest(request), true)
            logLines( stringBuffer,OMITTED_REQUEST, true)
            stringBuffer.append("\n└───────────────────────────────────────────────────────────────────────────────────────")
            CLog.log().i(builder.getTag(true),stringBuffer.toString())
        }

        fun printFileResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean, code: Int, headers: String, segments: List<String>, message: String) {
            val stringBuffer=StringBuffer()
            stringBuffer.append("\n┌────── Response ───────────────────────────────────────────────────────────────────────")

            logLines( stringBuffer,getResponse(headers, chainMs, code, isSuccessful, segments, message), true)
            logLines( stringBuffer,OMITTED_RESPONSE, true)
            stringBuffer.append("\n└───────────────────────────────────────────────────────────────────────────────────────")
            CLog.log().i(builder.getTag(true),stringBuffer.toString())
        }

        private fun getRequest(request: Request): Array<String> {
            val header = request.headers().toString()
            val log = "Method: @" + request.method() + DOUBLE_SEPARATOR + if (isEmpty(header)) "" else "Headers:" + LINE_SEPARATOR + dotHeaders(header)
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean, segments: List<String>, message: String): Array<String> {
            val segmentString = slashSegments(segments)
            val log = (if (!TextUtils.isEmpty(segmentString)) "$segmentString - " else "") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + DOUBLE_SEPARATOR + "Status Code: " + code + " / " + message + DOUBLE_SEPARATOR + if (isEmpty(header)) "" else "Headers:" + LINE_SEPARATOR + dotHeaders(header)
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun slashSegments(segments: List<String>): String {
            val segmentString = StringBuilder()
            val var2 = segments.iterator()

            while (var2.hasNext()) {
                segmentString.append("/").append(var2.next())
            }

            return segmentString.toString()
        }

        private fun dotHeaders(header: String): String {
            val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()
            var tag = "─ "
            if (headers.size > 1) {
                for (i in headers.indices) {
                    tag = when (i) {
                        0 -> "┌ "
                        headers.size - 1 -> "└ "
                        else -> "├ "
                    }

                    builder.append(tag).append(headers[i]).append("\n")
                }
            } else {
                val var5 = headers.size

                for (var6 in 0 until var5) {
                    val item = headers[var6]
                    builder.append(tag).append(item).append("\n")
                }
            }

            return builder.toString()
        }

        private fun logLines(stringBuffer: StringBuffer, lines: Array<String>, withLineSize: Boolean){
            val var7 = lines.size
            for (var8 in 0 until var7) {
                val line = lines[var8]
                val lineLength = line.length
                val MAX_LONG_SIZE = if (withLineSize) 110 else lineLength

                for (i in 0..lineLength / MAX_LONG_SIZE) {
                    val start = i * MAX_LONG_SIZE
                    var end = (i + 1) * MAX_LONG_SIZE
                    end = if (end > line.length) line.length else end
                    stringBuffer.append("\n"+"│ " + line.substring(start, end))
                }
            }
        }

        private fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                val body = copy.body()
                if (body == null) {
                    return ""
                } else {
                    body.writeTo(buffer)
                    return getJsonString(buffer.readUtf8())
                }
            } catch (var4: IOException) {
                return "{\"err\": \"" + var4.message + "\"}"
            }

        }

        fun getJsonString(msg: String): String {
            var message: String
            try {
                if (msg.startsWith("{")) {
                    val jsonObject = JSONObject(msg)
                    message = jsonObject.toString(3)
                } else if (msg.startsWith("[")) {
                    val jsonArray = JSONArray(msg)
                    message = jsonArray.toString(3)
                } else {
                    message = msg
                }
            } catch (var3: JSONException) {
                message = msg
            } catch (var4: OutOfMemoryError) {
                message = OOM_OMITTED
            }

            return message
        }

        init {
            DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR
            OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")
            OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")
            OOM_OMITTED = LINE_SEPARATOR + "Output omitted because of Object size."
        }
    }
}
