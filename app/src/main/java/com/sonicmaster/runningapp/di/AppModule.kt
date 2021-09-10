package com.sonicmaster.runningapp.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.sonicmaster.runningapp.db.RunDatabase
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
}