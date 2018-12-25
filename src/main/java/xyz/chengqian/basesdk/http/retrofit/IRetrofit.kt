package xyz.chengqian.basesdk.http.retrofit

import android.content.Context
import com.fasterxml.jackson.databind.JsonNode
import xyz.chengqian.basesdk.http.callback.CallBack
import xyz.chengqian.basesdk.utils.base.NetworkUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import xyz.cq.clog.CLog

abstract class IRetrofit {


    fun doRequestByRxRetrofit(callback: CallBack, vararg args: Any) {
        if (null != toastContext()) {
            if (!NetworkUtils.netWorkToast(toastContext()!!)) {
                return
            }
        }
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
                        onRequestCompleted(callback)
                    }

                    override fun onError(e: Throwable) {
                        CLog.log("THREAD").d("请求错误${baseUrl()}", e)
                        onRequestError(callback, e)
                    }

                    override fun onNext(responseString: JsonNode) {
                        onRequestNext(callback, responseString)
                    }
                })
    }

    abstract fun okHttpClient(): OkHttpClient

    abstract fun baseUrl(): String

    abstract fun bean(responseString: JsonNode): Any

    abstract fun getObservable(retrofit: Retrofit, vararg args: Any): Observable<JsonNode>

    abstract fun logServerError(): String

    abstract fun onRequestCompleted(callback: CallBack)

    abstract fun onRequestError(callback: CallBack, e: Throwable)

    abstract fun onRequestNext(callback: CallBack, responseString: JsonNode)

    fun toastContext(): Context? = null

}