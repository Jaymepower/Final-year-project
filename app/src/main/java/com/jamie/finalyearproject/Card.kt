package com.jamie.finalyearproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlin.math.roundToInt

class Card(val context: Context, val activity: Activity) {

    var ONE_LINE: Boolean = false
    var TWO_LINES: Boolean = false
    var FULL_HOUSE: Boolean = false

    var mutatedCards = 0
    var players = 1

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

    var song1Lock = false
    var song2Lock = false
    var song3Lock = false
    var song4Lock = false
    var song5Lock = false
    var song6Lock = false
    var song7Lock = false
    var song8Lock = false
    var song9Lock = false
    var song10Lock = false
    var song11Lock = false
    var song12Lock = false
    var song13Lock = false
    var song14Lock = false
    var song15Lock = false
    var song16Lock = false


    var album = activity.findViewById<ImageView>(R.id.album_cover)


    var song1 = activity.findViewById<Button>(R.id.song_1)
    var song2 = activity.findViewById<Button>(R.id.song_2)
    var song3 = activity.findViewById<Button>(R.id.song_3)
    var song4 = activity.findViewById<Button>(R.id.song_4)
    var song5 = activity.findViewById<Button>(R.id.song_5)
    var song6 = activity.findViewById<Button>(R.id.song_6)
    var song7 = activity.findViewById<Button>(R.id.song_7)
    var song8 = activity.findViewById<Button>(R.id.song_8)
    var song9 = activity.findViewById<Button>(R.id.song_9)
    var song10 = activity.findViewById<Button>(R.id.song_10)
    var song11 = activity.findViewById<Button>(R.id.song_11)
    var song12 = activity.findViewById<Button>(R.id.song_12)
    var song13 = activity.findViewById<Button>(R.id.song_13)
    var song14 = activity.findViewById<Button>(R.id.song_14)
    var song15 = activity.findViewById<Button>(R.id.song_15)
    var song16 = activity.findViewById<Button>(R.id.song_16)



    @SuppressLint("UseCompatLoadingForDrawables")
    fun checkBox(v: View) {
        when (v.id) {
            R.id.song_1 ->  if(!song1Lock)
            {
                if (!song1_click) {
                    song1.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song1_click = true
                } else {
                    song1.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song1_click = false
                }
            }


            R.id.song_2 -> if(!song2Lock)
            {
                if (!song2_click) {
                    song2.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song2_click = true
                } else {
                    song2.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song2_click = false
                }
            }

            R.id.song_3 -> if(!song3Lock)
            {
                if (!song3_click) {
                    song3.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song3_click = true
                } else {
                    song3.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song3_click = false
                }
            }

            R.id.song_4 -> if(!song4Lock)
            {
                if (!song4_click) {
                    song4.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song4_click = true
                } else {
                    song4.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song4_click = false
                }
            }

            R.id.song_5 -> if(!song5Lock)
            {
                if (!song5_click) {
                    song5.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song5_click = true
                } else {
                    song5.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song5_click = false
                }
            }

            R.id.song_6 -> if(!song6Lock)
            {
                if (!song6_click) {
                    song6.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song6_click = true
                } else {
                    song6.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song6_click = false
                }
            }

            R.id.song_7 -> if(!song7Lock)
            {
                if (!song7_click) {
                    song7.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song7_click = true
                } else {
                    song7.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song7_click = false
                }
            }


            R.id.song_8 ->if(!song8Lock)
            {
                if (!song8_click) {
                    song8.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song8_click = true
                } else {
                    song8.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song8_click = false
                }
            }

            R.id.song_9 -> if(!song9Lock)
            {
                if (!song9_click) {
                    song9.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song9_click = true
                } else {
                    song9.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song9_click = false
                }
            }

            R.id.song_10 -> if(!song10Lock)
            {
                if (!song10_click) {
                    song10.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song10_click = true
                } else {
                    song10.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song10_click = false
                }
            }

            R.id.song_11 -> if(!song11Lock)
            {
                if (!song11_click) {
                    song11.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song11_click = true
                } else {
                    song11.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song11_click = false
                }
            }

            R.id.song_12 -> if(!song12Lock)
            {
                if (!song12_click) {
                    song12.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song12_click = true
                } else {
                    song12.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song12_click = false
                }
            }


            R.id.song_13 -> if(!song13Lock)
            {
                if (!song13_click) {
                    song13.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song13_click = true
                } else {
                    song13.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song13_click = false
                }
            }

            R.id.song_14 -> if(!song14Lock)
            {
                if (!song14_click) {
                    song14.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song14_click = true
                } else {
                    song14.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song14_click = false
                }
            }


            R.id.song_15 -> if(!song15Lock)
            {
                if (!song15_click) {
                    song15.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                    song15_click = true
                } else {
                    song15.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                    song15_click = false
                }
            }


            R.id.song_16 -> if (!song16_click) {
                song16.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song16_click = true
            } else {
                song16.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song16_click = false
            }

        }
        }



    fun printCard(songs : ArrayList<String>)
    {

        song1.text = songs[0]
        song2.text = songs[1]
        song3.text = songs[2]
        song4.text = songs[3]
        song5.text = songs[4]
        song6.text = songs[5]
        song7.text = songs[6]
        song8.text = songs[7]
        song9.text = songs[8]
        song10.text = songs[9]
        song11.text = songs[10]
        song12.text = songs[11]
        song13.text = songs[12]
        song14.text = songs[13]
        song15.text = songs[14]
        song16.text = songs[15]
    }


    fun generateCard(songs : ArrayList<Song>,sub_genre : String) : ArrayList<String>
    {

        val temp = ArrayList<String>()

        if(mutatedCards <= players-1) {


            when (sub_genre) {
                "Americana" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.americana_wild).toMutableList()))
                "Bluegrass" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.bluegrass_wild).toMutableList()))
                "Blues" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.blues_wild).toMutableList()))
                "Folk" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.folk_wild).toMutableList()))
                "Modern Country" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.m_country_wild).toMutableList()))
                "Deep House" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.deep_wild).toMutableList()))
                "Drum and Bass" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.DnB_wild).toMutableList()))
                "House" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.house_wild).toMutableList()))
                "IDM" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.IDM).toMutableList()))
                "Techno" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.techno_wild).toMutableList()))
                "Christmas" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.christmas_wild).toMutableList()))
                "Halloween" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.halloween_wild).toMutableList()))
                "Charts" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.charts_wild).toMutableList()))
                "Disco" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.disco_wild).toMutableList()))
                "No1's" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.ones_wild).toMutableList()))
                "R&B" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.rnb_wild).toMutableList()))
                "Synth Pop" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.synth_wild).toMutableList()))
                "Jazz Rap" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.j_rap_wild).toMutableList()))
                "Lo-Fi Hip Hop" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.lofi_wild).toMutableList()))
                "Mumble Rap" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.mumble_wild).toMutableList()))
                "Old School Hip Hop" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.old_school_wild).toMutableList()))
                "Alternative" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.alternative_wild).toMutableList()))
                "Classic Rock" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.classic_wild).toMutableList()))
                "Hard Rock" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.hard_wild).toMutableList()))
                "Pop Punk" -> temp.addAll(ArrayList(activity.resources.getStringArray(R.array.pop_p_wild).toMutableList()))
            }
        }
            mutatedCards ++

        var slice = 1.0

        if (songs.size >= 70)
        {
            slice = 0.4
        }
        else if(songs.size >= 60)
        {
            slice = 0.5
        }
        else if (songs.size >= 40)
        {
            slice = 0.60
        }
        else if(songs.size >= 30)
        {
            slice = 0.7
        }

        for(i in 0 until (songs.size.toDouble() * slice).roundToInt())
        {
            Log.i("Adding song",songs[i].name)
            temp.add(songs[i].name)
        }


        temp.shuffle()

        return temp
    }


    /*   Checking -
     (1)All buttons in a line are clicked
     (2)Each song has already been played
     (3)Whether a line has already been secured   */

    @SuppressLint("UseCompatLoadingForDrawables")
    fun validateCard(played_songs : ArrayList<String>) : String
    {
        var response = "None"

        if (!ONE_LINE) {

                if (played_songs.contains(song1.text) && played_songs.contains(song2.text)
                        && played_songs.contains(song3.text) && played_songs.contains(song4.text)
                        && (song1_click && song2_click && song3_click && song4_click) ) {

                    ONE_LINE = true
                    response = "ONE"
                    song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song1Lock = true
                    song2Lock = true
                    song3Lock = true
                    song4Lock = true
                    song1_click = true
                    song2_click = true
                    song3_click = true
                    song4_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

                }
                else if(played_songs.contains(song5.text) && played_songs.contains(song6.text)
                        && played_songs.contains(song7.text) && played_songs.contains(song8.text) && (song5_click && song6_click && song7_click && song8_click))
                {

                    ONE_LINE = true
                    response = "ONE"
                    Log.i("FAB", "First line!")
                    song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song5Lock = true
                    song6Lock = true
                    song7Lock = true
                    song8Lock = true
                    song5_click = true
                    song6_click = true
                    song7_click = true
                    song8_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

                }
                else if(played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text) && (song9_click && song10_click && song11_click && song12_click) )
                {

                    ONE_LINE = true
                    response = "ONE"
                    Log.i("FAB", "First line!")
                    song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song9Lock = true
                    song10Lock = true
                    song11Lock = true
                    song12Lock = true
                    song9_click = true
                    song10_click = true
                    song11_click = true
                    song12_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                }
                else if(played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text) && (song13_click && song14_click && song15_click && song16_click))
                {

                    response = "ONE"
                    song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song13Lock = true
                    song14Lock = true
                    song15Lock = true
                    song16Lock = true
                    song13_click = true
                    song14_click = true
                    song15_click = true
                    song16_click = true
                    ONE_LINE = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

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

                    response = "TWO"
                    TWO_LINES = true
                    song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song1Lock = true
                    song2Lock = true
                    song3Lock = true
                    song4Lock = true
                    song5Lock = true
                    song6Lock = true
                    song7Lock = true
                    song8Lock = true
                    song1_click = true
                    song2_click = true
                    song3_click = true
                    song4_click = true
                    song5_click = true
                    song6_click = true
                    song7_click = true
                    song8_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()


                }
            } // Lines 1 and 3
            else if(song1_click && song2_click && song3_click && song4_click && song9_click && song10_click && song11_click && song12_click)
            {
                if (played_songs.contains(song1.text) && played_songs.contains(song2.text) && played_songs.contains(song3.text)
                        && played_songs.contains(song4.text) && played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {

                    response = "TWO"
                    TWO_LINES = true
                    song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song1Lock = true
                    song2Lock = true
                    song3Lock = true
                    song4Lock = true
                    song9Lock = true
                    song10Lock = true
                    song11Lock = true
                    song12Lock = true
                    song1_click = true
                    song2_click = true
                    song3_click = true
                    song4_click = true
                    song9_click = true
                    song10_click = true
                    song11_click = true
                    song12_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                }
            } // Lines 1 and 4
            else if(song1_click && song2_click && song3_click && song4_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song1.text) && played_songs.contains(song2.text) && played_songs.contains(song3.text)
                        && played_songs.contains(song4.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {

                    TWO_LINES = true
                    response = "TWO"
                    song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song1Lock = true
                    song2Lock = true
                    song3Lock = true
                    song4Lock = true
                    song13Lock = true
                    song14Lock = true
                    song15Lock = true
                    song16Lock = true
                    song1_click = true
                    song2_click = true
                    song3_click = true
                    song4_click = true
                    song13_click = true
                    song14_click = true
                    song3_click = true
                    song4_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

                }
            }// Lines 2 and 3
            else if(song5_click && song6_click && song7_click && song8_click && song9_click && song10_click && song11_click && song12_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {


                    TWO_LINES = true
                    response = "TWO"
                    song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song5Lock = true
                    song6Lock = true
                    song7Lock = true
                    song8Lock = true
                    song9Lock = true
                    song10Lock = true
                    song11Lock = true
                    song12Lock = true
                    song5_click = true
                    song6_click = true
                    song7_click = true
                    song8_click = true
                    song9_click = true
                    song10_click = true
                    song11_click = true
                    song12_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()


                }
            } // Lines 2 and 4
            else if(song5_click && song6_click && song7_click && song8_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {

                    TWO_LINES = true
                    response = "TWO"
                    song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song5Lock = true
                    song6Lock = true
                    song7Lock = true
                    song8Lock = true
                    song13Lock = true
                    song14Lock = true
                    song15Lock = true
                    song16Lock = true
                    song5_click = true
                    song6_click = true
                    song7_click = true
                    song8_click = true
                    song13_click = true
                    song14_click = true
                    song15_click = true
                    song16_click = true
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()


                }
            } // 3 and 4
            else if(song9_click && song10_click && song11_click && song12_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song9.text) && played_songs.contains(song10.text) && played_songs.contains(song11.text)
                        && played_songs.contains(song12.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {

                    response = "TWO"
                    TWO_LINES = true
                    song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song9Lock = true
                    song10Lock = true
                    song11Lock = true
                    song12Lock = true
                    song13Lock = true
                    song14Lock = true
                    song15Lock = true
                    song16Lock = true
                    song9_click = true
                    song10_click = true
                    song11_click = true
                    song12_click = true
                    song13_click = true
                    song14_click = true
                    song15_click = true
                    song16_click = true

                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

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

                    response = "FULL"
                    FULL_HOUSE = true
                    song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
                    song1Lock = true
                    song2Lock = true
                    song3Lock = true
                    song4Lock = true
                    song5Lock = true
                    song6Lock = true
                    song7Lock = true
                    song8Lock = true
                    song9Lock = true
                    song10Lock = true
                    song11Lock = true
                    song12Lock = true
                    song13Lock = true
                    song14Lock = true
                    song15Lock = true
                    song16Lock = true
                    song1_click = true
                    song2_click = true
                    song3_click = true
                    song4_click = true
                    song5_click = true
                    song6_click = true
                    song7_click = true
                    song8_click = true
                    song9_click = true
                    song10_click = true
                    song11_click = true
                    song12_click = true
                    song13_click = true
                    song14_click = true
                    song15_click = true
                    song16_click = true

                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()


                }


            }
        }
        Log.i("CARD",response)
        return response
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun cleanCard()
    {
        song1.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song2.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song3.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song4.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song5.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song6.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song7.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song8.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song9.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song10.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song11.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song12.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song13.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song14.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song15.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song16.background = activity.resources.getDrawable(R.drawable.green_border_button, activity.resources.newTheme())
        song1.text = ""
        song2.text = ""
        song3.text = ""
        song4.text = ""
        song5.text = ""
        song6.text = ""
        song7.text = ""
        song8.text = ""
        song9.text = ""
        song10.text = ""
        song11.text = ""
        song12.text = ""
        song13.text = ""
        song14.text = ""
        song15.text = ""
        song16.text = ""

        album.setImageResource(0)
    }



    }




