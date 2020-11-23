package com.example.finalyearproject

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.net.URLEncoder.*
import javax.net.ssl.HttpsURLConnection
import javax.security.auth.callback.Callback



class OptionsActivity : AppCompatActivity() {

    lateinit var token_store : SharedPreferences

    private lateinit var database: DatabaseReference

    // User variables
    var id = ""
    var displayName = ""
    var email = ""


    val client = OkHttpClient()
    var user = ""

    lateinit var access_token: String
    lateinit var userInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_activity)

        // Recieving the access token in order to issue GET requests for app perimissions
        var userInfo: TextView = findViewById(R.id.userinfo)
        access_token = intent.getStringExtra("token").toString()



        database = FirebaseDatabase.getInstance().reference

            // GlobalScope allows asynchronous threads to run
            GlobalScope.launch(Dispatchers.Default) {
                // GET user info Spotify endpoint
                val url = URL("https://api.spotify.com/v1/me")
                // Opening Https connection to set request
                val httpsURLConnection = withContext(Dispatchers.IO) {url.openConnection() as HttpsURLConnection }
                httpsURLConnection.requestMethod = "GET"
                // Adding the request parameters, in this case its the token needed in order to request user info
                httpsURLConnection.setRequestProperty("Authorization", "Bearer "+access_token)
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = false

                // Response returned from request , we use a bufferedReader to read the return
                val response = httpsURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }
                withContext(Dispatchers.Main) {
                    val jsonObject = JSONObject(response)

                    // Spotify Id
                    id = jsonObject.getString("id")
                    Log.d("Spotify Id :", id)

                    // Spotify Display Name
                     displayName = jsonObject.getString("display_name")
                    Log.d("Spotify Display Name :", displayName)

                    // Spotify Email
                     email = jsonObject.getString("email")
                    Log.d("Spotify Email :", email)



                    Log.d("Spotify Access token :", access_token)

                    // Extracting the users now to only display the first
                    var firstName :CharSequence = displayName

                    firstName = firstName.split(" ")[0]

                    userInfo.setText("Hi " + firstName )


                    addUser(id,displayName,email)

                }

            }







        }





    fun addUser(userID:String , name:String , email:String)
    {
        val user = User(userID,name,email)

       val userRef : DatabaseReference = database.child("Users").child(userID)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(!snapshot.exists())
                    database.child("Users").setValue(user)
                    Log.i("Firebase : ","Exisiting user found")
            }

            override fun onCancelled(error: DatabaseError)
            {
                Log.i("Firebase : ","new user added")
            }


        })
    }



    fun createGame(v : View)
    {
        val i : Intent = Intent(this,Hostactivity::class.java)
        i.putExtra("token", access_token)
        startActivity(i)
    }

}





