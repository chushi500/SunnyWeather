package com.android.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//单例类  统一的网络数据源访问入口，对所有网络请求的API进行封装
object SunnyWeatherNetwork {

    //使用ServiceCreator创建了一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    /**
     * 当外部调用SunnyWeatherNetwork的searchPlaces()函数时，Retrofit就会立即发起网络请求，同时当前的协程也会被阻塞住。
     * 直到服务器响应我们的请求之后，await()函数会将解析出来的数据模型对象取并返回，同时恢复当前协程的执行，
     * searchPlaces()函数在得到await()函数的返回值后会将该数据在返回到上一层
     */
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //简化Retrofit回调的写法。需要借助协程技术实现，定义了一个await()函数，并将searchPlaces()函数也声明成挂起函数。
    // await()---Call的扩展函数
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object:Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body!=null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}