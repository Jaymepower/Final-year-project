package com.example.finalyearproject

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.media.MediaPlayer
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
import com.example.finalyearproject.chatAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class Game : AppCompatActivity() {
            // playlist structure : "https://api.spotify.com/v1/playlists/15QCkUofmu6Gga9saOhENp/"
            val REDIRECT_URI: String = "com.example.finalyearproject://MainActivity/"
            val CLIENT_ID: String = "6573a6cbc21f424fad81067b6ce53fd0"
            var song_index = 0
            var user_count = 0
            var score = 0
            var noLines = 0
            var next : Boolean = true
            var start : Boolean = false
            var ONE_LINE: Boolean = false
            var TWO_LINES: Boolean = false
            var FULL_HOUSE: Boolean = false
            var one_line_status : String = ""
            var two_line_status : String = ""
            var full_house_status : String = ""
            var react = ""
            lateinit var genre: String
            lateinit var sub_genre: String

            private lateinit var played_songs: ArrayList<String>
            private lateinit var songs: ArrayList<Song>
            private lateinit var reactions : HashMap<String,String>

            lateinit var mediaPlayer: MediaPlayer
            lateinit var genre_ref : DatabaseReference
            lateinit var user_ref : DatabaseReference
            lateinit var preferences: SharedPreferences

            lateinit var session : AlertDialog
            lateinit var lines : AlertDialog
            lateinit var played : AlertDialog.Builder
            lateinit var victory : LovelyStandardDialog

            lateinit var chatDialog: ChatDialog

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
            lateinit var username : String
            lateinit var react_button : CounterFab
            lateinit var messageButton : CounterFab
            lateinit var card : Card

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.game_activity)
                now_playing = findViewById(R.id.now_playing)
                album_cover = findViewById(R.id.album_cover)
                react_button = findViewById(R.id.reaction_button)
                react_button.count = 0
                username = intent.getStringExtra("name").toString()
                played_songs = ArrayList()
                songs = ArrayList()
                reactions = HashMap()
                messageButton = findViewById(R.id.chat_button)

                chatDialog = ChatDialog()
                lines = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()
                session = AlertDialog.Builder(this,R.style.AlertDialogStyle).create()
                session.setTitle("Session")
                session.setIcon(R.drawable.p_wifi)
                session.setMessage("Devices connected \n               $user_count")
                session.setButton(AlertDialog.BUTTON_NEUTRAL,"Start",DialogInterface.OnClickListener { dialog, which -> })
                session.show()

                card = Card(this,this@Game)


                session.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {

                    if(user_count == 0 )
                    {
                        Toast.makeText(this@Game,"No devices connected yet",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        start = true
                        session.dismiss()
                    }

                }

                victory = LovelyStandardDialog(this,R.style.TintTheme)
                        .setTopColor(getColor(R.color.purple))
                        .setIcon(R.drawable.ic_trophy)
                        .setTitle("Winner")
                        .setTitleGravity(Gravity.CENTER)
                        .setMessageGravity(Gravity.CENTER)
                        .configureMessageView { message -> message.setTextColor(resources.getColor(R.color.black)) }
                        .setNeutralButton("OK", View.OnClickListener { victory.dismiss() })
                        .setCancelable(true)

                played = AlertDialog.Builder(this,R.style.AlertDialogStyle)
                played.setTitle("Played Songs")

                album_cover.setOnClickListener {

                   var sns = Array<String>(played_songs.size){ "it = $it" } ; var i = 0
                    for (x in played_songs)
                    {
                        sns.set(i,x)
                        i ++ 
                    }
                    played.setItems(sns,DialogInterface.OnClickListener { dialog, which ->  })
                    val d = played.create()
                    d.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",DialogInterface.OnClickListener { dialog,
                                                                                                       which -> d.dismiss() })
                    d.show()
                }
                song1 = findViewById(R.id.song_1);song2 = findViewById(R.id.song_2);song3 = findViewById(R.id.song_3);song4 = findViewById(R.id.song_4); song5 = findViewById(R.id.song_5); song6 = findViewById(R.id.song_6)
                song7 = findViewById(R.id.song_7); song8 = findViewById(R.id.song_8);song9 = findViewById(R.id.song_9);song10 = findViewById(R.id.song_10);song11 = findViewById(R.id.song_11);song12 = findViewById(R.id.song_12)
                song13 = findViewById(R.id.song_13);song14 = findViewById(R.id.song_14);song15 = findViewById(R.id.song_15);song16 = findViewById(R.id.song_16)

                genre = intent.getStringExtra("genre").toString()
                sub_genre = intent.getStringExtra("subgenre").toString()
                val token: String? = intent.getStringExtra("token")

               genre_ref = FirebaseDatabase.getInstance().getReference("Playlists")
               user_ref = FirebaseDatabase.getInstance().getReference("Users")

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
                        val name = jsonObject.getString("name")// Name of playlist
                        val page = jsonObject.getJSONObject("tracks")//Pager object
                        val song = page.getJSONArray("items")//Items array inside of pager object
                        Log.i("Spotify API", name.toString())
                        Log.i("Spotify API", page.toString())
                        Log.i("Spotify API", song.toString())
                        for (i in 0 until song.length()) {
                            val item = song.getJSONObject(i)
                            // Iterating the playlist for each track
                            val track = item.getJSONObject("track") // name of song
                            val name = track.getString("name") // Artist on song
                            val art_list = track.getJSONArray("artists")
                            val artist = art_list.getJSONObject(0)
                            val art_name = artist.getString("name")
                            val uri = track.getString("uri") // URI for streaming
                            val preview = track.getString("preview_url")     // 30 second spotify preview
                            val album = track.getJSONObject("album") // Album Object
                            val images = album.getJSONArray("images")
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
                        
                        handleClient()
                    }

                    var temp = ArrayList<String>()
                    for(i in songs)
                    {
                      temp.add(i.name)
                    }

                    card.printCard(card.generateCard(songs,sub_genre))

                }


            }

            fun boxclicked(v: View) {
                card.checkBox(v)
            }


            /*
            Originally i was going to use the Spotify Remote player to stream music to the app , there were two problems with this
            (1) The player would stream songs from the very beginning (0:00) meaning any song that had a long or incoherent intro would effect
            the overall speed and presentation of the game
            (2) The spotify player would appear in the users notification dropdown meaning they could essentially control and or break the game

            Therefore i decided to extract MP3 urls from spotify track objects and stream them using the MediaPlayer object, this file is streamed
            interally through the app,neither the host or client can control it and it plays for a very convenient 30 seconds which is exactly
            how the game would be played in real life, spotify still handles the storage and streaming but it is much better suited the nature of
            the application
             */

            fun playsong(song : Song) {

                    played_songs.add(song.name)
                    now_playing.text = "${song.name} - ${song.artists}"
                    react = ""
                    runOnUiThread { react_button.count = 0  }
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

                            reactions.clear()

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
                                Log.i("Server loading song...", next.toString())
                                delay(5000)
                                song_index ++
                                next = true
                                // 30 second wait in between songs
                            }
                        }
                    }
                }


            fun validateLines(v: View) {

                when(card.validateCard(played_songs))
                {
                    "ONE" -> {
                        if(!ONE_LINE)
                        {
                            Log.i("Testing","works")
                            victory.show()
                            ONE_LINE = true
                        }
                    }
                    "TWO" -> {
                            if(!TWO_LINES)
                            {
                                Log.i("Testing","works")
                                victory.show()
                                TWO_LINES = true
                            }
                            }
                    "FULL" -> {
                        if(!FULL_HOUSE)
                        {
                            Log.i("Testing","works")
                            victory.show()
                            FULL_HOUSE = true
                        }
                    }

                }


            }

                fun handleClient()
               {
                   GlobalScope.launch(Dispatchers.IO) {

                       val users = ArrayList<Socket>()
                       val server = ServerSocket(6000)
                       Log.i("Server","Listening on ${server.localPort}")
                       while(true)
                       {
                           if(user_count != 3)
                           {
                               val sock = server.accept()
                               thread(start = true) { connect(sock) }
                               users.add(sock)
                               user_count ++
                               runOnUiThread {  session.setMessage("Devices connected\n              $user_count") }

                           }


                       }

                   }
               }

    //ZfvoIX1UwvHAwmBa
    // redir add tcp:5000:6000
            fun connect(client : Socket)
            {
                GlobalScope.launch(Dispatchers.IO) {

                    try {
                        Log.i("Server","Client Connected")
                        val output = PrintWriter(client.getOutputStream(), true)
                        var input = BufferedReader(InputStreamReader(client.inputStream))
                        val jSong = Gson().toJson(songs)
                        Log.i("JSON LIST", "this is the json $jSong")
                        output.println(jSong.toString()) // 1

                        var status = "STATUS_NEUTRAL"
                        var change = "NO_CHANGE"
                        //songs.shuffle()
                        while(true)
                        {
                            /* Manages the timing of the game, when the player is finished next(Global) will become true
                             which will indicate that the next song is to be played, the playSong method essentially
                             acts as a timer thread
                             */
                            if(FULL_HOUSE)
                            {
                                status = "CLOSE"
                            }
                            if(next and start)
                            {
                                next = false // Resets the next boolean so another song will be played
                                val n_song = Gson().toJson(songs[song_index])
                                status = "SONG" + n_song.toString()
                                if(song_index < songs.size)
                                {
                                    playsong(songs[song_index])

                                }
                            }

                            //Loop to send client status updates, if a song is to be played it will be sent otherwise nothing
                            output.println(status) // 2
                            status = "STATUS_NEUTRAL" // Resets the status change

                            // If the server notices a line has been achieved it will inform each client
                            if(ONE_LINE)
                            {
                                change = "ONE"+one_line_status
                            }

                            if (TWO_LINES)
                            {
                                change = "TWO"+two_line_status
                            }

                            if(FULL_HOUSE)
                            {
                                val star = String(Character.toChars(0x2B50))
                                change = "FULL"+full_house_status
                                victory.setTitle("Winner")
                                victory.setMessage("Congratulations! $username you Won! \n\t\t\t$star +500 $star")
                                victory.setNeutralButton("End",View.OnClickListener { victory.dismiss()
                                })
                                victory.show()


                            }
                            output.println(change) // 3

                            //Status update from client to let server know if they have claimed any lines
                            // A client will only send its update once, server tracks lines
                            val back = input.readLine() // 4
                            if(back != null)
                            {
                                Log.i("Server (from Client)",back)
                                if(back.startsWith("ONE"))
                                {
                                    val c_name = back.substring(3,back.length)
                                    Log.i("Server (Name recieved)",c_name)
                                    ONE_LINE = true
                                    one_line_status = c_name

                                }
                                else if(back.startsWith("TWO"))
                                {
                                    val c_name = back.substring(3,back.length)
                                    Log.i("Server (Name recieved)",c_name)
                                    TWO_LINES = true
                                    two_line_status = c_name

                                }
                                else if(back.startsWith("FULL"))
                                {
                                    val c_name = back.substring(3,back.length)
                                    Log.i("Server (Name recieved)",c_name)
                                    FULL_HOUSE = true
                                    full_house_status = c_name
                                }

                            }

                            output.println(react)
                            val c_react = input.readLine()
                            if(!c_react.isNullOrEmpty())
                            {
                                val s_react = c_react.split("+")
                                when(s_react[1])
                                {
                                    "HAPPY" -> {
                                        if(!reactions[s_react[0]].equals("Loves"))
                                        {
                                            runOnUiThread {react_button.increase()  }
                                        }
                                        reactions[s_react[0]] = "Loves"
                                    }
                                    "BORED" -> {
                                        if(!reactions[s_react[0]].equals("Hates"))
                                        {
                                            runOnUiThread {react_button.increase()  }
                                        }
                                        reactions[s_react[0]] = "Hates"
                                    }
                                }
                            }


                            var last_message = ""
                            if(chatDialog.myLog.isNotEmpty())
                            {
                                last_message = Gson().toJson(chatDialog.myLog[chatDialog.myLog.size-1])
                            }
                            output.println(last_message)

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


                            if(song_index == songs.size + 1)
                            {
                                break
                            }
                        }

                        try {
                            client.close()
                            input.close()
                            output.close()
                            Log.i("Server Socket", "CLOSED")
                        }catch (e : IOException)
                        {
                            Log.i("Server Socket", "Did not close successfully")
                        }






                    }catch (e : SocketException)
                    {
                        runOnUiThread { Toast.makeText(this@Game,"Connection Lost",Toast.LENGTH_SHORT).show() }
                    }

                    preferences = getSharedPreferences("users_id", Context.MODE_PRIVATE)
                    val id = preferences.getString("user_id","FAILED")

                    user_ref.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(snap in snapshot.children)
                            {
                                val found_id : String = snap.child("user_id").value as String
                                Log.i("ENDGAME FIREBASE",found_id)
                                if(found_id.equals(id))
                                {
                                    val lines  = snap.child("lines").value as Long
                                    val score  = snap.child("score").value as Long
                                    user_ref.child(snap.key.toString()).child("lines").setValue(lines + 2)
                                    user_ref.child(snap.key.toString()).child("score").setValue(score + 25)
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }


    fun reaction(v : View)
    {
        runOnUiThread {react_button.count = 0  }
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.reaction_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val text = dialog.findViewById<TextView>(R.id.reaction_list)
        val song_title = dialog.findViewById<TextView>(R.id.song_title)
        song_title.text = songs[song_index].name
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
        text.setText(temp)
        val happy = dialog.findViewById<FloatingActionButton>(R.id.happy_button)
        happy.setOnClickListener {
            reactions.put(username,"Loves")
            react = "$username+HAPPY"
            dialog.dismiss()
        }
        val bored = dialog.findViewById<FloatingActionButton>(R.id.bored_button)
        bored.setOnClickListener {
            reactions.put(username,"Hates")
            react = "$username+BORED"
            dialog.dismiss()
        dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        if (start)
        {
            Toast.makeText(this@Game , "Leaving the session will stop the game!",Toast.LENGTH_LONG).show()
        }
        else{
            super.onBackPressed()
        }
    }

    fun showPopup(v : View)
            {
                lines.setTitle("Leaderboard")
                lines.setMessage("First Line : $one_line_status\nTwo Lines : $two_line_status\nFull House : $full_house_status")
                lines.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",DialogInterface.OnClickListener { dialog,
                                                                                                   which -> lines.dismiss() })
                lines.show()
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