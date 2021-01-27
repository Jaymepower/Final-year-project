package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class JoinActvity : AppCompatActivity()
{

    lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_activity)

         name = intent.getStringExtra("name").toString()




    }


    fun join_game(v : View)
    {
        val i = Intent(this,GameClient::class.java)
        i.putExtra("name",name)
        startActivity(i)
    }
}