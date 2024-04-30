package uk.ac.tees.mad.d3722574.util

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {

    fun Context.createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            externalCacheDir
        )
        return image
    }

    fun getGeoLocation (context: Context,latLng: LatLng):String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)
        return if(!address.isNullOrEmpty()) {
            address[0].featureName + ", " + address[0].locality + ", " + address[0].adminArea
        } else {
            null
        }
    }
}