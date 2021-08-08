package com.example.androidassessment.component.modules.network.configurations

import java.io.File

interface ApiConfiguration {
    val apiHost: String
    val cacheDir: File
    val cacheSize: Int
    val timeoutSeconds: Int
}
