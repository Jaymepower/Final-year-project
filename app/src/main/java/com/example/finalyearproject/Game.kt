package com.example.finalyearproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.icu.number.NumberFormatter.with
import android.media.AudioAttributes
import org.jetbrains.anko.toast
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.gson.Gson
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.ThreadPoolExecutor
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread


class Game : AppCompatActivity() {

            // playlist structure : "https://api.spotify.com/v1/playlists/15QCkUofmu6Gga9saOhENp/"
            val REDIRECT_URI: String = "com.example.finalyearproject://MainActivity/"
            val CLIENT_ID: String = "6573a6cbc21f424fad81067b6ce53fd0"
            val playlist: String = "https://api.spotify.com/v1/playlists/15QCkUofmu6Gga9saOhENp/"
            var song_index = 2
            var next : Boolean = false
            var ONE_LINE: Boolean = false
            var TWO_LINES: Boolean = false
            var FULL_HOUSE: Boolean = false
            var one_line_status : String = "One Line : -:-"
            var two_line_status : String = "Two Lines : -:- "
            var full_house_status : String = "Full house : -:-"


            private lateinit var players : ArrayList<String>
            private lateinit var played_songs: ArrayList<String>
            private lateinit var songs: ArrayList<Song>

            // media player
            lateinit var mediaPlayer: MediaPlayer

            lateinit var genre_ref : DatabaseReference
            lateinit var output : PrintWriter

            lateinit var lines : AlertDialog
            lateinit var victory : AlertDialog


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

            lateinit var name : String



            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.game_activity)
                now_playing = findViewById(R.id.now_playing)
                album_cover = findViewById(R.id.album_cover)


                name = intent.getStringExtra("name").toString()
                played_songs = ArrayList()
                songs = ArrayList()

                lines = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()
                victory = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()

                song1 = findViewById(R.id.song_1);song2 = findViewById(R.id.song_2);song3 = findViewById(R.id.song_3);song4 = findViewById(R.id.song_4); song5 = findViewById(R.id.song_5); song6 = findViewById(R.id.song_6)
                song7 = findViewById(R.id.song_7); song8 = findViewById(R.id.song_8);song9 = findViewById(R.id.song_9);song10 = findViewById(R.id.song_10);song11 = findViewById(R.id.song_11);song12 = findViewById(R.id.song_12)
                song13 = findViewById(R.id.song_13);song14 = findViewById(R.id.song_14);song15 = findViewById(R.id.song_15);song16 = findViewById(R.id.song_16)

                val genre: String? = intent.getStringExtra("genre")
                val sub_genre : String? = intent.getStringExtra("subgenre")
                val token: String? = intent.getStringExtra("token")


               genre_ref = FirebaseDatabase.getInstance().getReference("Playlists")

                if (genre != null) {

                    genre_ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot)
                        {

                            for(snap in snapshot.children)
                            {
                                val name = snap.key.toString()
                                Log.i("Firebase",name)

                                if(name.equals(genre))
                                {
                                    Log.i("Genre Found",name)
                                    for(sub in snap.children) {
                                        val sub_g = sub.key.toString()
                                        Log.i("Sub Genres", sub_g)
                                        Log.i("Playlist ID", sub.value.toString())

                                        if (sub_g.equals(sub_genre))
                                        {
                                            Log.i("Sub Genre Found", sub_g)
                                            getPlaylist(token.toString(),sub.value.toString())
                                        }
                                    }
                                }

                            }


                        }

                        override fun onCancelled(error: DatabaseError)
                        {
                            TODO("Not yet implemented")
                        }
                    })


                }
                else
                {
                    Toast.makeText(this,"Playlist Failed to load",Toast.LENGTH_LONG).show()
                }



            }



            fun getPlaylist(token : String,playlist_id : String)
            {
                GlobalScope.launch(Dispatchers.Default) {
                    // GET user info Spotify endpoint
                    val url = URL("https://api.spotify.com/v1/playlists/" + playlist_id +"/")
                    // Opening Https connection to set request
                    val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                    httpsURLConnection.requestMethod = "GET"
                    // Adding the request parameters, in this case its the token needed in order to request user info
                    httpsURLConnection.setRequestProperty("Authorization", "Bearer " + token)
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
                        Log.i("Spotify API", name.toString())
                        Log.i("Spotify API", page.toString())
                        Log.i("Spotify API", song.toString())
                        for (i in 0 until song.length()) {
                            val item = song.getJSONObject(i)
                            // Iterating the playlist for each track
                            var track = item.getJSONObject("track") // name of song
                            var name = track.getString("name") // Artist on song
                            var art_list = track.getJSONArray("artists")
                            var artist = art_list.getJSONObject(0)
                            var art_name = artist.getString("name")
                            var uri = track.getString("uri") // URI for streaming
                            var preview = track.getString("preview_url")     // 30 second spotify preview
                            var album = track.getJSONObject("album") // Album Object
                            var images = album.getJSONArray("images")
                             val image = images[0] as JSONObject
                             val image_url = image.getString("url")
                            Log.i("Track", album.toString())
                            Log.i("Image", images[0].toString())
                            Log.i("Image URL",image_url)

                            if (preview != "null") {
                                // Mapping JSON data to a kotlin object
                                songs.add(Song(name, art_name, uri, preview,image_url))
                            }
                            Log.i("Spotify API", name)
                            Log.i("Spotify API", preview)
                        }
                        Log.i("Spotify API", songs.toString())



                        //connect()
                        handleClient()


                    }
                    generateCard()
                    //playsong()

                }


            }


            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(Build.VERSION_CODES.M)
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

            private fun generateCard() {
                //songs.shuffle()
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

            fun playsong(song : Song) {

                    played_songs.add(song.name)
                    now_playing.setText(song.name + " - " + song.artists)


                    val url = song.preview_url//preview url
                    if (url != "null") {
                        val image = song.album_url


                        GlobalScope.launch {
                            this@Game.runOnUiThread {
                                Log.i("LOADING IMAGE", image)
                                Picasso.get()
                                        .load(image)
                                        .into(album_cover)

                            }


                            song_index ++
                            Log.i("Server loading song...", next.toString())
                            delay(10000)
                            next = true


                        }


                        mediaPlayer = MediaPlayer().apply {
                            setAudioAttributes(
                                    AudioAttributes.Builder()
                                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                            .setUsage(AudioAttributes.USAGE_MEDIA)
                                            .build()
                            )
                            setDataSource(url)
                            prepare() // might take long! (for buffering)
                            //start()

                            // 30 second wait in between songs


                        }



                    }
                }





        /*
        Checking -
        (1)All buttons in a line are clicked
        (2)Each song has already been played
        (3)Whether a line has already been secured
         */

            fun validateLines(v: View) {
                Log.i("FAB", "clicked")
                if (!ONE_LINE) {
                    if ((song1_click && song2_click && song3_click && song4_click) || (song5_click && song6_click && song7_click && song8_click)
                            || (song9_click && song10_click && song11_click && song12_click) || (song13_click && song14_click && song15_click && song16_click)) {

                        if (played_songs.contains(song1.text) && played_songs.contains(song2.text)
                                && played_songs.contains(song3.text) && played_songs.contains(song4.text)) {
                            ONE_LINE = true
                            one_line_status = ""
                            Log.i("FAB", "First line!")


                        }
                        else if(played_songs.contains(song5.text) && played_songs.contains(song6.text)
                                        && played_songs.contains(song7.text) && played_songs.contains(song8.text))
                        {
                            ONE_LINE = true
                            Log.i("FAB", "First line!")
                        }
                        else if(played_songs.contains(song9.text) && played_songs.contains(song10.text)
                                && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                        {
                            ONE_LINE = true
                            Log.i("FAB", "First line!")
                        }
                        else if(played_songs.contains(song13.text) && played_songs.contains(song14.text)
                                && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                        {
                            ONE_LINE = true
                            Log.i("FAB", "First line!")
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
                            victory.setTitle("Winner")
                            victory.setMessage("Congratulations! " + name + "you won!")
                            victory.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",DialogInterface.OnClickListener { dialog,
                                                                                                               which -> lines.dismiss() })
                            victory.show()
                            Log.i("FAB", "End Game!")
                        }


                    }
                }
            }

               fun handleClient()
               {

                   GlobalScope.launch(Dispatchers.IO) {
                       var user_count = 0
                       val users = ArrayList<Socket>()
                       val server = ServerSocket(6000)
                       Log.i("Server","Listening on ${server.localPort}")
                       while(user_count < 1)
                       {
                           val sock = server.accept()
                           users.add(sock)
                           user_count ++

                       }
                       for(i in users)
                       {
                           val thread = thread(start = true) { connect(i) }
                       }
                   }
               }

            fun connect(client : Socket)
            {
                GlobalScope.launch(Dispatchers.IO) {

                    Log.i("Server","Client Connected")
                    val output = PrintWriter(client.getOutputStream(), true)
                    var input = BufferedReader(InputStreamReader(client.inputStream))
                    val j_song = Gson().toJson(songs)
                    Log.i("JSON LIST", j_song.toString())
                    output.println(j_song.toString())

                    val first = Gson().toJson(songs[0])
                    output.println(first.toString())
                    playsong(songs[0])

                    songs.shuffle()
                    while(true)
                    {
                        var status = "Hello Client"
                        if(next)
                        {
                            next = false
                            val n_song = Gson().toJson(songs[song_index])
                            status = "SONG" + n_song.toString()
                            playsong(songs[song_index])
                        }
                        output.println(status)
                        val back = input.readLine()
                        Log.i("Server (from Client)",back)
                    }


                    /*
                    for(i in songs)
                    {
                        val send_song = Gson().toJson(i)
                        output.println(send_song)
                        val status = input.readLine()
                        playsong(i)

                        if(status.startsWith("LINEONE"))
                        {
                            var info = status.split('-')
                            ONE_LINE = true
                            one_line_status = "First Line : " + info[1]
                        }
                        else if(status.startsWith("LINETWO"))
                        {
                            var info = status.split('-')
                            TWO_LINES = true
                            two_line_status = "Second Line : " + info[1]
                        }
                        output.println("NO_CHANGE")
                        Log.i("Server", status)

                    }*/

                }

            }




            fun showpopup(v : View)
            {
                lines.setTitle("Leaderboard")
                lines.setMessage(one_line_status + "\n" + two_line_status + "\n" + full_house_status)
                lines.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",DialogInterface.OnClickListener { dialog,
                                                                                                   which -> lines.dismiss() })
                lines.show()
            }


}