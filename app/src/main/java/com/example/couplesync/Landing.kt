package com.example.couplesync

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.RequestHeaders
import okhttp3.Headers

class Landing : AppCompatActivity() {
    var restaurantImageURL = ""
    var restaurantName = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add)

        val restaurantImageView = findViewById<ImageView>(R.id.restaurantImage)
        val nameTextView = findViewById<TextView>(R.id.restaurantName)
        val thumbs_up = findViewById<ImageButton>(R.id.thumbs_up)
        val thumbs_down = findViewById<ImageButton>(R.id.thumbs_down)

        getRandomRestaurant(restaurantImageView, nameTextView)

        thumbs_up.setOnClickListener {
            getRandomRestaurant(restaurantImageView, nameTextView)
        }
        thumbs_down.setOnClickListener {
            getRandomRestaurant(restaurantImageView, nameTextView)
        }
    }

    private fun getRandomRestaurant(imageView: ImageView, nameTextView: TextView) {
        val client = AsyncHttpClient()
        val apiKey = "H1XoYkdmEbtFuydLRM9D8A0cubbOJAqKL2K3KoGxIzgTKqptu_DYyefGJO_OGEh5Hso3SQZMnoxlSti-nP7zuOEuCK5DCLoMVtK-jV6VXiPj51euyN4knvbC9XIhZnYx"
        val url = "https://api.yelp.com/v3/businesses/search?location=NewYork&limit=50"

        val headers = RequestHeaders()
        headers.set("Authorization", "Bearer $apiKey")
        client.get(url, headers, null, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Yelp", "API Request Success")
                val businesses = json.jsonObject.getJSONArray("businesses")
                val randomIndex = (0 until businesses.length()).random()
                val randomRestaurant = businesses.getJSONObject(randomIndex)

                val name = randomRestaurant.getString("name")
                val imageUrl = randomRestaurant.getString("image_url")

                Glide.with(this@Landing)
                    .load(imageUrl)
                    .apply(RequestOptions().transform(RoundedCorners(20)))
                    .into(imageView)
                nameTextView.text = name
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Yelp Error", "API Request Failed: $errorResponse")
            }
        })
    }
}
