package sillapps.com.experiment

import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import sillapps.com.experiment.app.appModule

@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(appModule) }

    @Test
    fun checkModules() = checkModules { modules(appModule) }
}