package com.agh.pandemic

import android.os.Parcel
import android.os.Parcelable


class ThreatCart(internal val cityName:String, val infection :Boolean) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
        parcel.writeByte(if (infection) 1 else 0)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<ThreatCart> = object : Parcelable.Creator<ThreatCart> {
            override fun createFromParcel(source: Parcel): ThreatCart{
                return ThreatCart(source)
            }

            override fun newArray(size: Int): Array<ThreatCart?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return (other is ThreatCart && cityName == other.cityName && infection == other.infection)
    }

    override fun toString(): String {
        var result = cityName
        if (infection) {
            result = "Infekcja " + result
        }
        return result
    }
}