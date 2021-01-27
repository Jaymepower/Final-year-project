package com.example.finalyearproject

import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonObject
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
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.logging.SocketHandler
import kotlin.collections.ArrayList

class GameClient : AppCompatActivity()
{
    var ONE_LINE: Boolean = false
    var TWO_LINES: Boolean = false
    var FULL_HOUSE: Boolean = false
    var status : String = "SONG RECIEVED"
    var one_line_status : String = "One Line : -:-"
    var two_line_status : String = "Two Lines : -:- "
    var full_house_status : String = "Full house : -:-"
    lateinit var name : String

    var song1_click: Boolean = false;
    var song2_click: Boolean = false;
    var song3_click: Boolean = false
    var song4_click: Boolean = false;
    var song5_click: Boolean = false;
    var song6_click: Boolean = false
    var song7_click: Boolean = false;
    var song8_click: Boolean = false;
    var song9_click: Boolean = false
    var song10_click: Boolean = false;
    var song11_click: Boolean = false;
    var song12_click: Boolean = false
    var song13_click: Boolean = false;
    var song14_click: Boolean = false;
    var song15_click: Boolean = false
    var song16_click: Boolean = false

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
    lateinit var lines : AlertDialog
    lateinit var victory : LovelyStandardDialog

    private lateinit var song_list: ArrayList<Song>
    private lateinit var played_songs : ArrayList<String>

    lateinit var socket : Socket
    lateinit var input : BufferedReader
    lateinit var output : PrintWriter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        name = intent.getStringExtra("name").toString()

        lines = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()
        album_cover = findViewById(R.id.album_cover)
        now_playing = findViewById(R.id.now_playing)
        song_list = ArrayList<Song>()
        played_songs = ArrayList<String>()

        leaderboard = findViewById(R.id.popup_button)

        leaderboard.setOnClickListener{
            lines.setTitle("Leaderboard")
            lines.setMessage(one_line_status + "\n" + two_line_status + "\n" + full_house_status)
            lines.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", DialogInterface.OnClickListener { dialog, which -> lines.dismiss() })
            lines.show()
        }

        victory = LovelyStandardDialog(this,R.style.TintTheme)
                .setTopColor(getColor(R.color.purple))
                .setIcon(R.drawable.ic_trophy)
                .setTitle("Winner")
                .setTitleGravity(Gravity.CENTER)
                .setMessageGravity(Gravity.CENTER)
                .setMessage("Congratulations")
                .setPositiveButton("OK",View.OnClickListener {
                    victory.dismiss()
                })

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
            val output = PrintWriter(socket.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            Log.i("Client Connected", "Connected to Server ${socket.inetAddress.hostAddress}")
            val mes = input.readLine()
            Log.i("Client", mes)
            val songs = JSONArray(mes)
            Log.i("Client (JSON LIST)", songs.toString())

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
            generateCard(song_list)
            val first = input.readLine()
            val item = JSONObject(first)
            val album_url = item.getString("album_url")
            val artists = item.getString("artists")
            val name = item.getString("name")
            val preview_url = item.getString("preview_url")
            val song_uri = item.getString("song_uri")
            play_song(Song(name,artists,song_uri,preview_url,album_url))
            while(true)
            {

                val mes = input.readLine()
                if(mes.toString().startsWith("SONG"))
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


                }
                Log.i("Client", mes)
                output.println("Song Recieved")
            }





        }
    }


    fun generateCard(playlist : ArrayList<Song>)
    {

        val songs = playlist
      //  songs.shuffle()
        song1.setText(songs[0].name)
        song2.setText(songs[1].name)
        song3.setText(songs[2].name)
        song4.setText(songs[3].name)
        song5.setText(songs[4].name)
        song6.setText(songs[5].name)
        song7.setText(songs[6].name)
        song8.setText(songs[7].name)
        song9.setText(songs[8].name)
        song10.setText(songs[9].name)
        song11.setText(songs[10].name)
        song12.setText(songs[11].name)
        song13.setText(songs[12].name)
        song14.setText(songs[13].name)
        song15.setText(songs[14].name)
        song16.setText(songs[15].name)


    }

    fun play_song(song : Song)
    {
        played_songs.add(song.name)

        now_playing.setText(song.name + "-" + song.artists)

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
        when (v.id) {
            R.id.song_1 -> if (!song1_click) {
                song1.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song1_click = true
            } else {
                song1.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song1_click = false
            }

            R.id.song_2 -> if (!song2_click) {
                song2.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song2_click = true
            } else {
                song2.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song2_click = false
            }

            R.id.song_3 -> if (!song3_click) {
                song3.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song3_click = true
            } else {
                song3.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song3_click = false
            }

            R.id.song_4 -> if (!song4_click) {
                song4.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song4_click = true
            } else {
                song4.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song4_click = false
            }

            R.id.song_5 -> if (!song5_click) {
                song5.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song5_click = true
            } else {
                song5.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song5_click = false
            }

            R.id.song_6 -> if (!song6_click) {
                song6.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song6_click = true
            } else {
                song6.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song6_click = false
            }

            R.id.song_7 -> if (!song7_click) {
                song7.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song7_click = true
            } else {
                song7.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song7_click = false
            }
            R.id.song_8 -> if (!song8_click) {
                song8.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song8_click = true
            } else {
                song8.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song8_click = false
            }

            R.id.song_9 -> if (!song9_click) {
                song9.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song9_click = true
            } else {
                song9.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song9_click = false
            }

            R.id.song_10 -> if (!song10_click) {
                song10.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song10_click = true
            } else {
                song10.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song10_click = false
            }

            R.id.song_11 -> if (!song11_click) {
                song11.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song11_click = true
            } else {
                song11.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song11_click = false
            }

            R.id.song_12 -> if (!song12_click) {
                song12.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song12_click = true
            } else {
                song12.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song12_click = false
            }
            R.id.song_13 -> if (!song13_click) {
                song13.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song13_click = true
            } else {
                song13.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song13_click = false
            }
            R.id.song_14 -> if (!song14_click) {
                song14.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song14_click = true
            } else {
                song14.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song14_click = false
            }
            R.id.song_15 -> if (!song15_click) {
                song15.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song15_click = true
            } else {
                song15.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song15_click = false
            }
            R.id.song_16 -> if (!song16_click) {
                song16.background = resources.getDrawable(R.drawable.red_border_but, resources.newTheme())
                song16_click = true
            } else {
                song16.background = resources.getDrawable(R.drawable.border_button, resources.newTheme())
                song16_click = false
            }

        }
    }


    fun validateLines(v: View) {
        victory.show()
        Log.i("FAB", "clicked")
        if (!ONE_LINE) {
            if ((song1_click && song2_click && song3_click && song4_click) || (song5_click && song6_click && song7_click && song8_click)
                    || (song9_click && song10_click && song11_click && song12_click) || (song13_click && song14_click && song15_click && song16_click)) {

                if (played_songs.contains(song1.text) && played_songs.contains(song2.text)
                        && played_songs.contains(song3.text) && played_songs.contains(song4.text)) {
                    ONE_LINE = true
                    Log.i("FAB", "First line!")
                    status = "LINEONE-"+name
                    one_line_status = "First Line : " + name
                    victory.setMessage("Congratulations! " + name + " you have achieved the first line")
                    victory.show()

                }
                else if(played_songs.contains(song5.text) && played_songs.contains(song6.text)
                        && played_songs.contains(song7.text) && played_songs.contains(song8.text))
                {
                    ONE_LINE = true
                    Log.i("FAB", "First line!")
                    status = "LINEONE-"+name
                    one_line_status = "First Line : " + name
                    victory.setMessage("Congratulations! " + name + " you have achieved the first line")
                    victory.show()
                }
                else if(played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {
                    ONE_LINE = true
                    Log.i("FAB", "First line!")
                    status = "LINEONE-"+name
                    one_line_status = "First Line : " + name
                    victory.setMessage("Congratulations! " + name + " you have achieved the first line")
                    victory.show()
                }
                else if(played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    ONE_LINE = true
                    Log.i("FAB", "First line!")
                    status = "LINEONE-"+name
                    one_line_status = "First Line : " + name
                    victory.setMessage("Congratulations! " + name + " you have achieved the first line")
                    victory.show()
                }
                else
                {
                    Log.i("FAB", "No first line unfortunately")
                }
            }

        }
        else if(!TWO_LINES)
        {
            // Lines 1 and 2
            if((song1_click && song2_click && song3_click && song4_click && song5_click && song6_click && song7_click && song8_click ))
            {
                if (played_songs.contains(song1.text) && played_songs.contains(song2.text) && played_songs.contains(song3.text)
                        && played_songs.contains(song4.text) && played_songs.contains(song5.text) && played_songs.contains(song6.text)
                        && played_songs.contains(song7.text) && played_songs.contains(song8.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                    victory.setMessage("Congratulations! " + name + " you have achieved the two lines")
                    victory.show()
                }
            } // Lines 1 and 3
            else if(song1_click && song2_click && song3_click && song4_click && song9_click && song10_click && song11_click && song12_click)
            {
                if (played_songs.contains(song1.text) && played_songs.contains(song2.text) && played_songs.contains(song3.text)
                        && played_songs.contains(song4.text) && played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                }
            } // Lines 1 and 4
            else if(song1_click && song2_click && song3_click && song4_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song1.text) && played_songs.contains(song2.text) && played_songs.contains(song3.text)
                        && played_songs.contains(song4.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                }
            }// Lines 2 and 3
            else if(song5_click && song6_click && song7_click && song8_click && song9_click && song10_click && song11_click && song12_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                }
            } // Lines 2 and 4
            else if(song5_click && song6_click && song7_click && song8_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                }
            } // 3 and 4
            else if(song9_click && song10_click && song11_click && song12_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song9.text) && played_songs.contains(song10.text) && played_songs.contains(song11.text)
                        && played_songs.contains(song12.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    TWO_LINES = true
                    Log.i("FAB", "Second line!")
                    status = "LINETWO-"+name
                    two_line_status = "Two Lines : " + name
                }
            }

        } // End game
        else if(!FULL_HOUSE)
        {
            if(song1_click && song2_click && song3_click && song4_click && song5_click && song6_click && song7_click && song8_click
                    && song9_click && song10_click && song11_click && song12_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song9.text) && played_songs.contains(song10.text) && played_songs.contains(song11.text)
                        && played_songs.contains(song12.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text) && played_songs.contains(song9.text)
                        && played_songs.contains(song10.text) && played_songs.contains(song11.text) && played_songs.contains(song12.text)
                        && played_songs.contains(song13.text) && played_songs.contains(song14.text) && played_songs.contains(song15.text)
                        && played_songs.contains(song16.text))
                {
                    FULL_HOUSE = true
                    Log.i("FAB", "End Game!")
                    status = "END-"+name
                    full_house_status = "Full house : " + name
                }


            }
        }
    }


    override fun onStop() {
        super.onStop()
        try {
            socket.close()
            output.close()
            input.close()
        }
        catch (e: IOException) {
            // handler
        }

    }
}