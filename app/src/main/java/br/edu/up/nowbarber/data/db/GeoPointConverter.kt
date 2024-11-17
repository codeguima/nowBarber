package br.edu.up.nowbarber.data.db

import androidx.room.TypeConverter
import com.google.firebase.firestore.GeoPoint
import com.google.gson.Gson

class GeoPointConverter {

    @TypeConverter
    fun fromGeoPoint(geoPoint: GeoPoint?): String? {
        return geoPoint?.let {
            // Convertendo o GeoPoint para JSON (ou pode ser separando por vírgula)
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun toGeoPoint(geoPointString: String?): GeoPoint? {
        return geoPointString?.let {
            // Convertendo a String de volta para um GeoPoint
            Gson().fromJson(it, GeoPoint::class.java)
        }
    }
}
