package com.hp.jetpack.demo.data.bean

data class Country(
    val city: MutableList<City>
)

data class City(
    val lists: MutableList<String>,
    val title: String
)