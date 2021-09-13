package com.sonicmaster.runningapp.ui.viewmodels

import androidx.lifecycle.*
import com.sonicmaster.runningapp.db.Run
import com.sonicmaster.runningapp.repository.MainRepository
import com.sonicmaster.runningapp.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _runSortedByDate = mainRepository.getAllRunSortedByDate()
    private val _runSortedByDistance = mainRepository.getAllRunSortedByDistance()
    private val _runSortedByCaloriesBurned = mainRepository.getAllRunSortedByCaloriesBurned()
    private val _runSortedByTimeInMillis = mainRepository.getAllRunSortedByTimeInMillis()
    private val _runSortedByAvgSpeed = mainRepository.getAllRunSortedByAvgSpeed()


    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(_runSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(_runSortedByDistance) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(_runSortedByCaloriesBurned) { result ->
            if (sortType == SortType.CALORIES_BURNED) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(_runSortedByTimeInMillis) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(_runSortedByAvgSpeed) { result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let {
                    runs.value = it
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> _runSortedByDate.value?.let { runs.value = it }
        SortType.DISTANCE -> _runSortedByDistance.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> _runSortedByCaloriesBurned.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> _runSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.AVG_SPEED -> _runSortedByAvgSpeed.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

    fun deleteRun(run: Run) = viewModelScope.launch {
        mainRepository.deleteRun(run)
    }
}