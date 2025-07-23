package ar.com.amanmeena.gallery

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val mediaReader: MediaReader
) : ViewModel() {

    var files by mutableStateOf(listOf<MediaFile>())
        private set

    val imageUris = mutableStateListOf<Uri>()

    fun setImageUris(uris: List<Uri>) {
        imageUris.clear()
        imageUris.addAll(uris)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            files = mediaReader.getAllMediaFiles()
        }
    }
    var favourites = mutableStateListOf<MediaFile>()


    fun isFavourite(uri: Uri): Boolean {
        return favourites.any { it.uri == uri }
    }

    fun toggleFavouriteByUri(uri: Uri) {
        val file = files.find { it.uri == uri }
        if (file != null) {
            if (favourites.contains(file)) {
                favourites.remove(file)
            } else {
                favourites.add(file)
            }
        }
    }

}
