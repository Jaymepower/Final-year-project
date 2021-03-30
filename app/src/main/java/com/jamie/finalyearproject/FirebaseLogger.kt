package com.jamie.finalyearproject

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.*

class FirebaseLogger
{

    lateinit var preferences: SharedPreferences
    lateinit var user_ref : DatabaseReference
    lateinit var log_ref : DatabaseReference


    fun publishDetails(context: Context, pl_lines : Int , pl_score : Int)
    {

        user_ref = FirebaseDatabase.getInstance().getReference("Users")


        preferences = context.getSharedPreferences("users_id", Context.MODE_PRIVATE)
        val id = preferences.getString("user_id","FAILED")

        user_ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children)
                {
                    val found_id : String = snap.child("user_id").value as String
                    if(found_id.equals(id))
                    {
                        val lines  = snap.child("lines").value as Long
                        val score  = snap.child("score").value as Long
                        user_ref.child(snap.key.toString()).child("lines").setValue(lines + pl_lines)
                        user_ref.child(snap.key.toString()).child("score").setValue(score + pl_score)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }


    fun LogGameDetails(log : GameLog)
    {

        log_ref = FirebaseDatabase.getInstance().getReference("Log")

        log_ref.push().setValue(log)


    }






}