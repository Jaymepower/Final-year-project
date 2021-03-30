package com.jamie.finalyearproject


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class Profile : AppCompatActivity() {

    lateinit var access_token: String
     var img_url: String = ""

    lateinit var username_field : TextView ; lateinit var email_field : TextView
    lateinit var country_field : TextView ; lateinit var follower_field : TextView

    lateinit var profile_pic : ImageView
    lateinit var progress : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        progress = Dialog(this)
        progress.setCancelable(true)
        progress.setContentView(R.layout.loading_dialog)
        progress.show()

        access_token = intent.getStringExtra("token").toString()

        profile_pic = findViewById(R.id.profile_pic)
        username_field = findViewById(R.id.profile_name)
        email_field = findViewById(R.id.profile_email)
        country_field = findViewById(R.id.profile_country)
        follower_field = findViewById(R.id.profile_followers)

        // GlobalScope allows asynchronous threads to run outside of UI thread
        GlobalScope.launch(Dispatchers.Default) {
            // GET user info Spotify endpoint
            val url = URL("https://api.spotify.com/v1/me")
            // Opening Https connection to set request
            val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
            httpsURLConnection.requestMethod = "GET"
            // Adding the request parameters, in this case its the token needed in order to request user info
            httpsURLConnection.setRequestProperty("Authorization", "Bearer " + access_token)
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = false

            // Response returned from request , we use a bufferedReader to read the return
            val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }
            withContext(Dispatchers.Main) {
                val jsonObject = JSONObject(response)

                Log.i("Profile",jsonObject.toString())

                val name : String = jsonObject.getString("display_name")
                Log.i("Profile",name)
                username_field.text = name

                val email: String = jsonObject.getString("email")
                Log.i("Profile", email)
                email_field.text = email

                val country : String = jsonObject.getString("country")
                Log.i("Profile",country)
                country_field.text = country

                val followers_obj = jsonObject.getJSONObject("followers")
                Log.i("Profile",followers_obj.toString())
                val followers = followers_obj.getString("total")
                follower_field.setText(followers)

                val images = jsonObject.getJSONArray("images")
                var pic = images[0] as JSONObject
                img_url = pic.getString("url")
                Log.i("Profile",img_url)

                Picasso.get().load(img_url).into(profile_pic ,object: com.squareup.picasso.Callback
                {
                    override fun onSuccess() {
                      progress.dismiss()
                    }

                    override fun onError(e: java.lang.Exception?) {
                        //do smth when there is picture loading error
                    }
                })



            }


        }



    }
}



