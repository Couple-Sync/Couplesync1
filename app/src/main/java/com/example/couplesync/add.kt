package com.example.couplesync

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class AddFragment : Fragment() {

    var restaurantImageURL = ""
    var restaurantName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantImageView = view.findViewById<ImageView>(R.id.restaurantImage)
        val nameTextView = view.findViewById<TextView>(R.id.restaurantName)
        val thumbs_up = view.findViewById<ImageButton>(R.id.thumbs_up)
        val thumbs_down = view.findViewById<ImageButton>(R.id.thumbs_down)

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
        val apiKey = "Insert Key"
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

                Glide.with(requireContext())
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
