package com.lduboscq.introkmm

class RocketsService(
    private val cache: Cache,
    private val api: Api
) {
    @Throws(Exception::class)
    suspend fun get(): List<RocketLaunch> {
        val cachedLaunches = cache.get()
        val forceReload = false // todo introduce multiplatform settings to save refresg date and refresh after 3min
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.fetch().also {
                cache.clearDatabase()
                cache.store(it)
            }
        }
    }
}