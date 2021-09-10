package com.sonicmaster.runningapp.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Run(
    var image: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeed: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var burnedCalories: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}