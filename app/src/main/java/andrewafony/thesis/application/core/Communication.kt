package andrewafony.thesis.application.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication {

    interface Observe<T> {

        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Getter<T> {

        val value: T?
    }

    interface Mutable<T> : Observe<T>, Mapper.Unit<T>

    abstract class Abstract<T>(private val liveData: MutableLiveData<T> = MutableLiveData()) :
        Mutable<T> {

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }

    abstract class Ui<T : Any>(private val liveData: MutableLiveData<T> = MutableLiveData()) :
        Abstract<T>(liveData) {

        override fun map(data: T) {
            liveData.value = data
        }
    }

    abstract class Background<T : Any>(private val liveData: MutableLiveData<T> = MutableLiveData()) :
        Abstract<T>(liveData) {

        override fun map(data: T) {
            liveData.postValue(data)
        }
    }

    abstract class Get<T: Any>(private val liveData: MutableLiveData<T> = MutableLiveData()) : Ui<T>(liveData), Getter<T> {

        override val value: T?
            get() = liveData.value
    }
}