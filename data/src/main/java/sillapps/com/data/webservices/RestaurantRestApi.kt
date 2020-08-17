package sillapps.com.data.webservices

import retrofit2.Response
import retrofit2.http.GET
import sillapps.com.data.dataobject.GetRestaurantResponse

interface RestaurantRestApi {
    @GET("/psicot/ab5fea0e28ceb9ae1420df5cedb32bc9/raw/4b1fc35395f7474a31078ffa2b1203cb243a217c/restaurants.json")
    suspend fun getRestaurants(): Response<GetRestaurantResponse>
}