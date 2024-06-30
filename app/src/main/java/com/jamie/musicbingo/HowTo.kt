package com.jamie.musicbingo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HowTo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.howto_activity)

        val host_dec = findViewById<TextView>(R.id.host_desc)
        host_dec.setText(R.string.host_desc)
        val join_desc = findViewById<TextView>(R.id.join_desc)
        join_desc.setText(R.string.join_desc)
        val game_desc = findViewById<TextView>(R.id.game_desc)
        game_desc.setText(R.string.game_desc)
        val winning_desc = findViewById<TextView>(R.id.winning_desc)
        winning_desc.setText(R.string.winning_desc)
        val false_desc = findViewById<TextView>(R.id.false_claim_desc)
        false_desc.setText(R.string.false_claim)
        val react_desc = findViewById<TextView>(R.id.react_desc)
        react_desc.setText(R.string.react_desc)
        val leader_desc = findViewById<TextView>(R.id.leaderboard_desc)
        leader_desc.setText(R.string.leaderboard_desc)
        val songsPlayed_desc = findViewById<TextView>(R.id.songs_played_desc)
        songsPlayed_desc.setText(R.string.songs_played_desc)
        val chat_desc = findViewById<TextView>(R.id.chat_desc)
        chat_desc.setText(R.string.chat_desc)
        val endgame_desc = findViewById<TextView>(R.id.endgame_desc)
        endgame_desc.setText(R.string.endgame)
    }

}