package com.app.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class FirstActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpage)
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FirstActivity> {
        override fun createFromParcel(parcel: Parcel): FirstActivity {
            return FirstActivity(parcel)
        }

        override fun newArray(size: Int): Array<FirstActivity?> {
            return arrayOfNulls(size)
        }
    }
    fun gotologin (view: View) {
        intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }
}
