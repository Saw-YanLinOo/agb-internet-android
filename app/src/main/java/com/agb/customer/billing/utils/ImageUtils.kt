package com.agb.customer.billing.utils

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


class ImageUtils(var mContext: AppCompatActivity?) {

    fun encodeBase64String(bm: Bitmap): String {
        val mByteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, mByteArrayOutputStream)
        val b = mByteArrayOutputStream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getImageFromUri(imageUri: Uri): Bitmap? {
        val inputStream = mContext?.contentResolver?.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)
    }

    fun getImageFromPath(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = mContext!!.contentResolver.query(uri, projection, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex: Int = cursor.getColumnIndex(projection[0])
        val filePath: String = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

}