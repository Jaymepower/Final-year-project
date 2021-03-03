package com.example.finalyearproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlin.concurrent.thread
import kotlin.random.Random

class JoinActvity : AppCompatActivity()
{

    lateinit var preferences: SharedPreferences
    lateinit var name : String
    lateinit var tipText : TextView
    val tips = arrayOf("Touch the album cover to see played songs","Make sure you are on the same network as the host","Games can be played up to 4 players!",
    "Make sure your songs are marked before clicking bingo")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_activity)

        val fadein  = AlphaAnimation(0.0f,1.0f)
        tipText = findViewById(R.id.tips)
        name = intent.getStringExtra("name").toString()

        preferences = getSharedPreferences("users_id",Context.MODE_PRIVATE)



        thread (start = true) {

            var current = 0
            while (true)
            {
                var index = Random.nextInt(0,3)
                if(index != current)
                {
                    current = index
                    tipText.startAnimation(fadein)
                    fadein.duration = 2000
                    fadein.fillAfter = true
                    tipText.setText(tips[current])
                    Thread.sleep(6000)

                }
            }
        }
    }


    fun join_game(v : View)
    {
        val i = Intent(this,GameClient::class.java)
        i.putExtra("name",name)
        startActivity(i)
    }
}