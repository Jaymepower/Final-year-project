package com.jamie.finalyearproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {

    // The SDK request code
    val REQUEST_CODE: Int  = 1337 ;
    // Redirect URI to help find app after login
    val REDIRECT_URI: String ="com.example.finalyearproject://MainActivity/"
    // App id
    val CLIENT_ID: String = "6573a6cbc21f424fad81067b6ce53fd0"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val video : VideoView = findViewById(R.id.videoView)
        val uri = Uri.parse("android.resource://"+ packageName +"/"+R.raw.bingo_backround)
        video.setVideoURI(uri)
      //  video.start()
        video.setOnCompletionListener { video.start() }

    }

    fun login(v: View)
    {
        // instantiate builder with id , token request and redirect uri
        var builder: AuthenticationRequest.Builder
                = AuthenticationRequest.Builder(CLIENT_ID,AuthenticationResponse.Type.TOKEN,REDIRECT_URI)

        // Define scope of permissions to use API
        builder.setScopes(arrayOf("user-read-email","streaming","user-read-currently-playing",
                "user-read-private","user-read-playback-state","app-remote-control","user-modify-playback-state","playlist-read-collaborative","playlist-read-private"));

        // Builds request
        var request: AuthenticationRequest = builder.build();

        // Opens login activity using activity context , request code , and request object
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


          }


    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent);

        val response: AuthenticationResponse = AuthenticationClient.getResponse(resultCode, intent);


                when (response.type) {

                    AuthenticationResponse.Type.TOKEN-> {
                        Log.i("LoginActivity", "is equals to TOKEN")

                        val accessToken: String? = response.accessToken


                        val preferences = getSharedPreferences("bearerToken", Context.MODE_PRIVATE)
                        var token = preferences.edit()
                        token.putString("token",accessToken)
                        token.apply()

                        val uid_pref = getSharedPreferences("uid",Context.MODE_PRIVATE)
                        val uid = uid_pref.edit()


                        val username_preferences = getSharedPreferences("username", Context.MODE_PRIVATE)
                        val editor = username_preferences.edit()


                        val i = Intent(this, OptionsActivity::class.java)
                        i.putExtra("token", accessToken)

                        GlobalScope.launch(Dispatchers.Default) {
                            // GET user info Spotify endpoint
                            val url = URL("https://api.spotify.com/v1/me")
                            // Opening Https connection to set request
                            val httpsURLConnection = withContext(Dispatchers.IO) {url.openConnection() as HttpsURLConnection }
                            httpsURLConnection.requestMethod = "GET"
                            // Adding the request parameters, in this case its the token needed in order to request user info
                            httpsURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken)
                            httpsURLConnection.doInput = true
                            httpsURLConnection.doOutput = false

                            // Response returned from request , we use a bufferedReader to read the return
                            val response = httpsURLConnection.inputStream.bufferedReader()
                                    .use { it.readText() }
                            withContext(Dispatchers.Main) {
                                val jsonObject = JSONObject(response)

                                // Spotify Id
                                val id = jsonObject.getString("id")
                                Log.d("Spotify Id :", id)
                                uid.putString("uid",id)
                                uid.apply()


                                // Spotify Display Name
                                val displayName = jsonObject.getString("display_name")
                                Log.d("Spotify Display Name :", displayName)
                                editor.putString("username", displayName)
                                editor.apply()


                                // Spotify Email
                                val email = jsonObject.getString("email")
                                Log.d("Spotify Email :", email)


                                val user = User(id,displayName, email, 0, 0,false,false,false,false,false)

                                val database = FirebaseDatabase.getInstance().reference
                                val userRef : DatabaseReference = database.child("Users")

                                userRef.addListenerForSingleValueEvent(object : ValueEventListener {

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (!snapshot.exists())
                                            userRef.push().setValue(user)


                                        startActivity(i)

                                        Log.i("Firebase : ", "Exisiting user found")
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.i("Firebase : ", "new user added")
                                    }


                                })




                            }

                        }




                    }

                    AuthenticationResponse.Type.ERROR -> {
                        Toast.makeText(this, "Error Logging in ", Toast.LENGTH_SHORT).show()

                    }

                    AuthenticationResponse.Type.UNKNOWN -> {
                        throw UnknownError()
                    }

                    else -> throw Exception()
                }

            }
}