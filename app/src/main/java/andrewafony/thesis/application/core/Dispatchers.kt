package andrewafony.thesis.application.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface Dispatchers: DispatchersName {

    fun launchUi(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    fun launchBackground(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    abstract class Abstract(
        private val ui: CoroutineDispatcher,
        private val background: CoroutineDispatcher,
    ) : Dispatchers {

        override fun launchUi(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit,
        ): Job = scope.launch(context = ui, block = block)

        override fun launchBackground(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit,
        ): Job = scope.launch(context = background, block = block)

        override val mainName: CoroutineDispatcher = ui

        override val backgroundName: CoroutineDispatcher = background
    }

    class Base: Abstract(kotlinx.coroutines.Dispatchers.Main, kotlinx.coroutines.Dispatchers.IO)
}

interface DispatchersName {

    val mainName: CoroutineDispatcher

    val backgroundName: CoroutineDispatcher
}