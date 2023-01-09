package com.lduboscq.introkmm

import android.content.Context
import com.lduboscq.introkmm.db.RocketsDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RocketsDatabase.Schema, context, "rockets.db")
    }
}
