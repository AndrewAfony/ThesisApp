package andrewafony.thesis.application.feature_deadlines.data.local

import androidx.room.TypeConverter
import java.util.*

class TypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long) : Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}