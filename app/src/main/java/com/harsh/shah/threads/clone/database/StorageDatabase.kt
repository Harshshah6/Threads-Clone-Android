package com.harsh.shah.threads.clone.database

import android.content.Context
import android.util.Log
import com.harsh.shah.threads.clone.Constants
import io.appwrite.Client
import io.appwrite.models.InputFile
import io.appwrite.models.UploadProgress
import io.appwrite.services.Storage

object StorageDatabase {
    private const val TAG = "StorageDatabase"
    private lateinit var client : Client
    private lateinit var storage : Storage

    fun create(context: Context) {
        client = Client(context)
            // Replace with your own endpoint and project ID
            .setEndpoint("https://demo.appwrite.io/v1")
            .setProject("65fad68fc6a18820e902")

        storage = Storage(client)
    }

    fun uploadFile(file: InputFile) {
        suspend {
            val uploadedFile = storage.createFile(
                bucketId = Constants.APPWRITE_STORAGE_BUCKET_ID,
                fileId = "",
                file = file,
                onProgress = { uploadProgress: UploadProgress ->
                    Log.i(TAG, "uploadFile: " + uploadProgress.progress)
                }
            )
        }
    }

    fun downloadFile(fileId: String) {
        suspend {
            val file = storage.getFileDownload(
                bucketId = Constants.APPWRITE_STORAGE_BUCKET_ID,
                fileId = fileId
            )
            Log.i(TAG, "downloadFile: $file")
        }
    }
}