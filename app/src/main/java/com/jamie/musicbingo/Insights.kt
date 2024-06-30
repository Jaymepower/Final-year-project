package com.jamie.musicbingo

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.database.*
import com.jamie.musicbingo.adapters.UserAdapter
import com.jamie.musicbingo.data.User


class Insights : AppCompatActivity() {
    lateinit var log: DatabaseReference

    lateinit var user: DatabaseReference

    lateinit var users: ArrayList<User>

    lateinit var adapter: UserAdapter

    var scoreSwitch = false
    var lineSwitch = false

    lateinit var scoreButton: Button
    lateinit var lineButton: Button

    lateinit var loading: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insights_activity)

        log = FirebaseDatabase.getInstance().getReference("Log")

        user = FirebaseDatabase.getInstance().getReference("Users")

        val recycler = findViewById<RecyclerView>(R.id.leaderboard_recycler)

        loading = Dialog(this)
        loading.setContentView(R.layout.loading_dialog)

        loading.show()

        scoreButton = findViewById(R.id.scoreButton)
        lineButton = findViewById(R.id.lineButton)


        val genres = HashMap<String, Int>()
        val subGenres = HashMap<String, Int>()

        users = ArrayList()

        val pie = findViewById<PieChart>(R.id.genre_chart)
        val bar = findViewById<HorizontalBarChart>(R.id.subgenre_chart)


        var pieEntries = ArrayList<PieEntry>()
        var barEntries = ArrayList<BarEntry>()


        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#000045")) // Dark blue
        colors.add(Color.parseColor("#6f2da8")) // purple
        colors.add(Color.parseColor("#329932")) // Dark green
        colors.add(Color.parseColor("#454500")) // gold
        colors.add(Color.parseColor("#ff8c00")) // Orange
        colors.add(Color.parseColor("#ff0073")) // pink
        colors.add(Color.parseColor("#f0dc82")) // Beige
        colors.add(Color.parseColor("#6495ed")) // Light blue
        colors.add(Color.parseColor("#d80000")) // Red
        colors.add(Color.parseColor("#8b4513")) // Brown
        colors.add(Color.parseColor("#177972")) // Tourqouise
        colors.add(Color.parseColor("#FD63F9")) // logo pink
        colors.shuffle()


        var gamesPlayed = 0
        var totalLines = 0
        var totalScore = 0
        var songsPlayed = 0

        log.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    gamesPlayed++
                    val genre = snap.child("genre").value.toString()
                    val sub = snap.child("subgenre").value.toString()
                    totalLines += snap.child("lines").value.toString().toInt()
                    totalScore += snap.child("score").value.toString().toInt()
                    songsPlayed += snap.child("songsPlayed").value.toString().toInt()


                    if (genre.isNotEmpty()) {
                        if (genres.containsKey(genre)) {
                            val x = genres[genre] as Int
                            genres[genre] = x + 1
                        } else {
                            genres[genre] = 1
                        }
                    }


                    if (sub.isNotEmpty()) {
                        if (subGenres.containsKey(sub)) {
                            val x = subGenres[sub] as Int
                            subGenres[sub] = x + 1
                        } else {
                            subGenres[sub] = 1
                        }
                    }


                }

                Log.i("Genres", genres.toString())

                val gpField = findViewById<TextView>(R.id.gamesPlayed_field)
                gpField.text = gamesPlayed.toString()

                val played = findViewById<TextView>(R.id.songs_played_field)
                played.text = songsPlayed.toString()

                val lines = findViewById<TextView>(R.id.total_lines_field)
                lines.text = totalLines.toString()

                val score = findViewById<TextView>(R.id.total_score_field)
                score.text = totalScore.toString()

                val avgSp = findViewById<TextView>(R.id.average_songs_played)
                avgSp.text = (songsPlayed / gamesPlayed).toString()


                for ((k, v) in genres) {
                    pieEntries.add(PieEntry(v.toFloat(), k))
                }

                var index = 0
                var labels = ArrayList<String>()

                for ((k, v) in subGenres) {
                    barEntries.add(BarEntry(index.toFloat(), v.toFloat(), k))
                    labels.add(k)
                    index++
                }


                Log.i("Insight", labels.toString())

                // Bar
                val barSet = BarDataSet(barEntries, "Sub Genre's")
                barSet.colors = colors
                val bData = BarData(barSet)
                bData.setValueTextColor(ContextCompat.getColor(this@Insights, R.color.white))
                bData.setDrawValues(true)
                bar.setDrawBarShadow(false)
                bar.description.isEnabled = false
                bar.xAxis.textColor = ContextCompat.getColor(this@Insights, R.color.white)
                bar.rendererLeftYAxis.paintAxisLabels.color = ContextCompat.getColor(this@Insights, R.color.white)
                bar.axisLeft.textColor = ContextCompat.getColor(this@Insights, R.color.white)
                bar.axisRight.textColor = ContextCompat.getColor(this@Insights, R.color.white)
                bar.axisRight.isEnabled = false
                bar.xAxis.setDrawGridLines(false)
                bar.axisLeft.setDrawGridLines(false)
                bar.xAxis.setDrawAxisLine(false)
                bar.description.isEnabled = false
                bar.xAxis.labelCount = labels.size
                bar.xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
                bar.axisLeft.setDrawAxisLine(false)
                bar.legend.textColor = ContextCompat.getColor(this@Insights, R.color.white)
                bar.legend.textSize = 10.toFloat()
                bar.legend.form = Legend.LegendForm.SQUARE
                bar.data = bData
                bar.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                bar.xAxis.setDrawLabels(true)
                bar.invalidate()


                // Pie
                val dataset = PieDataSet(pieEntries, " Genre's")
                dataset.colors = colors
                dataset.valueTextSize = 15.toFloat()
                dataset.valueTextColor = ContextCompat.getColor(this@Insights, R.color.white)
                pie.legend.textColor = ContextCompat.getColor(this@Insights, R.color.white)
                val data = PieData(dataset)
                data.setDrawValues(true)
                pie.data = data
                pie.setUsePercentValues(true)
                pie.setHoleColor(Color.TRANSPARENT)
                pie.description.text = ""
                pie.invalidate()


                loading.dismiss()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        user.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (snap in snapshot.children) {
                    val name = snap.child("displayName").value.toString()
                    val score = snap.child("score").value.toString().toInt()
                    val line = snap.child("lines").value.toString().toInt()

                    users.add(User("", name, "", line, score, false, false, false, false, false))
                }

                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@Insights.applicationContext)
                recycler.layoutManager = mLayoutManager
                adapter = UserAdapter(users)
                recycler.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }


    fun sortScore(v: View) {
        scoreSwitch = if (!scoreSwitch) {
            users.sortBy { it.score }
            adapter.notifyDataSetChanged()
            scoreButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0)
            lineButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            true
        } else {
            users.sortByDescending { it.score }
            adapter.notifyDataSetChanged()
            scoreButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0)
            lineButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            false
        }

    }


    fun sortLine(v: View) {
        lineSwitch = if (!lineSwitch) {
            users.sortBy { it.lines }
            adapter.notifyDataSetChanged()
            lineButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0)
            scoreButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            true
        } else {
            users.sortByDescending { it.lines }
            adapter.notifyDataSetChanged()
            lineButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0)
            scoreButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            false
        }
    }


}