package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                        //Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
                        val accessToken: String? = response.accessToken


                        val i = Intent(this, OptionsActivity::class.java)
                        i.putExtra("token", accessToken)

                        startActivity(i)
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