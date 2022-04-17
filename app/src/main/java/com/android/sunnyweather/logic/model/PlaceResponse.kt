package com.android.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 1、数据模型
 * 该文件中定义的类与属性，完全就是按照搜索城市数据接口返回的JSON格式来定义的
 */
data class PlaceResponse(val status: String, val places: List<Place>)

//由于JSON中一些字段的命名可能与Kotlin的命名规范不太一致，
//因此这里使用了 @SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立映射关系。
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)