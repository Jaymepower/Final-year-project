package com.example.finalyearproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.random.Random

class Game : AppCompatActivity() {

    val REDIRECT_URI: String = "com.example.finalyearproject://MainActivity/"
    val CLIENT_ID: String = "6573a6cbc21f424fad81067b6ce53fd0"
    val playlist : String = "https://api.spotify.com/v1/playlists/37i9dQZF1DWU4EQPjP9ZpS/"
    var ONE_LINE : Boolean = false
    val TWO_LINES : Boolean = false
    val FULL_HOUSE : Boolean = false

    private lateinit var name_list : ArrayList<String>
    private lateinit var played_songs : ArrayList<String>
    private lateinit var songs : ArrayList<Song>

    // media player
    lateinit var mediaPlayer: MediaPlayer


    var song1_click : Boolean = false  ; var song2_click : Boolean = false ; var song3_click : Boolean = false
    var song4_click : Boolean = false ;  var song5_click : Boolean = false ;  var song6_click : Boolean = false
    var song7_click : Boolean = false ;  var song8_click : Boolean = false ;  var song9_click : Boolean = false
    var song10_click : Boolean = false ;  var song11_click : Boolean = false ;  var song12_click : Boolean = false
    var song13_click : Boolean = false ;  var song14_click : Boolean = false ;  var song15_click : Boolean = false
    var song16_click : Boolean = false

    lateinit var song1 : Button ; lateinit var song2 : Button ; lateinit var song3 : Button ; lateinit var song4 : Button
    lateinit var song5 : Button ;  lateinit var song6 : Button ; lateinit var song7 : Button ;lateinit var song8 : Button
    lateinit var song9 : Button ; lateinit var song10 : Button ; lateinit var song11 : Button ; lateinit var song12 : Button
    lateinit var song13 : Button; lateinit var song14 : Button ; lateinit var song15 : Button ; lateinit var song16 : Button
    lateinit var now_playing : TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        now_playing = findViewById(R.id.now_playing)

        played_songs = ArrayList()
        name_list = ArrayList()
        songs = ArrayList()

        song1 = findViewById(R.id.song_1) ;song2 = findViewById(R.id.song_2);song3 = findViewById(R.id.song_3);song4 = findViewById(R.id.song_4); song5 = findViewById(R.id.song_5); song6 = findViewById(R.id.song_6)
        song7 = findViewById(R.id.song_7); song8 = findViewById(R.id.song_8);song9 = findViewById(R.id.song_9);song10 = findViewById(R.id.song_10);song11 = findViewById(R.id.song_11);song12 = findViewById(R.id.song_12)
        song13 = findViewById(R.id.song_13);song14 = findViewById(R.id.song_14);song15 = findViewById(R.id.song_15);song16 = findViewById(R.id.song_16)

        val genre: String? = intent.getStringExtra("genre")
        val token: String? = intent.getStringExtra("token")


        GlobalScope.launch(Dispatchers.Default) {
            // GET user info Spotify endpoint
            val url = URL(playlist)
            // Opening Https connection to set request
            val httpsURLConnection = withContext(Dispatchers.IO) {url.openConnection() as HttpsURLConnection }
            httpsURLConnection.requestMethod = "GET"
            // Adding the request parameters, in this case its the token needed in order to request user info
            httpsURLConnection.setRequestProperty("Authorization", "Bearer "+ token)
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = false

            // Response returned from request , we use a bufferedReader to read the return
            val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }
            withContext(Dispatchers.Main) {
                val jsonObject = JSONObject(response) // Playlist


                var name = jsonObject.getString("name")// Name of playlist

                var page = jsonObject.getJSONObject("tracks")//Pager object

                var song = page.getJSONArray("items")//Items array inside of pager object

                Log.i("Spotify API",name.toString())
                Log.i("Spotify API",page.toString())
                Log.i("Spotify API",song.toString())

                for (i in 0 until song.length()) {
                    val item = song.getJSONObject(i)

                    // Iterating the playlist for each track
                    var track = item.getJSONObject("track")

                    // name of song
                    var name = track.getString("name")
                    // Artist on song
                    var art_list = track.getJSONArray("artists")
                    var artist = art_list.getJSONObject(0)
                    var art_name = artist.getString("name")
                    // URI for streaming
                    var uri = track.getString("uri")
                    // 30 second spotify preview
                    var preview = track.getString("preview_url")
                    name_list.add(name)

                    // Mapping JSON data to a kotlin object
                    songs.add(Song(name,art_name,uri,preview))


                    Log.i("Spotify API",name)
                    Log.i("Spotify API",preview)

                }
                Log.i("Spotify API",name_list.toString())
                Log.i("Spotify API",songs.toString())

                generateCard()

            }

            playsong()
        }
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    fun boxclicked(v : View)
    {
        when(v.id)
        {
            R.id.song_1 -> if (!song1_click) {
                song1.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song1_click = true
            }
            else
            {
                song1.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song1_click = false
            }

            R.id.song_2 -> if (!song2_click) {
                song2.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song2_click = true
            }
            else
            {
                song2.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song2_click = false
            }

            R.id.song_3 -> if (!song3_click) {
                song3.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song3_click = true
            }
            else
            {
                song3.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song3_click = false
            }

            R.id.song_4 -> if (!song4_click) {
                song4.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song4_click = true
            }
            else
            {
                song4.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song4_click = false
            }

            R.id.song_5 -> if (!song5_click) {
                song5.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song5_click = true
            }
            else
            {
                song5.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song5_click = false
            }

            R.id.song_6 -> if (!song6_click) {
                song6.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song6_click = true
            }
            else
            {
                song6.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song6_click = false
            }

            R.id.song_7 -> if (!song7_click) {
                song7.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song7_click = true
            }
            else
            {
                song7.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song7_click = false
            }
            R.id.song_8 -> if (!song8_click) {
                song8.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song8_click = true
            }
            else
            {
                song8.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song8_click = false
            }

            R.id.song_9 -> if (!song9_click) {
                song9.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song9_click = true
            }
            else
            {
                song9.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song9_click = false
            }

            R.id.song_10 -> if (!song10_click) {
                song10.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song10_click = true
            }
            else
            {
                song10.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song10_click = false
            }

            R.id.song_11 -> if (!song11_click) {
                song11.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song11_click = true
            }
            else
            {
                song11.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song11_click = false
            }

            R.id.song_12 -> if (!song12_click) {
                song12.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song12_click = true
            }
            else
            {
                song12.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song12_click = false
            }
            R.id.song_13 -> if (!song13_click) {
                song13.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song13_click = true
            }
            else
            {
                song13.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song13_click = false
            }
            R.id.song_14 -> if (!song14_click) {
                song14.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song14_click = true
            }
            else
            {
                song14.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song14_click = false
            }
            R.id.song_15 -> if (!song15_click) {
                song15.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song15_click = true
            }
            else
            {
                song15.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song15_click = false
            }
            R.id.song_16 -> if (!song16_click) {
                song16.background = resources.getDrawable(R.drawable.red_border_but,resources.newTheme())
                song16_click = true
            }
            else
            {
                song16.background = resources.getDrawable(R.drawable.border_button,resources.newTheme())
                song16_click = false
            }

        }
    }

    private fun generateCard()
    {
        name_list.shuffle()
        song1.setText(name_list[0])
        song2.setText(name_list[1])
        song3.setText(name_list[2])
        song4.setText(name_list[3])
        song5.setText(name_list[4])
        song6.setText(name_list[5])
        song7.setText(name_list[6])
        song8.setText(name_list[7])
        song9.setText(name_list[8])
        song10.setText(name_list[9])
        song11.setText(name_list[10])
        song12.setText(name_list[11])
        song13.setText(name_list[12])
        song14.setText(name_list[13])
        song15.setText(name_list[14])
        song16.setText(name_list[15])

    }

    /*
    Originally i was going to use the Spotify Remote player to stream music to the app , there were two problems with this
    (1) The player would stream songs from the very beginning (0:00) meaning any song that had a long or incoherent intro would effect
    the overall speed and presentation of the game
    (2) The spotify player would appear in the users notification dropdown meaning they could essentially control and or break the game
    and i could not find a way around this

    Therefore i decided to extract MP3 urls from spotify track objects and stream them using the MediaPlayer library, this file is streamed
    interally through the app,neither the host or client can control it and it plays for a very convenient 30 seconds which is exactly
    how the game would be played in real life, spotify still handles the storage and streaming but it is much better suited the nature of
    the application
     */

    fun playsong()
    {
        for (i in 0 until songs.size)
        {
            var song : Song = songs[i]
            played_songs.add(song.name)
            now_playing.setText("Now Playing : " + song.name )

            val url = song.preview_url//preview url
             mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                        AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                )
                setDataSource(url)
                prepare() // might take long! (for buffering)
                start()
                 // 30 second wait in between songs
                Thread.sleep(32000)

            }

        } }

/*
Checking -
(1)All buttons in a line are clicked
(2)Each song has already been played
(3)Whether a line has already been secured
 */
    fun validateLines(v : View)
    {
        if(!ONE_LINE)
        {
            if(song1_click && song2_click && song3_click && song4_click)
            {
                if (played_songs.contains(song1.text) &&played_songs.contains(song2.text)
                        && played_songs.contains(song3.text) && played_songs.contains(song4.text)  )
                {
                        ONE_LINE = true
                    Toast.makeText(this@Game,"First Line Won",Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this@Game,"Sorry no line :( ",Toast.LENGTH_LONG).show()
                }
            }
        }


    }


}