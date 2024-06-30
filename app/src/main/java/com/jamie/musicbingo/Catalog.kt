package com.jamie.musicbingo

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.jamie.musicbingo.adapters.CatalogAdapter
import com.jamie.musicbingo.data.CatalogSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Catalog : AppCompatActivity() {

    lateinit var genreRef: DatabaseReference
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.catalog_activity)

        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.loading_dialog)
        dialog.show()

        val subGenre = intent.getStringExtra("subgenre")

        val pTitle = findViewById<TextView>(R.id.playlist_title)
        pTitle.text = subGenre

        val songs = ArrayList<CatalogSong>()

        genreRef = FirebaseDatabase.getInstance().getReference("Playlists")

        preferences = getSharedPreferences("bearerToken", Context.MODE_PRIVATE)
        var token = preferences.getString("token", null)

        genreRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (snap in snapshot.children) {

                    for (sub in snap.children) {
                        if (sub.key.toString() == subGenre) {
                            val playlistId = sub.value.toString()

                            GlobalScope.launch(Dispatchers.Default) {
                                // GET user info Spotify endpoint
                                val url = URL("https://api.spotify.com/v1/playlists/$playlistId/")
                                // Opening Https connection to set request
                                val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                                httpsURLConnection.requestMethod = "GET"
                                // Adding the request parameters, in this case its the token needed in order to request user info
                                httpsURLConnection.setRequestProperty("Authorization", "Bearer $token")
                                httpsURLConnection.doInput = true
                                httpsURLConnection.doOutput = false
                                // Response returned from request , we use a bufferedReader to read the return
                                val response = httpsURLConnection.inputStream.bufferedReader()
                                        .use { it.readText() }
                                withContext(Dispatchers.Main) {
                                    val jsonObject = JSONObject(response) // Playlist

                                    val page = jsonObject.getJSONObject("tracks")//Page object
                                    val song = page.getJSONArray("items")//Items array inside of pager object

                                    for (i in 0 until song.length()) {
                                        val item = song.getJSONObject(i)
                                        // Iterating the playlist for each track
                                        val track = item.getJSONObject("track") // name of song
                                        val name = track.getString("name")
                                        val art_list = track.getJSONArray("artists")
                                        val artist = art_list.getJSONObject(0)
                                        val art_name = artist.getString("name")
                                        val preview = track.getString("preview_url")     // 30 second spotify preview
                                        val explicit = track.getBoolean("explicit")

                                        val album = track.getJSONObject("album") // Album Object
                                        val albumName = album.getString("name")
                                        val images = album.getJSONArray("images")
                                        val image = images[0] as JSONObject
                                        val image_url = image.getString("url")

                                        if (preview != "null") {
                                            // Mapping JSON data to a kotlin object
                                            songs.add(CatalogSong(name, art_name, albumName, explicit, image_url))
                                        }
                                    }


                                    val recycler = findViewById<RecyclerView>(R.id.songs_recycler)
                                    val adapter = CatalogAdapter(songs)

                                    val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@Catalog)
                                    recycler.layoutManager = mLayoutManager

                                    recycler.adapter = adapter

                                    dialog.dismiss()
                                }


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
}