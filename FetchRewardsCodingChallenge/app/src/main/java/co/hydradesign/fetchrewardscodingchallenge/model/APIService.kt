package co.hydradesign.fetchrewardscodingchallenge.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Base URL
const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

// Creates the Moshi Converter
private val moshi = Moshi.Builder()
	.add(KotlinJsonAdapterFactory())
	.build()

// Creates the Retrofit Builder
private val retrofit = Retrofit.Builder()
	.addConverterFactory(MoshiConverterFactory.create(moshi))   // Adds The Moshi Converter
	.baseUrl(BASE_URL)                                          // Adds the BASE_URL
	.build()                                                    // Builds the Retrofit Instance

/** Retrofit API Service */
interface APIService {

	/**
	 * Retrieves and Converts the hiring.json file from the given API.
	 *
	 * @return A list containing ListItems.
	 */
	@GET( "/hiring.json" )
	suspend fun getHiringJSON() : List< ListItem >
}

/** Gives access to the Retrofit APIService */
object ItemAPI {
	val retrofitService : APIService by lazy { retrofit.create( APIService :: class.java ) }
}