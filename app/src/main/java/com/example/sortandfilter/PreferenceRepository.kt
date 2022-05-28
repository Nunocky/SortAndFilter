package com.example.sortandfilter

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.sortandfilter.data.SortParam
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepository @Inject constructor(
    private val application: Application
) {
    private val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences(
            "prefs", MODE_PRIVATE
        )
    }

    var sortParam: SortParam
        get() {
            val json =
                sharedPreferences.getString("sortParam", "{\"field\":0,\"order\":0}")
            return Gson().fromJson(json, SortParam::class.java)
        }
        set(value) {
            sharedPreferences
                .edit()
                .putString("sortParam", Gson().toJson(value))
                .apply()
        }

    var filterText: String
        get() {
            return sharedPreferences.getString("filterText", "") ?: ""
        }
        set(value) {
            sharedPreferences
                .edit()
                .putString("filterText", value)
                .apply()
        }
}