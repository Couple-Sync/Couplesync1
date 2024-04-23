package com.example.couplesync

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.android.synthetic.main.activity_main.rvRestaurants
import okhttp3.Headers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.item_restaurant.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"
private const val API_KEY = "key here"
private const val BASE_URL =    "https://api.yelp.com/v3/"
class MainActivity : AppCompatActivity(){
    var restaurantImageURL = ""
    var restaurantName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", "First Date", "New York").enqueue(object :
            Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

//        val restaurantImageView = findViewById<ImageView>(R.id.restaurantImage)
//        val nameTextView = findViewById<TextView>(R.id.restaurantName)
//        val thumbs_up = findViewById<ImageButton>(R.id.thumbs_up)
//        val thumbs_down = findViewById<ImageButton>(R.id.thumbs_down)
//
//        getRandomRestaurant(restaurantImageView, nameTextView)
//
//        thumbs_up.setOnClickListener {
//            getRandomRestaurant(restaurantImageView, nameTextView)
//        }
//        thumbs_down.setOnClickListener {
//            getRandomRestaurant(restaurantImageView, nameTextView)
//        }
//
//    }


//        private fun getRandomRestaurant(imageView: ImageView, nameTextView: TextView) {
//            val client = AsyncHttpClient()
//            val apiKey = "H1XoYkdmEbtFuydLRM9D8A0cubbOJAqKL2K3KoGxIzgTKqptu_DYyefGJO_OGEh5Hso3SQZMnoxlSti-nP7zuOEuCK5DCLoMVtK-jV6VXiPj51euyN4knvbC9XIhZnYx"
//            val url = "https://api.yelp.com/v3/businesses/search?location=NewYork&limit=50"
//
//
//            val headers = RequestHeaders()
//            headers.set("Authorization", "Bearer $apiKey")
//            client.get(url, headers, null, object : JsonHttpResponseHandler() {
//                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
//                    Log.d("Yelp", "API Request Success")
//                    val businesses = json.jsonObject.getJSONArray("businesses")
//                    val randomIndex = (0 until businesses.length()).random()
//                    val randomRestaurant = businesses.getJSONObject(randomIndex)
//
//                    val name = randomRestaurant.getString("name")
//                    val imageUrl = randomRestaurant.getString("image_url")
//
//
//                    Glide.with(this@MainActivity)
//                        .load(imageUrl)
//                        .apply(RequestOptions().transform(RoundedCorners(20)))
//                        .into(imageView)
//                    nameTextView.text = name
//                }
//                override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
//                    Log.d("Yelp Error", "API Request Failed: $errorResponse")
//                }
//            })
//        }
})}

}


