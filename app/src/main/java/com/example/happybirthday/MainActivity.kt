package com.example.happybirthday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContentView(R.layout.activity_main)
        // "2020-07-29T06:00:00.0400"
//        val date1 = LocalDateTime.of(2013,7,29,6,0,0,0)
//        val date2 = LocalDateTime.now()
//        val days = ChronoUnit.DAYS.between(date2, date1)
        val days1 = getResources().getString(R.string.days1)
        val days2 = getResources().getString(R.string.days2)
        tv_days.text = days1 + " " + getDays() + days2
    }
    private fun getDays(): String {
        val date1 = LocalDateTime.now()
        val date2 = LocalDateTime.parse("2020-07-29T06:00:00.0400")
        val result = ChronoUnit.DAYS.between(date1, date2)
        return result.toString()
    }
}