package com.example.vynils.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vynils.model.Album
import com.example.vynils.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    private val _album = MutableLiveData<Album>()


    val albums: LiveData<List<Album>>
        get() = _albums

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun setAlbum(album: Album){
        _album.value = album
    }

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataCreateFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default) {
                _album.value?.let {
                    albumsRepository.refreshAlbumCreate(it)
                }
            }
        } catch (e:Exception) {
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }


    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.Default){
                    var data = albumsRepository.refreshAlbumList()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception) {
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun refreshDataDetailAlbumFromNetWork(albumId: Int){
        try{
            viewModelScope.launch (Dispatchers.Default) {
                withContext(Dispatchers.Default){
                    var data = albumsRepository.refreshAlbumDetail(albumId)
                    _album.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception) {
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }
}