package com.jamie.musicbingo.data

data class User(var user_id : String, var displayName :String, var email : String,var lines : Int,var score : Int,
                val pop : Boolean , val rock : Boolean, val country : Boolean, val rap : Boolean ,val electronic : Boolean)


