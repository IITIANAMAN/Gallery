package ar.com.amanmeena.gallery

import android.net.Uri




data class MediaFile(
    val uri: Uri,
    val name: String,
    val type: MediaType,
    val path: String? = null
)
