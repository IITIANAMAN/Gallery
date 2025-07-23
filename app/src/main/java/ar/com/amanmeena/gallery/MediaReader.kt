package ar.com.amanmeena.gallery

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore

class MediaReader(
    private val context: Context
) {
    fun getAllMediaFiles(): List<MediaFile> {
        val mediaFiles = mutableListOf<MediaFile>()
        val queryUri = if(Build.VERSION.SDK_INT>=29)
        {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        }else
        {
            MediaStore.Files.getContentUri("external")
        }

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DATA
        )
        context.contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            null
        )?.use {cursor->
            val idColumns = cursor.getColumnIndexOrThrow(
                MediaStore.Files.FileColumns._ID
            )
            val nameColumn = cursor.getColumnIndexOrThrow(
                MediaStore.Files.FileColumns.DISPLAY_NAME
            )
            val typeColumn = cursor.getColumnIndexOrThrow(
                MediaStore.Files.FileColumns.MEDIA_TYPE
            )
            val dataColumn = cursor.getColumnIndexOrThrow(
                MediaStore.Files.FileColumns.DATA
            )
            while(cursor.moveToNext())
            {
                val id = cursor.getLong(idColumns)
                val name = cursor.getString(nameColumn)
                val type = cursor.getInt(typeColumn)
                val path = cursor.getString(dataColumn)
                if(name != null && type != null)
                {
                    val contentUri = ContentUris.withAppendedId(
                        queryUri,
                        id
                    )
                    val mediaType = when (type) {
                        MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO -> MediaType.VIDEO
                        MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE -> MediaType.IMAGE
                        else -> null
                    }

                    if (mediaType != null) {
                        mediaFiles.add(
                            MediaFile(
                                uri = contentUri,
                                name = name,
                                type = mediaType,
                                path = path
                            )
                        )
                    }



                }
            }
        }
        return mediaFiles.toList()

    }
}