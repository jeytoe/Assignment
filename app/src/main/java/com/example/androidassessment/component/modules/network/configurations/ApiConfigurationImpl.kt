package com.example.androidassessment.component.modules.network.configurations

import java.io.File

class ApiConfigurationImpl(override val cacheDir: File) : ApiConfiguration {
    override val apiHost: String
        get() = ""
    override val cacheSize: Int
        get() = 15 * 1024 * 1024
    override val timeoutSeconds: Int
        get() = 30

}
