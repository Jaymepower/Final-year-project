package com.jamie.finalyearproject

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
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
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class Game : AppCompatActivity() {

            val REDIRECT_URI: String = "com.example.finalyearproject://MainActivity/"
            val CLIENT_ID: String = "6573a6cbc21f424fad81067b6ce53fd0"
            var song_index = 0
            var user_count = 0

            lateinit var username : String
            lateinit var uid : String
            var score = 0
            var noLines = 0
            var next : Boolean = true
            var start : Boolean = false
            var ONE_LINE: Boolean = false
            var TWO_LINES: Boolean = false
            var FULL_HOUSE: Boolean = false

            var bingoLock = false
            var lockCounter = 30


            var one_line_status : String = ""
            var two_line_status : String = ""
            var full_house_status : String = ""
            var react = ""
            var reactCount = 0
            lateinit var genre: String
            lateinit var sub_genre: String

            private lateinit var played_songs: ArrayList<String>
            private lateinit var songs: ArrayList<Song>
            private lateinit var reactions : HashMap<String, String>
            private lateinit var users : ArrayList<Socket>

            lateinit var mediaPlayer: MediaPlayer
            lateinit var genre_ref : DatabaseReference
            lateinit var firebaseLog : FirebaseLogger

            lateinit var server : ServerSocket

            lateinit var session : AlertDialog
            lateinit var lines : AlertDialog
            lateinit var played : AlertDialog.Builder


            lateinit var chatDialog: ChatDialog
            lateinit var chatBox : Dialog

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


                chatDialog = ChatDialog()
                chatBox = chatDialog.Build(this@Game)

                username = intent.getStringExtra("name").toString()



                var preferences = getSharedPreferences("username", Context.MODE_PRIVATE)
                username = preferences.getString("username", null).toString()

                preferences = getSharedPreferences("uid", Context.MODE_PRIVATE)
                uid = preferences.getString("uid", null).toString()

                mediaPlayer = MediaPlayer()


                played_songs = ArrayList()
                songs = ArrayList()
                reactions = HashMap()
                messageButton = findViewById(R.id.chat_button)



                lines = AlertDialog.Builder(this, R.style.AlertDialogStyle).create()
                session = AlertDialog.Builder(this, R.style.AlertDialogStyle).create()
                session.setTitle("Session")
                session.setIcon(R.drawable.p_wifi)
                session.setMessage("Devices connected \n               $user_count")
                session.setButton(AlertDialog.BUTTON_NEUTRAL, "Start", DialogInterface.OnClickListener { dialog, which -> })
                session.show()

                card = Card(this@Game, this@Game)

                session.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
                    if(user_count == 0 )
                    {
                        Toast.makeText(this@Game, "No devices connected yet", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        start = true
                        session.dismiss()
                    }
                }





                played = AlertDialog.Builder(this, R.style.AlertDialogStyle)
                played.setTitle("Played Songs")


                album_cover.setOnClickListener {
                    val dialog = PlayedSongsDialog().build(this, songs, played_songs.size)
                    dialog.show()
                }

                song1 = findViewById(R.id.song_1);song2 = findViewById(R.id.song_2);song3 = findViewById(R.id.song_3);song4 = findViewById(R.id.song_4); song5 = findViewById(R.id.song_5); song6 = findViewById(R.id.song_6)
                song7 = findViewById(R.id.song_7); song8 = findViewById(R.id.song_8);song9 = findViewById(R.id.song_9);song10 = findViewById(R.id.song_10);song11 = findViewById(R.id.song_11);song12 = findViewById(R.id.song_12)
                song13 = findViewById(R.id.song_13);song14 = findViewById(R.id.song_14);song15 = findViewById(R.id.song_15);song16 = findViewById(R.id.song_16)

                genre = intent.getStringExtra("genre").toString()
                sub_genre = intent.getStringExtra("subgenre").toString()
                val token: String? = intent.getStringExtra("token")

               genre_ref = FirebaseDatabase.getInstance().getReference("Playlists")


                genre_ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (snap in snapshot.children) {
                            val name = snap.key.toString()

                            if (name == genre) {
                                for (sub in snap.children) {
                                    val sub_g = sub.key.toString()


                                    if (sub_g.equals(sub_genre)) {

                                        getPlaylist(token.toString(), sub.value.toString())
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }



            fun getPlaylist(token: String, playlist_id: String)
            {
                GlobalScope.launch(Dispatchers.Default) {
                    val url = URL("https://api.spotify.com/v1/playlists/" + playlist_id + "/")
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


                            if (preview != "null") {
                                // Mapping JSON data to a kotlin object
                                songs.add(Song(name, art_name, uri, preview, image_url))
                            }

                        }

                        songs.shuffle()
                        card.printCard(card.generateCard(songs, sub_genre))
                        handleClient()
                    }






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

            @SuppressLint("SetTextI18n")
            private fun playsong(song: Song) {

                    played_songs.add(song.name)
                    now_playing.text = "${song.name} - ${song.artists}"
                    react = ""
                    runOnUiThread { react_button.count = 0  }
                    val image = song.album_url

                        GlobalScope.launch {
                            this@Game.runOnUiThread {
                                Picasso.get()
                                       .load(image)
                                        .into(album_cover) }

                            reactions.clear()


                                delay(10000)
                                song_index ++
                                next = true

                            }


                mediaPlayer.reset()

                mediaPlayer.apply {
                    setAudioAttributes(
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build())
                    setDataSource(song.preview_url)
                    prepare() // might take long(for buffering)
                    start() }


                }


            fun validateLines(v: View) {

                val winDialog = WinDialog()

                if(!bingoLock)
                {
                    when(card.validateCard(played_songs))
                    {
                        "ONE" -> {
                            if (!ONE_LINE) {
                                runOnUiThread { winDialog.build(this@Game,username,"ONE",played_songs.size).show() }
                                ONE_LINE = true
                                one_line_status = username
                                noLines += 1
                                score += 25
                            }
                        }
                        "TWO" -> {
                            if (!TWO_LINES) {
                                winDialog.build(this,username,"TWO",played_songs.size).show()
                                TWO_LINES = true
                                two_line_status = username
                                when(noLines)
                                {
                                    0 -> noLines = 2
                                    1 -> noLines = 1
                                }
                                score += 50
                            }
                        }
                        "FULL" -> {
                            if (!FULL_HOUSE) {

                                FULL_HOUSE = true
                                start = false
                                full_house_status = username
                                noLines += 4
                                score += 100
                                card.cleanCard()
                                firebaseLog = FirebaseLogger()
                                firebaseLog.publishDetails(this@Game, noLines, score)
                                firebaseLog.LogGameDetails(GameLog(uid, genre, sub_genre, played_songs.size,SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())), noLines, score, reactCount,true))
                                LeaderboardDialog().endGame(this@Game, one_line_status, two_line_status, full_house_status, noLines, score, reactCount, played_songs.size).show()
                            }
                        }

                        "None" -> {
                            lockCounter = 30
                            val dialog = Dialog(this)
                            dialog.setContentView(R.layout.false_dialog)
                            dialog.findViewById<TextView>(R.id.fail_name).text = username
                            dialog.findViewById<Button>(R.id.fail_button).setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.show()
                            bingoLock = true
                            thread {

                                val mediaPlayer = MediaPlayer.create(
                                        this,
                                        R.raw.vinyscratch)
                                mediaPlayer.setVolume(9.toFloat(),9.toFloat())
                                mediaPlayer.start()


                                while(lockCounter != 0)
                                {
                                    lockCounter --
                                    if (lockCounter == 1)
                                        bingoLock = false

                                    Thread.sleep(1000)
                                }


                            }


                        }
                    }
                }
                else
                {
                    runOnUiThread { Toast.makeText(this@Game,"LOCKED $lockCounter seconds remaining",Toast.LENGTH_SHORT).show() }
                }


            }


                private fun handleClient() {
                   GlobalScope.launch(Dispatchers.IO) {
                       users = ArrayList()
                       server = ServerSocket(6000)
                       while(true)
                       {
                           if(user_count != 3)
                           {
                               try{
                                   val sock = server.accept()
                                   thread(start = true) { connect(sock) }
                                   users.add(sock)
                                   user_count ++
                                   card.players = user_count
                                   runOnUiThread {  session.setMessage("Devices connected\n                $user_count") }
                               }catch (e : SocketException){}
                           }
                       }
                   }
               }

    // ZfvoIX1UwvHAwmBa
    // redir add tcp:5000:6000
    private fun connect(client: Socket)
            {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val output = PrintWriter(client.getOutputStream(), true)
                        val input = BufferedReader(InputStreamReader(client.inputStream))
                        val jSong = Gson().toJson(card.generateCard(songs,sub_genre))

                        output.println("$genre-$sub_genre")
                        output.println(jSong.toString())

                        var status = "STATUS_NEUTRAL"
                        var change = "NO_CHANGE"

                        while(true)
                        {
                            if(next and start)
                            {
                                next = false // Resets the next boolean so another song will be played
                                if(song_index < songs.size)
                                {
                                    val n_song = Gson().toJson(songs[song_index])
                                    status = "SONG$n_song"
                                    playsong(songs[song_index])
                                }
                            }

                            //Loop to send client status updates, if a song is to be played it will be sent otherwise nothing
                            output.println(status) // 2
                            status = "STATUS_NEUTRAL" // Resets the status change

                            // If the server notices a line has been achieved it will inform each client
                            if(ONE_LINE)
                            {
                                change = "ONE$one_line_status"
                            }

                            if (TWO_LINES)
                            {
                                change = "TWO$two_line_status"
                            }

                            if(FULL_HOUSE)
                            {
                                start = false
                                change = "FULL$full_house_status"

                            }
                            output.println(change) // 3

                            //Status update from client to let server know if they have claimed any lines
                            // A client will only send its update once, server tracks lines
                            val back = input.readLine() // 4
                            if(back != null)
                            {
                                if(!ONE_LINE && back.startsWith("ONE"))
                                {
                                    val cName = back.substring(3, back.length)
                                    ONE_LINE = true
                                    card.ONE_LINE = true
                                    one_line_status = cName
                                    runOnUiThread { Toast.makeText(this@Game,"$cName has achieved the first line",Toast.LENGTH_SHORT).show()}

                                }
                                else if(!TWO_LINES && back.startsWith("TWO"))
                                {
                                    val cName = back.substring(3, back.length)
                                    TWO_LINES = true
                                    card.TWO_LINES = true
                                    two_line_status = cName
                                    runOnUiThread { Toast.makeText(this@Game,"$cName has achieved the two lines",Toast.LENGTH_SHORT).show()}

                                }
                                else if(!FULL_HOUSE && back.startsWith("FULL"))
                                {
                                    runOnUiThread { card.cleanCard() }
                                    val cName = back.substring(4, back.length)
                                    FULL_HOUSE = true
                                    card.FULL_HOUSE = true
                                    full_house_status = cName
                                    firebaseLog = FirebaseLogger()
                                    try{
                                        input.close()
                                        output.close()
                                    }catch(e : IOException)
                                    { }

                                    firebaseLog.publishDetails(this@Game, noLines, score)
                                    runOnUiThread {
                                        Runnable{LeaderboardDialog().endGame(this@Game, one_line_status, two_line_status, full_house_status, noLines, score, reactCount, played_songs.size).show() }.run()  }
                                }

                            }



                            output.println(react)
                            val c_react = input.readLine()

                            if(!c_react.isNullOrEmpty())
                            {
                                val s_react = c_react.split("+")

                                if(!reactions.containsKey(s_react[0]))
                                    reactCount ++


                                when(s_react[1])
                                {
                                    "HAPPY" -> {
                                        if (!reactions[s_react[0]].equals("Loves")) {
                                            runOnUiThread { react_button.increase() }
                                        }
                                        reactions[s_react[0]] = "Loves"
                                    }
                                    "BORED" -> {
                                        if (!reactions[s_react[0]].equals("Hates")) {
                                            runOnUiThread { react_button.increase() }
                                        }
                                        reactions[s_react[0]] = "Hates"
                                    }
                                }
                            }


                            var lastMessage = ""
                            if(chatDialog.myLog.isNotEmpty())
                            {
                                lastMessage = Gson().toJson(chatDialog.myLog[chatDialog.myLog.size - 1])
                            }
                            output.println(lastMessage)

                            val messagesString = input.readLine()
                            if(messagesString != "")
                            {
                                val message = Gson().fromJson(messagesString, Message::class.java)
                                if (!chatDialog.messages.contains(message))
                                {
                                    if(!chatBox.isShowing)
                                    {
                                        runOnUiThread {  messageButton.increase() }
                                    }
                                    runOnUiThread {
                                        chatDialog.notifyChange()
                                    }
                                    chatDialog.messages.add(message)
                                }
                            }
                        }


                    } catch (e: IOException)
                    {
                        try {
                            for(i in users)
                                i.close()
                        }catch (e : IOException){}
                    }

                }
            }


    fun reaction(v: View)
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
        for((x, y) in reactions)
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
                score += 5
            }

            reactions.put(username, "Loves")
            react = "$username+HAPPY"
            dialog.dismiss()
        }
        val bored = dialog.findViewById<FloatingActionButton>(R.id.bored_button)
        bored.setOnClickListener {

            if(reactions.get(username) == null)
            {
                reactCount ++
                score += 5
            }

            reactions.put(username, "Hates")
            react = "$username+BORED"
            dialog.dismiss()
        dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        if (start)
        {
            Toast.makeText(this@Game, "Leaving the session will stop the game!", Toast.LENGTH_SHORT).show()
        }
        else{
            super.onBackPressed()
        }
    }

    fun showPopup(v: View)
    {
     LeaderboardDialog().build(this, one_line_status, two_line_status, full_house_status,score,noLines).show()

    }


    fun chatBox(v: View)
    {
        runOnUiThread { messageButton.count = 0 }
        chatDialog.sendButton.setOnClickListener {
            if(chatDialog.text.text.isNotEmpty())
            {
                chatDialog.messages.add(Message(username, chatDialog.text.text.toString(), System.currentTimeMillis()))
                chatDialog.myLog.add(Message(username, chatDialog.text.text.toString(), System.currentTimeMillis()))
                chatDialog.text.setText("")
                chatDialog.notifyChange()
            }
            else
            {
                chatDialog.text.error = "Message Cannot be empty"
            }
        }
        chatBox.show()

    }

    override fun onDestroy() {
        try {

            server.close()
            for(i in users)
            {
                i.getOutputStream().close()
                i.getInputStream().close()
                i.close()
            }
        }catch (e : IOException){}
        super.onDestroy()

    }


    override fun onStop() {
        try {
            server.close()
            for(i in users)
            {
                i.getOutputStream().close()
                i.getInputStream().close()
                i.close()
            }
        }catch (e : IOException){}

        finish()
        super.onStop()
    }



}