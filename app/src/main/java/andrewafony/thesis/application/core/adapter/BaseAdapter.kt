package andrewafony.thesis.application.core.adapter

import andrewafony.thesis.application.core.BaseItem
import andrewafony.thesis.application.core.Mapper
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : BaseItem>(
    private val viewHolderFabric: ViewHolderFabric<T>,
) : RecyclerView.Adapter<BaseViewHolder<T>>(), Mapper.Unit<List<T>> {

    private val list: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return viewHolderFabric.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun map(data: List<T>) {
        val diffUtil = DiffUtilCallback(list, data)
        val result = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

class DiffUtilCallback<T : BaseItem>(
    private val oldList: List<T>,
    private val newList: List<T>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id() == newList[newItemPosition].id()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].content() == newList[newItemPosition].content()
    }
}