package andrewafony.thesis.application.feature_home.presentation.adapter

import andrewafony.thesis.application.R
import andrewafony.thesis.application.core.adapter.BaseViewHolder
import andrewafony.thesis.application.core.adapter.ViewHolderFabric
import andrewafony.thesis.application.databinding.TimetableItemClassBinding
import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.firestore.GeoPoint

class TimetableViewHolderFabric(
    private val chipClickHandler: TimetableClickHandler,
) : ViewHolderFabric<TimetableItemUi> {

    override fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<TimetableItemUi> {
        return TimetableClassViewHolder(TimetableItemClassBinding.inflate(LayoutInflater.from(
            parent.context), parent, false), chipClickHandler)
    }
}

class TimetableClassViewHolder(
    private val binding: TimetableItemClassBinding,
    private val clickHandler: TimetableClickHandler
) : BaseViewHolder<TimetableItemUi>(binding.root) {

    override fun bind(data: TimetableItemUi) {
        binding.run {
            Glide.with(root)
                .load(data.employee.photo)
                .placeholder(R.drawable.user_placeholder)
                .into(binding.teacherPhoto)
            root.setOnClickListener {
                clickHandler.onClassClick(data)
            }
            order.text = data.order
            disciplineName.text = data.name
            time.text = "${data.startTime} - ${data.endTime}"
            chipType.text = data.type.replaceFirstChar { it.uppercase() }
            if (data.link.isBlank()) {
                chipPlace.apply {
                    text = context.getString(R.string.offline)
                    setOnClickListener {
                        clickHandler.onPlaceClick(data.place)
                    }
                }
            } else {
                chipPlace.apply {
                    text = context.getString(R.string.online)
                    setOnClickListener {
                        clickHandler.onLinkClick(data.link)
                    }
                }
            }
            if (data.isFirstClass) {
                date.apply {
                    visibility = View.VISIBLE
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
                    visibility = View.INVISIBLE
                }
                dateFrame.visibility = View.INVISIBLE
                month.visibility = View.INVISIBLE
                dayOfWeek.visibility = View.INVISIBLE
            }
            dateFrame.visibility = if (adapterPosition == 0) View.VISIBLE else View.INVISIBLE
        }
    }
}

interface TimetableClickHandler {

    fun onClassClick(classDate: TimetableItemUi)

    fun onPlaceClick(geoPoint: GeoPoint)

    fun onLinkClick(link: String)
}