package com.lduboscq.introkmm
import com.lduboscq.introkmm.db.RocketsDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RocketsDatabase.Schema, "rockets.db")
    }
}