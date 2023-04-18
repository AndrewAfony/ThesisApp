package andrewafony.thesis.application.feature_home.presentation.adapter

import andrewafony.thesis.application.core.adapter.BaseViewHolder
import andrewafony.thesis.application.core.adapter.ViewHolderFabric
import andrewafony.thesis.application.databinding.TimetableItemClassBinding
import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.GeoPoint

class TimetableViewHolderFabric(
    private val chipClickHandler: TimetableChipClickHandler
) : ViewHolderFabric<TimetableItemUi> {

    override fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<TimetableItemUi> {
        return TimetableClassViewHolder(TimetableItemClassBinding.inflate(LayoutInflater.from(
            parent.context), parent, false), chipClickHandler)
    }
}

class TimetableClassViewHolder(
    private val binding: TimetableItemClassBinding,
    private val chipClickHandler: TimetableChipClickHandler
) : BaseViewHolder<TimetableItemUi>(binding.root) {

    override fun bind(data: TimetableItemUi) {
        binding.run {
            disciplineName.text = data.name
            time.text = "${data.startTime} - ${data.endTime}"
            chipType.text = data.type.replaceFirstChar { it.uppercase() }
            if (data.link.isBlank()) {
                chipPlace.apply {
                    text = "Offline"
                    setOnClickListener {
                        chipClickHandler.onPlaceClick(data.place)
                    }
                }
            } else {
                chipPlace.apply {
                    text = "Online"
                    setOnClickListener {
                        chipClickHandler.onLinkClick(data.link)
                    }
                }
            }
            if (data.isFirstClass) {
                date.apply {
                    visibility =  View.VISIBLE
                    text = data.dateDay
                }
                dateFrame.visibility = View.VISIBLE
                month.apply {
                    visibility = View.VISIBLE
                    text = data.dateMonth
                }
                dayOfWeek.apply {
                    visibility = View.VISIBLE
                    text = data.dateWeekDay
                }
            } else {
                date.apply {
                    visibility =  View.INVISIBLE
                }
                dateFrame.visibility = View.INVISIBLE
                month.visibility = View.INVISIBLE
                dayOfWeek.visibility = View.INVISIBLE
            }

        }
    }
}

interface TimetableChipClickHandler {

    fun onPlaceClick(geoPoint: GeoPoint)

    fun onLinkClick(link: String)
}