package xyz.chengqian.basesdk.http.retrofit

import com.fasterxml.jackson.databind.JsonNode
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import xyz.chengqian.basesdk.http.callback.CallBack
import xyz.chengqian.basesdk.http.callback.CallBack1
import xyz.chengqian.basesdk.showinfo.CLog
import java.util.concurrent.TimeUnit

abstract class BaseRetrofit {

    companion object {
        const val BASE_URL = "http://card.yifubank.com/"
    }

    fun doRequestByRxRetrofit(callback: CallBack, vararg args: Any) {
        getObservable(Retrofit.Builder()
                .baseUrl(baseUrl())//基础URL 建议以 / 结尾
                .client(okHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava 适配器
                .build(), *args)
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(object : Subscriber<JsonNode>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        CLog.logThread().d("请求错误${baseUrl()}", e)
                        if (callback is CallBack1) {
                            callback.onError(CallBack.ERROR_CODE, e)
                        }
                    }

                    override fun onNext(responseString: JsonNode) {
                        if (!responseString.has("code") ||
                                responseString["code"].asText() == "0000" ) {
                            callback.onSuccess(bean(responseString))
                        } else{
                            if (callback is CallBack1&&responseString.has("code")){
                                callback.onError(responseString["code"].asText(),bean(responseString))
                            }else if (callback is CallBack1){
                                callback.onError(responseString["code"].asText(), null)
                            }
                        }
                    }
                })
    }

    /**
     *  构造okhttp头部
     *
     * */
    private val httpLoggingInterceptor = LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request(CLog.REQUEST)
                .response(CLog.RESPONSE)
                .build();
    private fun okHttpClient() = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                return@addInterceptor response
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    open fun baseUrl() = BASE_URL

    open fun bean(responseString: JsonNode): Any = responseString

    abstract fun getObservable(retrofit: Retrofit, vararg args: Any): Observable<JsonNode>

}