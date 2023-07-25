package com.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.interfaces.UiAction
import com.example.base.interfaces.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base class for [ViewModel] instances
 */
abstract class BaseViewModel<Action : UiAction, State : UiState> : ViewModel() {

    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    val currentState: State
        get() = uiState.value

    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event : MutableSharedFlow<Action> = MutableSharedFlow()
    val action = _event.asSharedFlow()


    init {
        subscribeActions()
    }

    /**
     * Start listening to Action
     */
    private fun subscribeActions() {
        viewModelScope.launch {
            action.collect {
                handleAction(it)
            }
        }
    }

    /**
     * Handle each action
     */
    abstract fun handleAction(action : Action)


    /**
     * Set new Action
     */
    fun setAction(action : Action) {
        val newAction = action
        viewModelScope.launch { _event.emit(newAction) }
    }


    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

}