package com.sonicmaster.runningapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.databinding.FragmentStatisticsBinding
import com.sonicmaster.runningapp.ui.viewmodels.StatisticsViewModel
import com.sonicmaster.runningapp.utils.CustomMarkerView
import com.sonicmaster.runningapp.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels()

    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupBarChart()
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, {
            it?.let {
                val totalRunTime = Utility.getFormattedTime(it)
                binding.tvTotalTime.text = totalRunTime
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner, {
            it?.let {
                val kms = it / 1000f
                val totalDistance = (round(kms * 10f) / 10f).toString()
                binding.tvTotalDistance.text = totalDistance
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, {
            it?.let {
                val avgSpeed = (round(it * 10f) / 10f).toString()
                binding.tvAverageSpeed.text = avgSpeed
            }
        })

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, {
            it?.let {
                val totalCalories = "${it}kcal"
                binding.tvTotalCalories.text = totalCalories
            }
        })

        viewModel.runSortedByDate.observe(viewLifecycleOwner, {
            it?.let {
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeed) }
                val barDataSet = BarDataSet(allAvgSpeed, "Avg Speed Over Time").apply {
                    valueTextColor = Color.BLACK
                    color = Color.GRAY
                }
                binding.apply {
                    barChart.data = BarData(barDataSet)
                    barChart.marker =
                        CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                    barChart.invalidate()
                }
            }
        })
    }


    private fun setupBarChart() {
        binding.apply {
            barChart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawLabels(false)
                axisLineColor = Color.GREEN
                textColor = Color.BLACK
                setDrawGridLines(false)
            }
            barChart.axisLeft.apply {
                axisLineColor = Color.GREEN
                textColor = Color.BLACK
                setDrawGridLines(false)
            }
            barChart.axisRight.apply {
                axisLineColor = Color.GREEN
                textColor = Color.BLACK
                setDrawGridLines(false)
            }
            barChart.apply {
                description.text = "Avg Speed Over Time"
                legend.isEnabled = false
            }
        }
    }
}