package andrewafony.thesis.application.core

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {

    fun string(@StringRes resId: Int): String

    class Base(private val context: Context): ManageResources{

        override fun string(resId: Int): String = context.getString(resId)
    }
}