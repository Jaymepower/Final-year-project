package com.jamie.finalyearproject

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.TextView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Size
import kotlin.reflect.KParameter

class WinDialog
{



    @SuppressLint("NewApi")
    fun build(context: Context, name : String, status : String, sp : Int) : Dialog
    {
        val dialog = Dialog(context)

        dialog.setContentView(R.layout.win_dialog)

        val confetti = dialog.findViewById<KonfettiView>(R.id.viewKonfetti)

        confetti.build()
                .addColors(Color.GRAY, Color.WHITE, context.getColor(R.color.logoPink),context.getColor(R.color.purple))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addSizes(Size(6, 10f))
        .setPosition(-50f, DisplayMetrics().widthPixels + 50f, -50f, -50f)
            .streamFor(350, 4000)



        val close = dialog.findViewById<ImageView>(R.id.win_dialog_close)
        close.setOnClickListener {
            dialog.dismiss()

        }


        val namefield = dialog.findViewById<TextView>(R.id.win_name)
        namefield.text = name

        val statusField = dialog.findViewById<TextView>(R.id.win_status)

        val objective = dialog.findViewById<TextView>(R.id.win_objective)


        val spField = dialog.findViewById<TextView>(R.id.win_songs_played)
        spField.text = sp.toString()


        when(status)
        {
            "ONE" ->{
                statusField.setText(R.string.one_line)
                objective.text = "Two Lines"
            }
            "TWO" -> {
                statusField.setText(R.string.two_line)
                objective.text = "Two Lines"
            }
        }



        return dialog
    }


}