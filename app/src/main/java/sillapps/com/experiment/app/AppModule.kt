package sillapps.com.experiment.app

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sillapps.com.data.database.ExperimentDatabase
import sillapps.com.data.mapper.RestaurantMapper
import sillapps.com.data.repository.RestaurantRepositoryImpl
import sillapps.com.domain.contract.RestaurantRepository
import sillapps.com.domain.usecase.GetRestaurantUseCase
import sillapps.com.experiment.details.RestaurantDetailsViewModel
import sillapps.com.experiment.home.HomeViewModel

val appModule = module {

    // DB
    single { Room.databaseBuilder(get(), ExperimentDatabase::class.java, "experiment_database").build() }
    single<RestaurantRepository> { RestaurantRepositoryImpl(get(), get(), get()) }
    single { get<ExperimentDatabase>().restaurantDAO() }
    single { get<ExperimentDatabase>().discountDAO() }
    single { RestaurantMapper() }

    // UseCase
    factory { GetRestaurantUseCase(get()) }

    // ViewModel
    viewModel { HomeViewModel(get()) }
    viewModel { RestaurantDetailsViewModel() }
}