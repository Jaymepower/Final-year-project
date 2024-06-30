package com.jamie.musicbingo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.jamie.musicbingo.Game
import com.jamie.musicbingo.R

class HostActivity : AppCompatActivity() {

    lateinit var access_token: String
    lateinit var username: String

    lateinit var user_ref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_activity)

        user_ref = FirebaseDatabase.getInstance().getReference("Users")


        val id_pref = getSharedPreferences("uid", Context.MODE_PRIVATE)
        val id = id_pref.getString("uid", null).toString()


        var pop = false
        var rock = false
        var country = false
        var electronic = false
        var rap = false


        user_ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val currentID = snap.child("user_id").value.toString()

                    if (id == currentID) {

                        pop = snap.child("pop").value.toString().toBoolean()
                        rock = snap.child("rock").value.toString().toBoolean()
                        country = snap.child("country").value.toString().toBoolean()
                        electronic = snap.child("electronic").value.toString().toBoolean()
                        rap = snap.child("rap").value.toString().toBoolean()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        access_token = intent.getStringExtra("token").toString()
        username = intent.getStringExtra("name").toString()


        var genre: Spinner = findViewById(R.id.genre_spin)
        ArrayAdapter.createFromResource(
                this,
                R.array.genre_list,
                R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            genre.adapter = adapter
        }


        var subgenre: Spinner = findViewById(R.id.subgenre_spin)
        ArrayAdapter.createFromResource(
                this,
                R.array.empty,
                R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            subgenre.adapter = adapter
        }

        genre.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long,
            ) {
                if (genre.selectedItem.toString() == "Rock") {
                    if (rock) {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.rock_sublist,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    } else {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.rock_sublist_locked,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    }

                } else if (genre.selectedItem.toString() == "Pop") {
                    if (pop) {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.pop_sublist,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    } else {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.pop_sublist_locked,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    }

                } else if (genre.selectedItem.toString() == "Electronic") {
                    if (electronic) {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.elect_sublist,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    } else {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.elect_sublist_locked,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    }


                } else if (genre.selectedItem.toString() == "Rap") {
                    if (rap) {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.rap_sublist,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    } else {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.rap_sublist_locked,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    }
                } else if (genre.selectedItem.toString() == "Country") {
                    if (country) {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.country_sublist,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    } else {
                        ArrayAdapter.createFromResource(
                                this@HostActivity,
                                R.array.country_sublist_locked,
                                R.layout.spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subgenre.adapter = adapter
                        }
                    }
                } else if (genre.selectedItem.toString() == "Festive") {
                    ArrayAdapter.createFromResource(
                            this@HostActivity,
                            R.array.festive_sublist,
                            R.layout.spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        subgenre.adapter = adapter
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


    fun startGame(v: View) {
        val sub: Spinner = findViewById(R.id.subgenre_spin)
        val genre: Spinner = findViewById(R.id.genre_spin)

        if (!sub.selectedItem.toString().contains(String(Character.toChars(0x1F512)))) {
            val i = Intent(this, Game::class.java)
            i.putExtra("genre", genre.selectedItem.toString())
            i.putExtra("subgenre", sub.selectedItem.toString())
            i.putExtra("token", access_token)
            i.putExtra("name", username)
            startActivity(i)
            finish()
        } else {
            Toast.makeText(this, "Playlist Locked", Toast.LENGTH_SHORT).show()
        }
    }

}






