package com.android.sunnyweather.logic

import androidx.lifecycle.liveData
import com.android.sunnyweather.logic.model.Place
import com.android.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

/**
 * 仓库层   单例类
 * 判断调用方请求的数据应该是从本地数据源中获取还是从网络数据源中获取，并将获得的数据返回给调用方
 */
object Repository {

    /**
     * 一般在仓库层中定义的方法，为了能将异步获取的数据以响应式编程的方式通知给上一层，通常会返回一个LiveData对象。
     * liveData()函数是lifecycle-livedata-ktx 库提供的，它可以自动构建并返回一个LiveData对象，然后
     * 在它的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数的代码块中调用任意的挂起函数了。
     */
    //将liveData()函数的线程参数类型指定成了Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了。
    fun searchPlaces(query:String) = liveData(Dispatchers.IO){
        val result = try{
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places) //使用Kotlin内置的Result.success()方法来包装获取的城市数据列表
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result) //emit()方法将包装的结果发射出去，该方法类似于调用LiveData的setValue()方法来通知数据变化，只不过这里我们无法直接取得返回的LiveData对象，所以 lifecycle-livedata-ktx 库提供了这样一个替代方法。
    }
}