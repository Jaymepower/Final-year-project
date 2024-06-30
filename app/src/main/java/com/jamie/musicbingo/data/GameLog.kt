package com.jamie.musicbingo.data

data class GameLog(val user_id : String, val genre : String, val subgenre : String, val songsPlayed : Int, val date :String,
val lines : Int, val score : Int,val reactions : Int,val win : Boolean)
