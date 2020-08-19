package sillapps.com.experiment.contract

internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}