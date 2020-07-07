package com.example.happybirthday

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContentView(R.layout.activity_main)
        var daysToBirthday = ""
        findViewById<Button>(R.id.buttonView).setOnClickListener {
            daysToBirthday = getDays(findViewById<EditText>(R.id.editTextView).text.toString())
            findViewById<EditText>(R.id.editTextView).visibility = View.GONE
            findViewById<Button>(R.id.buttonView).visibility = View.GONE
            findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
            //Hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            // Make the display string
            val days1 = getResources().getString(R.string.days1)
            val days2 = getResources().getString(R.string.days2)
            findViewById<TextView>(R.id.textView).text = days1 + " " + daysToBirthday + days2
        }
    }

    fun getDays(string: String): String {
        val today = LocalDateTime.now()
        val birth = LocalDateTime.parse(string + "T00:00:00.0400")
        var nextBirthdayYear = 0
        var nextBirthday = today
        if (
            ((today.monthValue == birth.monthValue) && (today.dayOfMonth >= birth.dayOfMonth))
            || (today.monthValue > birth.monthValue)) { nextBirthdayYear = today.year + 1 }
        else { nextBirthdayYear = today.year }
        var birthMonthStr = ""
        var birthDayOfMonthStr = ""
        if (birth.monthValue < 10) { birthMonthStr = "0"+birth.monthValue.toString()}
        else {birthMonthStr = birth.monthValue.toString()}
        if (birth.dayOfMonth < 10) { birthDayOfMonthStr = "0"+birth.dayOfMonth.toString()}
        else {birthDayOfMonthStr = birth.dayOfMonth.toString()}

        nextBirthday = LocalDateTime.parse(
            nextBirthdayYear.toString() + "-"
            + birthMonthStr + "-"
            + birthDayOfMonthStr + "T00:00:00.0400")
        val result = ChronoUnit.DAYS.between(today, nextBirthday)
        return result.toString()
    }
}