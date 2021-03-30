package com.jamie.finalyearproject

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.andremion.counterfab.CounterFab
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GameClient : AppCompatActivity()
{
    var mONE_LINE: Boolean = false
    var mTWO_LINES: Boolean = false
    var mFULL_HOUSE: Boolean = false
    var ONE_LINE: Boolean = false
    var TWO_LINES: Boolean = false
    var FULL_HOUSE: Boolean = false
    var playing = false
    var score = 0
    var noLines = 0
    var one_line_status : String = ""
    var two_line_status : String = ""
    var full_house_status : String = ""
    lateinit var username : String
    var react_content = ""
    var reactCount = 0
    lateinit var react_button : CounterFab
    lateinit var messageButton : CounterFab


    lateinit var song1: Button;
    lateinit var song2: Button;
    lateinit var song3: Button;
    lateinit var song4: Button
    lateinit var song5: Button;
    lateinit var song6: Button;
    lateinit var song7: Button;
    lateinit var song8: Button
    lateinit var song9: Button;
    lateinit var song10: Button;
    lateinit var song11: Button;
    lateinit var song12: Button
    lateinit var song13: Button;
    lateinit var song14: Button;
    lateinit var song15: Button;
    lateinit var song16: Button
    lateinit var now_playing: TextView
    lateinit var album_cover : ImageView
    lateinit var leaderboard : FloatingActionButton
    lateinit var card : Card

    lateinit var lines : AlertDialog
    lateinit var victory : LovelyStandardDialog
    lateinit var chatDialog: ChatDialog
    lateinit var firebaseLogger: FirebaseLogger

    private lateinit var song_list: ArrayList<Song>
    private lateinit var played_songs : ArrayList<String>
    private lateinit var reactions : HashMap<String,String>
    private lateinit var psCyclerList : ArrayList<Song>

    lateinit var socket : Socket
    lateinit var input : BufferedReader
    lateinit var output : PrintWriter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        //username = intent.getStringExtra("name").toString()
        username = "Other user"

        lines = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()
        album_cover = findViewById(R.id.album_cover)
        now_playing = findViewById(R.id.now_playing)
        song_list = ArrayList()
        played_songs = ArrayList()
        psCyclerList = ArrayList()
        reactions = HashMap()
        react_button = findViewById(R.id.reaction_button)
        messageButton = findViewById(R.id.chat_button)
        react_button.count = 0

        chatDialog = ChatDialog()
        leaderboard = findViewById(R.id.popup_button)
        leaderboard.setOnClickListener{
            LeaderboardDialog().build(this,one_line_status,two_line_status,full_house_status).show()
        }

        victory = LovelyStandardDialog(this,R.style.TintTheme)
                .setTopColor(getColor(R.color.purple))
                .setIcon(R.drawable.bingo_smaller)
                .setTitle("Winner")
                .setTitleGravity(Gravity.CENTER)
                .setMessageGravity(Gravity.CENTER)
                .setPositiveButton("OK",View.OnClickListener {
                    victory.dismiss()
                })


        album_cover.setOnClickListener {
            val dialog = PlayedSongsDialog().build(this,psCyclerList,psCyclerList.size)
            dialog.show()
        }

        card = Card(this,this@GameClient)

        connect()

        song1 = findViewById(R.id.song_1);song2 = findViewById(R.id.song_2);song3 = findViewById(R.id.song_3);song4 = findViewById(R.id.song_4); song5 = findViewById(R.id.song_5); song6 = findViewById(R.id.song_6)
        song7 = findViewById(R.id.song_7); song8 = findViewById(R.id.song_8);song9 = findViewById(R.id.song_9);song10 = findViewById(R.id.song_10);song11 = findViewById(R.id.song_11);song12 = findViewById(R.id.song_12)
        song13 = findViewById(R.id.song_13);song14 = findViewById(R.id.song_14);song15 = findViewById(R.id.song_15);song16 = findViewById(R.id.song_16)

        Log.i("Client","Client Running")

    }

    fun connect()
    {
        Log.i("Client","Client Opening Socket")
        GlobalScope.launch (Dispatchers.IO){
                socket = Socket("10.0.2.2", 5000)
                playing = true
                output = PrintWriter(socket.getOutputStream(), true)
                input = BufferedReader(InputStreamReader(socket.getInputStream()))
                Log.i("Client Connected", "Connected to Server ${socket.inetAddress.hostAddress}")
                //output.println(username) // Send name to server
                val mes = input.readLine() ////////////////////////
                if(mes != null)
                {
                    val songs = JSONArray(mes)

                    for (i in 0 until songs.length()) {
                        val item = songs.getJSONObject(i)
                        Log.i("Client", item.toString())
                        val album_url = item.getString("album_url")
                        val artists = item.getString("artists")
                        val name = item.getString("name")
                        val preview_url = item.getString("preview_url")
                        val song_uri = item.getString("song_uri")
                        song_list.add(Song(name,artists,song_uri,preview_url,album_url))

                    }
                    card.printCardbySong(song_list)
                }


                var status = "Hello Server"
                while(true)
                {
                    val mes = input.readLine() ///////////////////////////////////

                    if(mes != null)
                    {
                        if(mes.startsWith("SONG"))
                        {
                            Log.i("Client", "Processing song")
                            val song = mes.substring(4,mes.length)
                            val n_item = JSONObject(song)
                            val n_album_url = n_item.getString("album_url")
                            val n_artists = n_item.getString("artists")
                            val n_name = n_item.getString("name")
                            val n_preview_url = n_item.getString("preview_url")
                            val n_song_uri = n_item.getString("song_uri")

                            play_song(Song(n_name,n_artists,n_song_uri,n_preview_url,n_album_url))
                            psCyclerList.add(Song(n_name,n_artists,n_song_uri,n_preview_url,n_album_url))
                            Log.i("Client", mes)
                        }


                    }

                    val change = input.readLine() ///////////////////////////
                    if(change != null)
                    {
                        if (change.startsWith("ONE"))
                        {
                            ONE_LINE = true
                            val n_line = change.substring(3,change.length)
                            one_line_status = n_line
                        }
                        else if(change.startsWith("TWO"))
                        {
                            TWO_LINES = true
                            val n_line = change.substring(3,change.length)
                            two_line_status = n_line
                        }
                        else if(!FULL_HOUSE && change.startsWith("FULL"))
                        {
                            FULL_HOUSE = true
                            val n_line = change.substring(3,change.length)
                            full_house_status = n_line
                            firebaseLogger = FirebaseLogger()
                            firebaseLogger.publishDetails(this@GameClient,noLines,score)
                            runOnUiThread { Runnable{LeaderboardDialog().build(this@GameClient,one_line_status,two_line_status,full_house_status).show() }.run()  }
                        }


                    }

                    if(mONE_LINE)
                    {
                        status = "ONE$username"
                    }

                    if(mTWO_LINES)
                    {
                        status = "TWO$username"
                    }

                    if(mFULL_HOUSE)
                    {
                        status = "FULL$username"
                    }

                    output.println(status)/////////////////////////
                    status = "NO_CHANGE"

                    if( mes == "CLOSE" )
                    {
                        Log.i("Client",mes)
                        break
                    }
                    val react = input.readLine()
                    if(react != null && !react.isEmpty() )
                    {
                        val s_react = react.split("+")
                        when(s_react[1])
                        {
                            "HAPPY" -> {
                                if(!reactions.get(s_react[0]).equals("Loves"))
                                {
                                    runOnUiThread {react_button.increase()  }
                                }
                                reactions.put(s_react[0], "Loves")
                            }
                            "BORED" -> {
                                if(!reactions.get(s_react[0]).equals("Hates"))
                                {
                                    runOnUiThread {react_button.increase()  }
                                }
                                reactions.put(s_react[0],"Hates")
                            }
                        }
                    }

                    output.println(react_content)



                    val messagesString = input.readLine()
                    if(messagesString != "")
                    {
                        val message = Gson().fromJson(messagesString,Message::class.java)
                        if (!chatDialog.messages.contains(message))
                        {
                            runOnUiThread { messageButton.increase()
                             chatDialog.notifyChange()
                            }
                            chatDialog.messages.add(message)

                        }
                    }

                    var last_message = ""
                    if(chatDialog.myLog.isNotEmpty())
                    {
                        last_message = Gson().toJson(chatDialog.myLog[chatDialog.myLog.size-1])
                    }
                    output.println(last_message)



                }



            try {
                socket.close()
                input.close()
                output.close()
                Log.i("Client Socket", "CLOSED")
            }catch (e : IOException)
            {
                Log.i("Client Socket", "Did not close successfully")
            }



        }
    }



    fun play_song(song : Song)
    {
        played_songs.add(song.name)
        runOnUiThread { react_button.count = 0  }
        react_content = ""
        reactions.clear()
        now_playing.text = "${song.name} - ${song.artists}"

        GlobalScope.launch {
            this@GameClient.runOnUiThread {
                Log.i("LOADING IMAGE", song.album_url)
                Picasso.get()
                        .load(song.album_url)
                        .into(album_cover)
            }
        }
    }


    fun boxclicked(v: View) {
        card.checkBox(v)
    }


    fun validateLines(v: View) {
        when(card.validateCard(played_songs))
        {
            "ONE" -> {
                if(!ONE_LINE)
                {
                    victory.show()
                    ONE_LINE = true
                    noLines += 1
                    score += 25
                }
            }
            "TWO" -> {
                if(!TWO_LINES)
                {
                    victory.show()
                    TWO_LINES = true
                    noLines += 2
                    score += 50
                }
            }
            "FULL" -> {
                if(!FULL_HOUSE)
                {
                    victory.show()
                    FULL_HOUSE = true
                    firebaseLogger = FirebaseLogger()
                    firebaseLogger.publishDetails(this, noLines,score)
                    LeaderboardDialog().endGame(this@GameClient,one_line_status,two_line_status,full_house_status,noLines,score,reactCount,played_songs.size).show()

                }
            }

        }
    }

    override fun onBackPressed() {
        if(playing)
        {
            Toast.makeText(this@GameClient , "Leaving the session will stop the game!",Toast.LENGTH_LONG).show()
        }
        else
        {
            super.onBackPressed()
        }

    }

    fun reaction(v :View)
    {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.reaction_layout)
        val text = dialog.findViewById<TextView>(R.id.reaction_list)
        val song_title = dialog.findViewById<TextView>(R.id.song_title)
        if (played_songs.size != 0 )
        {
            song_title.text = played_songs[played_songs.size-1]
        }
        var temp = ""
        for((x,y) in reactions)
        {
            var emoji = ""
            when(y){
                "Loves" -> emoji = String(Character.toChars(0x1F60A))
                "Hates" -> emoji = String(Character.toChars(0x1F612))
            }
            temp += "$x : $emoji \n"
        }
        if(temp == "")
        {
            temp = "Be the first to react!"
        }
        text.text = temp
        val happy = dialog.findViewById<FloatingActionButton>(R.id.happy_button)
        happy.setOnClickListener {


            if(reactions.get(username) == null)
            {
                reactCount ++
            }


            reactions.put(username,"Loves")
            react_content = "$username+HAPPY"
            dialog.dismiss()
        }
        val bored = dialog.findViewById<FloatingActionButton>(R.id.bored_button)
        bored.setOnClickListener {

            if(reactions.get(username) == null)
            {
                reactCount ++
            }

            reactions.put(username,"Hates")
            react_content = "$username+BORED"
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }



    override fun onDestroy() {
        try {
            socket.close()
            output.close()
            input.close()
        }
        catch (e: IOException) {
        }
        super.onDestroy()

    }


    fun chatBox(v : View)
    {
        runOnUiThread { messageButton.count = 0 }
        val dialog = chatDialog.Build(this)

        chatDialog.sendButton.setOnClickListener {
            if(chatDialog.text.text.isNotEmpty())
            {
                chatDialog.messages.add(Message(username,chatDialog.text.text.toString(),System.currentTimeMillis()))
                chatDialog.myLog.add(Message(username,chatDialog.text.text.toString(),System.currentTimeMillis()))
                chatDialog.text.setText("")
                chatDialog.notifyChange()
            }

        }
        dialog.show()

    }
}