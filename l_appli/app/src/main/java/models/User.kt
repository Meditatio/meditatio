package models

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
class User(val uid: String?, val username: String?, val profileImageUrl: String?, var interest:String?, var coach:String?, var score:String?, var step: String?, var verre:String?, var sport:String?, var eat:String?, var fresh:String?, var meditation:String?): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        ) {
    }

    constructor(): this("","","","","false", "0","0", "0","false", "false", "false", "false")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(username)
        parcel.writeString(profileImageUrl)
        parcel.writeString(interest)
        parcel.writeString(coach)
        parcel.writeString(score)
        parcel.writeString(step)
        parcel.writeString(verre)
        parcel.writeString(sport)
        parcel.writeString(eat)
        parcel.writeString(fresh)
        parcel.writeString(meditation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}