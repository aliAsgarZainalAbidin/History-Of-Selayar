package com.example.historyofselayar.data.db

import androidx.room.TypeConverter
import com.example.historyofselayar.data.model.Attach
import com.example.historyofselayar.data.model.Wisata
import com.google.gson.Gson
import com.google.gson.JsonParser

class HistoryConverter {
    @TypeConverter
    fun wisataToJson(value : Wisata?) : String = Gson().toJson(value)

    @TypeConverter
    fun jsonToWisata(value : String) : Wisata = Gson().fromJson(value, Wisata::class.java)

    @TypeConverter
    fun attachToJson(value : Attach?) : String = Gson().toJson(value)

    @TypeConverter
    fun jsonToAttach(value : String) : Attach = Gson().fromJson(value, Attach::class.java)

    @TypeConverter
    fun listAttachToJSon(value : List<Attach>) : String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListAttach(value : String) : ArrayList<Attach>? {
        val list = arrayListOf<Attach>()
        if (!value.equals("null")){
            val array = JsonParser.parseString(value).asJsonArray
            array.forEach { jsonElement ->
                val jsonObject = jsonElement.asJsonObject
                val name = jsonObject.get("attach_name").toString()
                val id = jsonObject.get("id").toString().toInt()
                val wisata_id = jsonObject.get("wisata_id").toString().toInt()
                val attachment = Attach(null,name,id,wisata_id)
                list.add(attachment)
            }
        }
        return list
    }
}