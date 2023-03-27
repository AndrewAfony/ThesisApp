package andrewafony.thesis.application.core.adapter

import android.view.ViewGroup

interface ViewHolderFabric<T> {

    fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    class Error<T>: ViewHolderFabric<T> {
        override fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
            throw IllegalStateException("Unknown viewType $viewType")
        }
    }
}