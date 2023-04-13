package andrewafony.thesis.application.feature_main.domain

import andrewafony.thesis.application.feature_main.domain.model.TimetableItemDomain

interface TimetableRepository {

    fun timetable() : List<TimetableItemDomain>

    fun lesson(): TimetableItemDomain
}