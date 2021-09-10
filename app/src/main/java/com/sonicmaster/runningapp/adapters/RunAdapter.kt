package com.sonicmaster.runningapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.sonicmaster.runningapp.R
import com.sonicmaster.runningapp.db.Run
import com.sonicmaster.runningapp.utils.Utility.getFormattedTime
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivRunImage)
        val dateTextView: MaterialTextView = itemView.findViewById(R.id.tvDate)
        val avgSpeedTextView: MaterialTextView = itemView.findViewById(R.id.tvAvgSpeed)
        val distanceInKms: MaterialTextView = itemView.findViewById(R.id.tvDistance)
        val timeTextView: MaterialTextView = itemView.findViewById(R.id.tvTime)
        val caloriesTextView: MaterialTextView = itemView.findViewById(R.id.tvCalories)
    }

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunAdapter.RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RunAdapter.RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timeInMillis
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        holder.apply {
            imageView.apply {
                Glide.with(this).load(run.image).into(this)
            }
            dateTextView.text = dateFormat.format(calendar.time)
            avgSpeedTextView.text = "${run.avgSpeed}km/h"
            distanceInKms.text = "${run.distanceInMeters / 1000f}km"
            timeTextView.text = getFormattedTime(run.timeInMillis)
            caloriesTextView.text = "${run.burnedCalories}kcal"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}