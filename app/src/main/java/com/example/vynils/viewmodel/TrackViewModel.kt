package com.example.vynils.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynils.model.Track
import com.example.vynils.repository.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackViewModel(application: Application) : AndroidViewModel(application) {
    private val tracksRepository = TrackRepository(application)
    private val _track = MutableLiveData<Track>()

    val track: LiveData<Track>
        get() = _track

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun setTrack(track: Track) {
        _track.value = track
    }

    fun associateTrack(albumId: Int) {
        try {
            viewModelScope.launch (Dispatchers.Default) {
                _track.value?.let {
                    tracksRepository.associateTrack(it, albumId)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e:Exception) {
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}