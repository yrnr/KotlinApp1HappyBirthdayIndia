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
            //Hide keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            // Use the date-of-birth text entered, to know how far away is the next birthday
            daysToBirthday = getDays(findViewById<EditText>(R.id.editTextView).text.toString())
            // Vanish the editTextView and buttonView
            findViewById<EditText>(R.id.editTextView).visibility = View.GONE
            findViewById<Button>(R.id.buttonView).visibility = View.GONE
            // Show the imageView
            findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
            // Show the display string
            findViewById<TextView>(R.id.textView).text = String.format(
                getResources().getString(R.string.days1), daysToBirthday)
        }
    }

    private fun getDays(editViewInput: String): String {
        // Create LocalDateTime object for the birthday
        val birth = LocalDateTime.parse(editViewInput + "T00:00:00.0400")

        val today = LocalDateTime.now()

        // https://github.com/microsoft/TypeScript/issues/20743
        // Find the year of next-birth-day
        val nextBirthdayYear = if (
            ((today.monthValue == birth.monthValue) && (today.dayOfMonth >= birth.dayOfMonth))
            || (today.monthValue > birth.monthValue)) {
            today.year + 1
        } else {
            today.year
        }
        // Find the month of next-birth-day, and make string Eg., 07 for July
        val birthMonthStr = if (birth.monthValue < 10) {
            "0"+birth.monthValue.toString()
        } else {
            birth.monthValue.toString()
        }
        // Make the day-of-month string for parsing, Eg., "05" for the 5th day of the month. Just "5" causes parsing exception
        val birthDayOfMonthStr = if (birth.dayOfMonth < 10) {
            "0"+birth.dayOfMonth.toString()
        } else {
            birth.dayOfMonth.toString()
        }

        // Just add time string format for the YYYY-MM-DD format and parse it to make a LocalDateTime object
        val nextBirthday = LocalDateTime.parse(
            nextBirthdayYear.toString() + "-"
            + birthMonthStr + "-"
            + birthDayOfMonthStr + "T00:00:00.0400")

        // Find the days till next birthday and return the result string
        val result = ChronoUnit.DAYS.between(today, nextBirthday)
        return result.toString()
    }
}