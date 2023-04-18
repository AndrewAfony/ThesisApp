package andrewafony.thesis.application.feature_home.domain

interface TimetableRepository {

    suspend fun timetable() : List<TimetableItemDomain>

    suspend fun lesson(): TimetableItemDomain
}