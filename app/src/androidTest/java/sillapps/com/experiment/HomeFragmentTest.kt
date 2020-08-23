package sillapps.com.experiment

import androidx.navigation.fragment.NavHostFragment
import androidx.test.annotation.UiThreadTest
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sillapps.com.data.utils.*
import sillapps.com.domain.model.Discount
import sillapps.com.domain.model.Restaurant
import sillapps.com.experiment.app.MainActivity
import sillapps.com.experiment.home.HomeFragment
import sillapps.com.experiment.home.HomeViewEffect
import sillapps.com.experiment.home.HomeViewEvent
import sillapps.com.experiment.utils.FetchStatus

class HomeFragmentTest {

    @get:Rule
    val mainActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var homeFragment: HomeFragment

    private val discount = Discount(DISCOUNT_ID, REST_ID, NB_PEOPLE, MIN_AMOUNT, DISCOUNT_PERCENT)
    private val restaurant = Restaurant(REST_ID, REST_NAME, REST_DESC, REST_FORMATED_HOUR, BG_URL, LOGO_URL, listOf(discount, discount))

    @Before
    fun setupHomeFragment() {
        (mainActivity.activity.supportFragmentManager.fragments.first() as? NavHostFragment)?.let { navHostFragment ->
            this.navHostFragment = navHostFragment
            (navHostFragment.childFragmentManager.fragments.first() as? HomeFragment)?.let { homeFragment ->
                this.homeFragment = homeFragment
            }
        }
    }

    @Test
    fun setup() {
        assertEquals(FetchStatus.Fetching, homeFragment.viewModel.viewStates().value?.fetchStatus)
    }

    @Test
    @UiThreadTest
    fun init() {
        homeFragment.viewModel.process(HomeViewEvent.Init)
        assertEquals(FetchStatus.Fetching, homeFragment.viewModel.viewStates().value?.fetchStatus)
    }

    @Test
    @UiThreadTest
    fun onSwipeRefresh() {
        homeFragment.viewModel.process(HomeViewEvent.OnSwipeRefresh)
        assertEquals(FetchStatus.Fetching, homeFragment.viewModel.viewStates().value?.fetchStatus)
    }

    @Test
    @UiThreadTest
    fun onBottomReached() {
        homeFragment.viewModel.process(HomeViewEvent.OnBottomReached)
        assertEquals(FetchStatus.Fetching, homeFragment.viewModel.viewStates().value?.fetchStatus)
    }

    @Test
    @UiThreadTest
    fun onRestaurantClicked() {
        homeFragment.viewModel.process(HomeViewEvent.RestaurantClicked(restaurant, emptyList()))
        assertEquals(HomeViewEffect.GoToDetail(restaurant, emptyList()), homeFragment.viewModel.viewEffects().value)
    }
}