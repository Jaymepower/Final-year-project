package com.jamie.finalyearproject


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
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
    lateinit var scoreField : TextView ; lateinit var lineField : TextView

    lateinit var profile_pic : ImageView
    lateinit var progress : Dialog


    lateinit var id : String
    lateinit var logRef : DatabaseReference
    lateinit var userRef : DatabaseReference
    lateinit var games : ArrayList<GameLog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        progress = Dialog(this)
        progress.setCancelable(true)
        progress.setContentView(R.layout.loading_dialog)
        progress.show()



        val uidPref = getSharedPreferences("uid", Context.MODE_PRIVATE)
        id = uidPref.getString("uid",null).toString()


        val preferences = getSharedPreferences("bearerToken", Context.MODE_PRIVATE)
        access_token = preferences.getString("token", null).toString()

        profile_pic = findViewById(R.id.profile_pic)
        username_field = findViewById(R.id.profile_name)
        email_field = findViewById(R.id.profile_email)
        country_field = findViewById(R.id.profile_country)
        follower_field = findViewById(R.id.profile_followers)
        scoreField = findViewById(R.id.profile_score)
        lineField = findViewById(R.id.profile_lines)




        logRef = FirebaseDatabase.getInstance().getReference("Log")

        userRef = FirebaseDatabase.getInstance().getReference("Users")

        games = ArrayList<GameLog>()



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





        userRef.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children)
                {
                    val uid = snap.child("user_id").value.toString()

                    if(uid == id)
                    {
                        val score = snap.child("score").value.toString()
                        val lines = snap.child("lines").value.toString()

                        scoreField.text = score
                        lineField.text = lines

                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })








    }



    fun showLog(v : View)
    {

        progress.show()

        logRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for(snap in snapshot.children)
                {
                    val uid = snap.child("user_id").value.toString()
                    Log.i("Profile",uid)

                    if(true) {

                        val genre = snap.child("genre").value.toString()
                        val subgenre = snap.child("subgenre").value.toString()
                        val sp = snap.child("songsPlayed").value.toString().toInt()
                        val date = snap.child("date").value.toString()
                        val lines = snap.child("lines").value.toString().toInt()
                        val score = snap.child("score").value.toString().toInt()
                        val reacts = snap.child("reactions").value.toString().toInt()
                        val win = snap.child("win").value.toString().toBoolean()

                        Log.i("Profile",genre)
                        games.add(GameLog(id,genre,subgenre,sp,date,lines,score,reacts,win))
                    }

                }
                progress.dismiss()
                val dialog = HistoryDialog().build(this@Profile,games)
                dialog.show()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }









}



