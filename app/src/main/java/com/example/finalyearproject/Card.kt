package com.example.finalyearproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Button

class Card(val context: Context, val activity: Activity) {

    var ONE_LINE: Boolean = false
    var TWO_LINES: Boolean = false
    var FULL_HOUSE: Boolean = false
    var one_line_status : String = ""
    var two_line_status : String = ""
    var full_house_status : String = ""

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
            R.id.song_1 -> if (!song1_click) {
                song1.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song1_click = true
            } else {
                song1.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song1_click = false
            }

            R.id.song_2 -> if (!song2_click) {
                song2.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song2_click = true
            } else {
                song2.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song2_click = false
            }

            R.id.song_3 -> if (!song3_click) {
                song3.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song3_click = true
            } else {
                song3.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song3_click = false
            }

            R.id.song_4 -> if (!song4_click) {
                song4.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song4_click = true
            } else {
                song4.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song4_click = false
            }

            R.id.song_5 -> if (!song5_click) {
                song5.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song5_click = true
            } else {
                song5.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song5_click = false
            }

            R.id.song_6 -> if (!song6_click) {
                song6.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song6_click = true
            } else {
                song6.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song6_click = false
            }

            R.id.song_7 -> if (!song7_click) {
                song7.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song7_click = true
            } else {
                song7.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song7_click = false
            }
            R.id.song_8 -> if (!song8_click) {
                song8.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song8_click = true
            } else {
                song8.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song8_click = false
            }

            R.id.song_9 -> if (!song9_click) {
                song9.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song9_click = true
            } else {
                song9.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song9_click = false
            }

            R.id.song_10 -> if (!song10_click) {
                song10.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song10_click = true
            } else {
                song10.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song10_click = false
            }

            R.id.song_11 -> if (!song11_click) {
                song11.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song11_click = true
            } else {
                song11.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song11_click = false
            }

            R.id.song_12 -> if (!song12_click) {
                song12.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song12_click = true
            } else {
                song12.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song12_click = false
            }
            R.id.song_13 -> if (!song13_click) {
                song13.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song13_click = true
            } else {
                song13.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song13_click = false
            }
            R.id.song_14 -> if (!song14_click) {
                song14.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song14_click = true
            } else {
                song14.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song14_click = false
            }
            R.id.song_15 -> if (!song15_click) {
                song15.background = activity.resources.getDrawable(R.drawable.red_border_but, activity.resources.newTheme())
                song15_click = true
            } else {
                song15.background = activity.resources.getDrawable(R.drawable.border_button, activity.resources.newTheme())
                song15_click = false
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
        val temp = songs

        song1.text = temp[0]
        song2.text = temp[1]
        song3.text = temp[2]
        song4.text = temp[3]
        song5.text = temp[4]
        song6.text = temp[5]
        song7.text = temp[6]
        song8.text = temp[7]
        song9.text = temp[8]
        song10.text = temp[9]
        song11.text = temp[10]
        song12.text = temp[11]
        song13.text = temp[12]
        song14.text = temp[13]
        song15.text = temp[14]
        song16.text = temp[15]
    }

    fun printCardbySong(playlist : ArrayList<Song>)
    {
        val songs = playlist
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


    fun generateCard(songs : ArrayList<Song>,sub_genre : String) : ArrayList<String>
    {
        val temp = ArrayList<String>()

        for (i in songs) { temp.add(i.name) }

        when(sub_genre)
        {
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
            "Christmas" ->temp.addAll(ArrayList(activity.resources.getStringArray(R.array.christmas_wild).toMutableList()))
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

        //return ArrayList(temp.shuffled())
        return temp
    }


    /*   Checking -
     (1)All buttons in a line are clicked
     (2)Each song has already been played
     (3)Whether a line has already been secured   */

    fun validateCard(played_songs : ArrayList<String>) : String
    {
        var response = "None"
        Log.i("FAB", "clicked")
        Log.i("FAB", " one line is ${ONE_LINE.toString()}")
        Log.i("FAB", " two line is ${TWO_LINES.toString()}")
        Log.i("FAB", " full house is ${FULL_HOUSE.toString()}")
        val star = String(Character.toChars(0x2B50))
        if (!ONE_LINE) {
            if ((song1_click && song2_click && song3_click && song4_click) || (song5_click && song6_click && song7_click && song8_click)
                    || (song9_click && song10_click && song11_click && song12_click) || (song13_click && song14_click && song15_click && song16_click)) {

                if (played_songs.contains(song1.text) && played_songs.contains(song2.text)
                        && played_songs.contains(song3.text) && played_songs.contains(song4.text)) {
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    ONE_LINE = true
                    Log.i("FAB", "First line!")
                    response = "ONE"


                }
                else if(played_songs.contains(song5.text) && played_songs.contains(song6.text)
                        && played_songs.contains(song7.text) && played_songs.contains(song8.text))
                {
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    ONE_LINE = true
                    response = "ONE"
                    Log.i("FAB", "First line!")

                }
                else if(played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    ONE_LINE = true
                    response = "ONE"
                    Log.i("FAB", "First line!")
                }
                else if(played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    response = "ONE"

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
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    response = "TWO"
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
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    response = "TWO"

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
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    TWO_LINES = true
                    response = "TWO"

                    Log.i("FAB", "Second line!")
                }
            }// Lines 2 and 3
            else if(song5_click && song6_click && song7_click && song8_click && song9_click && song10_click && song11_click && song12_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song9.text) && played_songs.contains(song10.text)
                        && played_songs.contains(song11.text) && played_songs.contains(song12.text))
                {

                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    TWO_LINES = true
                    response = "TWO"
                    Log.i("FAB", "Second line!")

                }
            } // Lines 2 and 4
            else if(song5_click && song6_click && song7_click && song8_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song5.text) && played_songs.contains(song6.text) && played_songs.contains(song7.text)
                        && played_songs.contains(song8.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {

                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    TWO_LINES = true
                    response = "TWO"
                    Log.i("FAB", "Second line!")
                }
            } // 3 and 4
            else if(song9_click && song10_click && song11_click && song12_click && song13_click && song14_click && song15_click && song16_click)
            {
                if (played_songs.contains(song9.text) && played_songs.contains(song10.text) && played_songs.contains(song11.text)
                        && played_songs.contains(song12.text) && played_songs.contains(song13.text) && played_songs.contains(song14.text)
                        && played_songs.contains(song15.text) && played_songs.contains(song16.text))
                {
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()
                    response = "TWO"

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
                    val mediaPlayer = MediaPlayer.create(
                            context,
                            R.raw.success_sound)
                    mediaPlayer.start()

                    response = "FULL"
                    FULL_HOUSE = true
                    Log.i("FAB", "End Game!")

                }


            }
        }
        return response
    }






    }




