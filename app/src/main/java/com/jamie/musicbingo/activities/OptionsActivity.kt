package com.jamie.musicbingo.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jamie.musicbingo.*


class OptionsActivity : AppCompatActivity() {


    lateinit var preferences: SharedPreferences


    // User variables
    var id = ""
    var displayName = ""
    var user = ""

    lateinit var access_token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options_activity)


        //FirebaseLogger().logMockUser()



        access_token = intent.getStringExtra("token").toString()


        val uidPref = getSharedPreferences("uid", Context.MODE_PRIVATE)
        id = uidPref.getString("uid",null).toString()


        preferences = getSharedPreferences("bearerToken", Context.MODE_PRIVATE)
        val pref_token = preferences.getString("token", null)

        if(pref_token != null)
        {
            Log.i("preferences",pref_token)
            access_token = pref_token
        }
        else
        {
            Log.i("preferences","token is null")
        }



        }









    fun createGame(v: View)
    {
        val i = Intent(this, HostActivity::class.java)
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

    fun gotoStore(v: View)
    {
        val i = Intent(this, Store::class.java)
        i.putExtra("uid",id)
        startActivity(i)
    }

    fun gotoInsights(v : View)
    {
        val i = Intent(this, Insights::class.java)
        i.putExtra("uid",id)
        startActivity(i)
    }

    fun gotoCatalogue(v : View)
    {
        val i = Intent(this, GenreList::class.java)
        startActivity(i)
    }



}





