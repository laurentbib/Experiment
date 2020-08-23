package sillapps.com.experiment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import sillapps.com.experiment.app.MainActivity

class MainActivityTest {

    @get:Rule
    val mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun startDestination() {
        (mainActivityTestRule.activity.supportFragmentManager.fragments.first() as? NavHostFragment)?.run {
            assertEquals(navController.graph[navController.graph.startDestination], findNavController().currentDestination)
        }
    }
}