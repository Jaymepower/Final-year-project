package com.jamie.musicbingo

import android.content.Context
import android.content.SharedPreferences
import com.github.javafaker.Faker
import com.google.firebase.database.*
import com.jamie.musicbingo.data.GameLog
import com.jamie.musicbingo.data.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random

class FirebaseLogger {

    lateinit var preferences: SharedPreferences
    lateinit var userRef: DatabaseReference
    lateinit var logRef: DatabaseReference

    lateinit var list: Array<String>
    fun publishDetails(context: Context, pl_lines: Int, pl_score: Int) {
        userRef = FirebaseDatabase.getInstance().getReference("Users")
        preferences = context.getSharedPreferences("users_id", Context.MODE_PRIVATE)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val id: String = snap.child("user_id").value as String
                    if (id == id) {
                        val lines = snap.child("lines").value as Long
                        val score = snap.child("score").value as Long
                        userRef.child(snap.key.toString()).child("lines").setValue(lines + pl_lines)
                        userRef.child(snap.key.toString()).child("score").setValue(score + pl_score)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }


    fun LogGameDetails(log: GameLog) {
        FirebaseDatabase.getInstance().getReference("Log").push().setValue(log)
    }


    fun logMockDetails(context: Context) {

        thread(start = true) {
            while (true) {
                val scores = arrayListOf(25, 50, 100)
                val score = scores[Random.nextInt(scores.size)]

                val lines = arrayListOf(1, 2, 4)
                val line = lines[Random.nextInt(lines.size)]

                var id = ""

                for (i in 0 until 10)
                    id += Random.nextInt(10).toString()


                val gList = context.resources.getStringArray(R.array.genre_list)

                val genre = gList[Random.nextInt(gList.size)]

                when (genre) {
                    "Pop" -> list = context.resources.getStringArray(R.array.pop_sublist)
                    "Rock" -> list = context.resources.getStringArray(R.array.rock_sublist)
                    "Country" -> list = context.resources.getStringArray(R.array.country_sublist)
                    "Rap" -> list = context.resources.getStringArray(R.array.rap_sublist)
                    "Electronic" -> list = context.resources.getStringArray(R.array.elect_sublist)
                    "Festive" -> list = context.resources.getStringArray(R.array.festive_sublist)
                }

                var subgenre: String = list[Random.nextInt(list.size)]


                val sPlayed = Random.nextInt(16, 48)

                val date = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))

                val reactions = Random.nextInt(10, 100)

                val win = Random.nextBoolean()

                logRef = FirebaseDatabase.getInstance().getReference("Log")

                logRef.push().setValue(GameLog(id, genre, subgenre, sPlayed, date, line, score, reactions, win))
            }

        }


    }


    fun logMockUser() {


        thread(start = true) {
            while (true) {

                val score = Random.nextInt(0, 10000)

                val lines = Random.nextInt(0, 1000)

                var id = ""

                for (i in 0 until 10)
                    id += Random.nextInt(10).toString()


                val name = Faker.instance().name().firstName() + " " + Faker.instance().name().lastName()

                val email = name.replace(" ", "") + "@mail.com"


                val user = User(id, name, email.toLowerCase(), lines, score, false, false, false, false, false)


                userRef = FirebaseDatabase.getInstance().getReference("Users")

                userRef.push().setValue(user)


            }
        }


    }


}