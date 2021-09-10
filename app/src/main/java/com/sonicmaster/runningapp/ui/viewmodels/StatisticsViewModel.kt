package com.sonicmaster.runningapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.sonicmaster.runningapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel
@Inject
constructor(mainRepository: MainRepository) : ViewModel() {

}