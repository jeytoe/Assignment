package com.example.androidassessment

import androidx.test.platform.app.InstrumentationRegistry
import com.example.androidassessment.component.modules.database.UserRepository
import org.junit.rules.ExternalResource

class UserDbRule : ExternalResource() {
    private val userRepository: UserRepository

    override fun before() {
        clear()
    }

    override fun after() {
        clear()
    }

    fun saveUser(username: String, password: String) {
        userRepository.insertUser(username, password)
    }

    private fun clear() {
        userRepository.clearUsers()
    }

    init {
        val applicationContext =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication
        userRepository = applicationContext.getAppComponent()!!.userRepository()
    }
}
