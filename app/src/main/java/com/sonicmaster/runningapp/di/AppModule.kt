package com.sonicmaster.runningapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.sonicmaster.runningapp.db.RunDatabase
import com.sonicmaster.runningapp.utils.Utility.KEY_FIRST_TIME_TOGGLE
import com.sonicmaster.runningapp.utils.Utility.KEY_NAME
import com.sonicmaster.runningapp.utils.Utility.KEY_WEIGHT
import com.sonicmaster.runningapp.utils.Utility.SHARED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRunDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RunDatabase::class.java, "run_db").build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunDatabase) = db.runDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "")

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 0f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}