package com.jamie.musicbingo.data

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.jamie.musicbingo.R
import com.jamie.musicbingo.activities.OptionsActivity

class LeaderboardDialog {

    @SuppressLint("SetTextI18n")
    fun build(context: Context, first: String, two: String, winner: String, score: Int, lines: Int): Dialog {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.leaderboard_dialog)


        val first_field = dialog.findViewById<TextView>(R.id.first_line_field)
        val second_field = dialog.findViewById<TextView>(R.id.two_line_field)
        val win_field = dialog.findViewById<TextView>(R.id.winner_field)

        val line_field = dialog.findViewById<TextView>(R.id.leader_line)
        line_field.text = lines.toString()

        val score_field = dialog.findViewById<TextView>(R.id.leader_score)
        score_field.text = score.toString()

        val status = dialog.findViewById<TextView>(R.id.game_status)

        val close = dialog.findViewById<ImageView>(R.id.win_dialog_close)
        close.setOnClickListener {
            dialog.dismiss()
        }

        if (first.isEmpty()) {
            first_field.text = "-:-"
            status.text = "The first line is still to play for"
        } else {
            first_field.text = first
        }

        if (two.isEmpty()) {
            second_field.text = "-:-"
            status.text = "Two lines have not been claimed yet, keep going!"
        } else {
            second_field.text = two
        }

        if (winner.isEmpty()) {
            win_field.text = "-:-"
            status.text = "Go for the win, the full house is up for grabs"

        } else {
            win_field.text = winner
        }



        return dialog
    }


    fun endGame(context: Context, first: String, two: String, winner: String, lines: Int, score: Int, reactions: Int, sp: Int): Dialog {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.endgame_dialog)


        val oneLine = dialog.findViewById<TextView>(R.id.end_game_oneline)
        val twoLine = dialog.findViewById<TextView>(R.id.end_game_twoline)
        val win = dialog.findViewById<TextView>(R.id.end_game_winner)
        val line_field = dialog.findViewById<TextView>(R.id.endgame_lines_field)
        val score_field = dialog.findViewById<TextView>(R.id.endgame_score_field)
        val reacts = dialog.findViewById<TextView>(R.id.endgame_reacts_field)
        val playedSongs = dialog.findViewById<TextView>(R.id.endgame_sp_field)

        val button = dialog.findViewById<Button>(R.id.endgame_button)

        button.setOnClickListener {
            dialog.dismiss()
            val i = Intent(context, OptionsActivity::class.java)
            val activity = context as Activity
            context.startActivity(i)
            activity.finish()

        }

        oneLine.text = first
        twoLine.text = two
        win.text = winner
        line_field.text = lines.toString()
        score_field.text = score.toString()
        reacts.text = reactions.toString()
        playedSongs.text = sp.toString()


        return dialog
    }


}