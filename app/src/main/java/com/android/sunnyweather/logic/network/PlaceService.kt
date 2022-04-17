package com.android.sunnyweather.logic.network

import com.android.sunnyweather.SunnyWeatherApplication
import com.android.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//2、用于访问彩云天气城市搜索API的Retrofit接口
//定义好PlaceService接口，为了能够使用它，还得创建一个Retrofit构建器才行（ServiceCreator）
interface PlaceService {

    /**
     * 调用searchPlaces()方法的时候，Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址。
     * 搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现（请求时自动拼接在url后面），
     * 另外两个参数不会变，因此固定写在@GET注解中即可。
     * searchPlaces()方法的返回值被声明成了Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象了。
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}