package sillapps.com.experiment.utils

internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}