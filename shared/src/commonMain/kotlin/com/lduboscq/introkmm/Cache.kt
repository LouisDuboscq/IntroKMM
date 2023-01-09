package com.lduboscq.introkmm

class Cache(
    private val rocketQuery: RocketLaunchQueries,

) {

    private fun store(rocketLaunch: RocketLaunch) {
        rocketQuery.insertOrReplace(
            Rocket_launch_Entity(
                id = rocketLaunch.id,
                flight_number = rocketLaunch.flightNumber,
                mission_name = rocketLaunch.missionName,
                launch_date_utc = rocketLaunch.launchDateUTC,
                launch_success = rocketLaunch.launchSuccess
            )
        )
    }

    internal fun store(rocketLaunches: List<RocketLaunch>) {
        rocketQuery.transaction {
            rocketLaunches.forEach { rocketLaunch ->
                store(rocketLaunch)
            }
        }
    }

    fun get(): List<RocketLaunch> =
        rocketQuery.selectAll().executeAsList().map {
            RocketLaunch(
                id = it.id,
                flightNumber = it.flight_number,
                missionName = it.mission_name,
                launchDateUTC = it.launch_date_utc,
                launchSuccess = it.launch_success
            )
        }

    internal fun clearDatabase() {
        rocketQuery.transaction {
            rocketQuery.deleteAll()
        }
    }
}
