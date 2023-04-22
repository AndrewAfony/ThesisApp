package andrewafony.thesis.application.feature_deadlines.presentation.adapter

import andrewafony.thesis.application.R
import andrewafony.thesis.application.core.adapter.BaseViewHolder
import andrewafony.thesis.application.core.adapter.ViewHolderFabric
import andrewafony.thesis.application.databinding.DeadlineItemBinding
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import java.text.SimpleDateFormat

class DeadlinesViewHolderFabric : ViewHolderFabric<DeadlineItemUi> {

    override fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<DeadlineItemUi> {
        return DeadlineViewHolder(
            DeadlineItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }
}

class DeadlineViewHolder(
    private val binding: DeadlineItemBinding,
) : BaseViewHolder<DeadlineItemUi>(binding.root) {

    override fun bind(data: DeadlineItemUi) {
        binding.run {
            textDeadline.text = data.deadlineText
            textDescription.text = data.description

            if (data.discipline != null) {
                with(textDiscipline) {
                    text = data.discipline
                    visibility = View.VISIBLE
                }

                val constraints = ConstraintSet()
                constraints.clone(binding.root)
                constraints.connect(textDeadline.id, ConstraintSet.TOP, textDiscipline.id, ConstraintSet.BOTTOM)
                constraints.applyTo(binding.root)
            }

            if (data.date != null) {
                with(textDeadlineDate) {
                    visibility = View.VISIBLE
                    text = SimpleDateFormat("EEE, MMM dd").format(data.date)
                }
                imageDeadlineDate.visibility = View.VISIBLE
            }
        }
    }
}