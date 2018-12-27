package xyz.chengqian.basesdk.http.retrofit

import android.text.TextUtils
import okhttp3.*
import java.util.HashMap
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * @author 程前 created on 2018/12/21.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
class LoggingInterceptor(private val builder: LoggingInterceptor.Builder) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val headerMap = builder.headers
        var rSubtype: String?
        var key: String
        if (headerMap.size > 0) {
            val requestBuilder = request.newBuilder()
            val var5 = headerMap.keys.iterator()

            while (var5.hasNext()) {
                rSubtype = var5.next()
                key = headerMap[rSubtype]!!
                requestBuilder.addHeader(rSubtype, key)
            }

            request = requestBuilder.build()
        }

        val queryMap = this.builder.httpUrl
        if (queryMap.size > 0) {
            val httpUrlBuilder = request.url().newBuilder(request.url().toString())
            val var28 = queryMap.keys.iterator()

            while (var28.hasNext()) {
                key = var28.next()
                val value = queryMap[key] as String
                httpUrlBuilder!!.addQueryParameter(key, value)
            }

            request = request.newBuilder().url(httpUrlBuilder!!.build()).build()
        }

        val requestBody = request.body()
        rSubtype = null
        if (requestBody?.contentType() != null) {
            rSubtype = requestBody.contentType()!!.subtype()
        }

        val executor = this.builder.executor
        if (this.isNotFileRequest(rSubtype)) {
            if (executor != null) {
                executor.execute(createPrintJsonRequestRunnable(this.builder, request))
            } else {
                Printer.printJsonRequest(this.builder, request)
            }
        } else if (executor != null) {
            executor.execute(createFileRequestRunnable(this.builder, request))
        } else {
            Printer.printFileRequest(this.builder, request)
        }

        val st = System.nanoTime()
        val response = chain.proceed(request)
        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val segmentList = request.url().encodedPathSegments()
        val header = response.headers().toString()
        val code = response.code()
        val isSuccessful = response.isSuccessful
        val message = response.message()
        val responseBody = response.body()
        val contentType = responseBody!!.contentType()
        var subtype: String? = null
        if (contentType != null) {
            subtype = contentType.subtype()
        }

        if (this.isNotFileRequest(subtype)) {
            val bodyString = Printer.getJsonString(responseBody.string())
            val url = response.request().url().toString()
            if (executor != null) {
                executor.execute(createPrintJsonResponseRunnable(this.builder, chainMs, isSuccessful, code, header, bodyString, segmentList, message, url))
            } else {
                Printer.printJsonResponse(this.builder, chainMs, isSuccessful, code, header, bodyString, segmentList, message, url)
            }

            val body = ResponseBody.create(contentType, bodyString)
            return response.newBuilder().body(body).build()
        } else {
            if (executor != null) {
                executor.execute(createFileResponseRunnable(this.builder, chainMs, isSuccessful, code, header, segmentList, message))
            } else {
                Printer.printFileResponse(this.builder, chainMs, isSuccessful, code, header, segmentList, message)
            }

            return response
        }
    }

    class Builder {
        val headers: HashMap<String, String> = HashMap()
        val httpUrl: HashMap<String, String> = HashMap()
        private var isDebug: Boolean = false
        private var requestTag = ""
        private var responseTag = ""
        internal var executor: Executor? = null
            private set

        internal fun getTag(isRequest: Boolean): String {
            return if (isRequest) {
                if (TextUtils.isEmpty(this.requestTag)) TAG else this.requestTag
            } else {
                if (TextUtils.isEmpty(this.responseTag)) TAG else this.responseTag
            }
        }

        fun addHeader(name: String, value: String): LoggingInterceptor.Builder {
            this.headers[name] = value
            return this
        }

        fun addQueryParam(name: String, value: String): LoggingInterceptor.Builder {
            this.httpUrl[name] = value
            return this
        }

        fun tag(tag: String): LoggingInterceptor.Builder {
            TAG = tag
            return this
        }

        fun request(tag: String): LoggingInterceptor.Builder {
            this.requestTag = tag
            return this
        }

        fun response(tag: String): LoggingInterceptor.Builder {
            this.responseTag = tag
            return this
        }

        fun loggable(isDebug: Boolean): LoggingInterceptor.Builder {
            this.isDebug = isDebug
            return this
        }


        fun executor(executor: Executor): LoggingInterceptor.Builder {
            this.executor = executor
            return this
        }

        fun build(): LoggingInterceptor {
            return LoggingInterceptor(this)
        }

        companion object {
            private var TAG = "LoggingI"
        }
    }

    private fun isNotFileRequest(subtype: String?): Boolean {
        return subtype != null && (subtype.contains("json") || subtype.contains("xml") || subtype.contains("plain") || subtype.contains("html"))
    }

    private fun createPrintJsonRequestRunnable(builder: LoggingInterceptor.Builder, request: Request): Runnable {
        return Runnable { Printer.printJsonRequest(builder, request) }
    }

    private fun createFileRequestRunnable(builder: LoggingInterceptor.Builder, request: Request): Runnable {
        return Runnable { Printer.printFileRequest(builder, request) }
    }

    private fun createPrintJsonResponseRunnable(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean, code: Int, headers: String, bodyString: String, segments: List<String>, message: String, responseUrl: String): Runnable {
        return Runnable { Printer.printJsonResponse(builder, chainMs, isSuccessful, code, headers, bodyString, segments, message, responseUrl) }
    }

    private fun createFileResponseRunnable(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean, code: Int, headers: String, segments: List<String>, message: String): Runnable {
        return Runnable { Printer.printFileResponse(builder, chainMs, isSuccessful, code, headers, segments, message) }
    }

}