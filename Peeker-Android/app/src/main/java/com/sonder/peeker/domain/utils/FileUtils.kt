package com.sonder.peeker.domain.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import java.io.*

class FileUtils {
    companion object {
        fun fileFromContentUri(context: Context, contentUri: Uri): File {
            // Preparing Temp file name
            val fileExtension = getFileExtension(context, contentUri)
            val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

            // Creating Temp file
            val tempFile = File(context.cacheDir, fileName)
            tempFile.createNewFile()

            try {
                val oStream = FileOutputStream(tempFile)
                val inputStream = context.contentResolver.openInputStream(contentUri)

                inputStream?.let {
                    copy(inputStream, oStream)
                }

                oStream.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return tempFile
        }

        private fun getFileExtension(context: Context, uri: Uri): String? {
            val fileType: String? = context.contentResolver.getType(uri)
            return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
        }

        @Throws(IOException::class)
        private fun copy(source: InputStream, target: OutputStream) {
            val buf = ByteArray(8192)
            var length: Int
            while (source.read(buf).also { length = it } > 0) {
                target.write(buf, 0, length)
            }
        }
    }
}