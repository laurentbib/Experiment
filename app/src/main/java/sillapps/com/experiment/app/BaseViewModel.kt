package sillapps.com.experiment.app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sillapps.com.experiment.utils.SingleLiveEvent
import sillapps.com.experiment.utils.ViewModelContract

open class BaseViewModel<STATE, EFFECT, EVENT> : ViewModel(), ViewModelContract<EVENT> {

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    fun viewStates(): LiveData<STATE> = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }


    private val _viewEffects: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<EFFECT> = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect
            ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        Log.d(TAG, "processing viewEvent: $viewEvent")
    }
}