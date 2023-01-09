package com.lduboscq.introkmm

import com.lduboscq.introkmm.db.RocketsDatabase

class AppDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = RocketsDatabase(databaseDriverFactory.createDriver())
    val rocketLaunchQueries = database.rocketLaunchQueries
}
