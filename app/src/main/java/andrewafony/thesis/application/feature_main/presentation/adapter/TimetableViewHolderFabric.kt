package andrewafony.thesis.application.feature_main.presentation.adapter

import andrewafony.thesis.application.core.adapter.BaseViewHolder
import andrewafony.thesis.application.core.adapter.ViewHolderFabric
import andrewafony.thesis.application.databinding.TimetableItemClassBinding
import andrewafony.thesis.application.databinding.TimetableItemHeaderBinding
import andrewafony.thesis.application.feature_main.presentation.TimetableItemUi
import android.view.LayoutInflater
import android.view.ViewGroup

class TimetableViewHolderFabric : ViewHolderFabric<TimetableItemUi> {

    override fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<TimetableItemUi> {
        return TimetableClassViewHolder(TimetableItemClassBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }
}

class TimetableClassViewHolder(
    private val binding: TimetableItemClassBinding,
) : BaseViewHolder<TimetableItemUi>(binding.root) {

    override fun bind(data: TimetableItemUi) {
        binding.run {
            disciplineName.text = data.name
            time.text = data.time
        }
    }
}