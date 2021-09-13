package com.sonicmaster.runningapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.db.Run
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    private val runs: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {

    private val tvDate: TextView = findViewById(R.id.tvDate)
    private val tvDuration: TextView = findViewById(R.id.tvDuration)
    private val tvAvgSpeed: TextView = findViewById(R.id.tvAvgSpeed)
    private val tvDistance: TextView = findViewById(R.id.tvDistance)
    private val tvCaloriesBurned: TextView = findViewById(R.id.tvCaloriesBurned)

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        tvDate.text = dateFormat.format(run.timestamp)
        tvDuration.text = Utility.getFormattedTime(run.timeInMillis)
        tvAvgSpeed.text = "${run.avgSpeed}km/h"
        tvDistance.text = "${run.distanceInMeters / 1000f}km"
        tvCaloriesBurned.text = "${run.burnedCalories}kcal"


    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }
}