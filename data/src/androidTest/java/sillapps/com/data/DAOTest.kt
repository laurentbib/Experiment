package sillapps.com.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import sillapps.com.data.database.ExperimentDatabase
import sillapps.com.data.databaseobject.DiscountDBObject
import sillapps.com.data.databaseobject.RestaurantDBObject
import sillapps.com.data.utils.*

@RunWith(AndroidJUnit4::class)
class DAOTest {

    private lateinit var database: ExperimentDatabase

    private val discountDBObject = DiscountDBObject(restaurantId = REST_ID, nbPeople = NB_PEOPLE, minimumAmount = MIN_AMOUNT, discountPercent = DISCOUNT_PERCENT)
    private val restaurantDBObject = RestaurantDBObject(name = REST_NAME, description = REST_DESC, availableAt = REST_AVAILABILITY, backgroundUrl = BG_URL, logoUrl = LOGO_URL)

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, ExperimentDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun discountDAOInsertAndGet() {
        // Insert
        val inRestId = database.restaurantDAO().insert(restaurantDBObject).toInt()
        val inDiscount = discountDBObject.copy(restaurantId = inRestId)
        val inDiscountId = database.discountDAO().insert(inDiscount).toInt()
        // Get
        val outDiscounts = database.discountDAO().getByRestaurantId(inRestId)
        assertThat(outDiscounts.size, `is`(1))
        assertEquals(outDiscounts[0], inDiscount.copy(id = inDiscountId))
    }

    @Test
    fun discountDAOInsertAllAndGet() {
        // Insert All
        val inRestId = database.restaurantDAO().insert(restaurantDBObject).toInt()
        val inDiscount = discountDBObject.copy(restaurantId = inRestId)
        val inDiscounts = listOf(inDiscount, inDiscount)
        val inDiscountIds = database.discountDAO().insertAll(inDiscounts).map { it.toInt() }
        // Get
        val outDiscounts = database.discountDAO().getByRestaurantId(inRestId)
        assertThat(outDiscounts.size, `is`(inDiscounts.size))
        outDiscounts.forEachIndexed { index, discount -> assertEquals(discount, inDiscounts[index].copy(id = inDiscountIds[index])) }
    }

    @Test
    fun discountDAOInsertAndPurge() {
        // Insert
        val inRestId = database.restaurantDAO().insert(restaurantDBObject).toInt()
        val inDiscount = discountDBObject.copy(restaurantId = inRestId)
        val inDiscounts = listOf(inDiscount, inDiscount)
        database.discountDAO().insertAll(inDiscounts)
        assertThat(database.discountDAO().getByRestaurantId(inRestId).size, `is`(inDiscounts.size))
        // Purge
        database.restaurantDAO().purge()
        assertThat(database.restaurantDAO().getAll().size, `is`(0))
        assertThat(database.discountDAO().getAll().size, `is`(0))
    }

    @Test
    fun restaurantDAOInsertAndGet() {
        // Insert
        val inRestId = database.restaurantDAO().insert(restaurantDBObject).toInt()
        // Get
        val outRests = database.restaurantDAO().getAll()
        assertThat(outRests.size, `is`(1))
        assertEquals(outRests[0], restaurantDBObject.copy(id = inRestId))
    }

    @Test
    fun restaurantDAOInsertAllAndGet() {
        // Insert All
        val inRests = listOf(restaurantDBObject, restaurantDBObject)
        val inRestIds = database.restaurantDAO().insertAll(inRests).map { it.toInt() }
        // Get
        val outRests = database.restaurantDAO().getAll()
        assertThat(outRests.size, `is`(inRests.size))
        outRests.forEachIndexed { index, rest -> assertEquals(rest, inRests[index].copy(id = inRestIds[index])) }
    }

    @Test
    fun restaurantDAOInsertAndPurge() {
        // Insert
        val insertedRestaurants = listOf(restaurantDBObject, restaurantDBObject)
        database.restaurantDAO().insertAll(insertedRestaurants)
        assertThat(database.restaurantDAO().getAll().size, `is`(insertedRestaurants.size))
        // Purge
        database.restaurantDAO().purge()
        assertThat(database.restaurantDAO().getAll().size, `is`(0))
    }
}