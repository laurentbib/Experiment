package sillapps.com.data.webservices

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sillapps.com.domain.model.ErrorCode
import sillapps.com.domain.model.Either

object RetrofitHelper {

    private const val BASE_URL = "https://gist.githubusercontent.com/"

    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val httpClient = OkHttpClient.Builder().apply { addInterceptor(logging) }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun create(): RestaurantRestApi {
        return retrofit.create(RestaurantRestApi::class.java)
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Either<ErrorCode, T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    Either.Successful(it)
                } ?: Either.Failure(ErrorCode.NO_DATA)
            } else {
                Either.Failure(ErrorCode.NETWORK_ERROR)
            }
        } catch (e: Exception) {
            Either.Failure(ErrorCode.UNKNOWN_ERROR)
        }
    }

    suspend fun getRestaurants() = safeApiCall { create().getRestaurants() }
}