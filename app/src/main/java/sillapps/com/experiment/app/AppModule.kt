package sillapps.com.experiment.app

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sillapps.com.data.repository.RestaurantRepositoryImpl
import sillapps.com.data.database.ExperimentDatabase
import sillapps.com.data.mapper.RestaurantMapper
import sillapps.com.domain.contract.RestaurantRepository
import sillapps.com.domain.usecase.GetRestaurantUseCase
import sillapps.com.experiment.details.viewmodel.RestaurantDetailsViewModel
import sillapps.com.experiment.home.viewmodel.HomeViewModel

val appModule = module {

    single { RestaurantMapper() }

    single { Room.databaseBuilder(get(), ExperimentDatabase::class.java, "experiment_database").build() }

    single { get<ExperimentDatabase>().restaurantDAO() }

    single { get<ExperimentDatabase>().discountDAO() }

    single<RestaurantRepository> { RestaurantRepositoryImpl(get(), get(), get()) }

    factory { GetRestaurantUseCase(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { RestaurantDetailsViewModel() }
}