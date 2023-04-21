package andrewafony.thesis.application.feature_deadlines.data.local

import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DeadlineItem::class], version = 1)
abstract class DeadlinesDatabase : RoomDatabase() {

    abstract fun dao(): DeadlinesDao
}