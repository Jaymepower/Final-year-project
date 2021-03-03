package com.example.finalyearproject

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class OptionsActivity : AppCompatActivity() {

    lateinit var token_store : SharedPreferences
    lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var preferences: SharedPreferences
    lateinit var progress : Dialog


    // User variables
    var id = ""
    var displayName = ""
    var email = ""
    var user = ""

    lateinit var access_token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_activity)

        progress = Dialog(this)
        progress.setCancelable(true)
        progress.setContentView(R.layout.loading_dialog)
        progress.show()

        preferences = getSharedPreferences("users_id", Context.MODE_PRIVATE)
        var editor = preferences.edit()


        // Recieving the access token in order to issue GET requests for app perimissions
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
                httpsURLConnection.setRequestProperty("Authorization", "Bearer " + access_token)
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
                    editor.putString("user_id", id)
                    editor.commit()

                    // Spotify Display Name
                     displayName = jsonObject.getString("display_name")
                    Log.d("Spotify Display Name :", displayName)
                    progress.dismiss()

                    // Spotify Email
                     email = jsonObject.getString("email")
                    Log.d("Spotify Email :", email)


                    Log.d("Spotify Access token :", access_token)

                    // Extracting the users now to only display the first
                    var firstName :CharSequence = displayName


                    addUser(id, displayName, email)

                }

            }







        }





    fun addUser(userID: String, name: String, email: String)
    {
        val user = User(userID, name, email, 0, 0)

       val userRef : DatabaseReference = database.child("Users")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists())
                    userRef.push().setValue(user)
                Log.i("Firebase : ", "Exisiting user found")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Firebase : ", "new user added")
            }


        })
    }



    fun createGame(v: View)
    {
        val i = Intent(this, Hostactivity::class.java)
        i.putExtra("token", access_token)
        i.putExtra("name", displayName)
        startActivity(i)
    }

    fun profile(v: View)
    {
        val i = Intent(this, Profile::class.java)
        i.putExtra("token", access_token)
        startActivity(i)
    }

    fun JoinGame(v: View)
    {
        val i = Intent(this, JoinActvity::class.java)
        i.putExtra("token", access_token)
        i.putExtra("name", displayName)
        startActivity(i)
    }

    fun howTo(v: View)
    {
        val i = Intent(this, HowTo::class.java)
        startActivity(i)
    }







}





