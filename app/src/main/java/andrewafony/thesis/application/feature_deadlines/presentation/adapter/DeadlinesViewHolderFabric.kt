package andrewafony.thesis.application.feature_deadlines.presentation.adapter

import andrewafony.thesis.application.core.adapter.BaseViewHolder
import andrewafony.thesis.application.core.adapter.ViewHolderFabric
import andrewafony.thesis.application.databinding.DeadlineItemBinding
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
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
    private val binding: DeadlineItemBinding
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

            Log.d("MyHelper", "bind: ${data.description}")
            if (data.description == null) {

                textDescription.visibility = View.GONE

                val constraints = ConstraintSet()
                constraints.clone(binding.root)
                constraints.connect(buttonIsDone.id, ConstraintSet.BOTTOM, root.id, ConstraintSet.BOTTOM)
                constraints.applyTo(binding.root)
            }

            if (data.description == null && data.date == null) {
                textDescription.visibility = View.GONE

                val constraints = ConstraintSet()
                constraints.clone(binding.root)
                constraints.connect(buttonIsDone.id, ConstraintSet.BOTTOM, root.id, ConstraintSet.BOTTOM)
                constraints.connect(textDeadline.id, ConstraintSet.BOTTOM, buttonIsDone.id, ConstraintSet.BOTTOM)
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