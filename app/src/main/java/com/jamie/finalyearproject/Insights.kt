package com.jamie.finalyearproject

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class Insights : AppCompatActivity()
{




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insights_activity)

        val chart = findViewById<PieChart>(R.id.genre_chart)

        var entries = ArrayList<PieEntry>()
        var colors = ArrayList<Int>()

        colors.add(Color.parseColor("#304567"))
















    }
}