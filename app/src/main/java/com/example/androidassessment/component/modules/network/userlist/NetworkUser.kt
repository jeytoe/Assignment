package com.example.androidassessment.component.modules.network.userlist

import com.google.gson.annotations.SerializedName

data class NetworkUser(
    var id: Int,
    var name: String,
    var username: String,
    var email: String,
    var address: Address
)

data class Address(
    var street: String,
    var suite: String,
    var city: String,
    var zipCode: String,
    var geo: LatLong,
    var phone: String,
    var website: String,
    var company: Company
)

data class LatLong(
    var lat: Float,
    @SerializedName("lng")
    var long: Float
)

data class Company(
    var name: String,
    var catchPhrase: String,
    var bs: String
)
