package com.app.covidhelp

data class GenericFeed(
    var id: String = "",
    var userID: String = "",
    var username: String = "",
    var contact: String = "",
    var description: String = "",
    var tags: ArrayList<String> = ArrayList(),
    var voters: ArrayList<String> = ArrayList(),
    var trust: Int = 0,
    var timestamp: Long = DateUtils.getCurrentDateTime(),
    var lat: Double = 0.0,
    var lon: Double = 0.0,
) {
}