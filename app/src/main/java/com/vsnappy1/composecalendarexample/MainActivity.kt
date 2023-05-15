package com.vsnappy1.composecalendarexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vsnappy1.composecalendarexample.ui.theme.ComposeCalendarExampleTheme
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.timepicker.TimePicker

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalendarExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        DatePicker(
                            modifier = Modifier.padding(16.dp).background(Color.Cyan, RoundedCornerShape(8.dp)),
                            onDateSelected = { year, month, day ->
                                Log.d(TAG, "Date: $month/$day/$year")
                            },
                        )

                        TimePicker(modifier = Modifier.padding(16.dp),
                            onTimeSelected = { hour, minute, timeOfDay ->
                                Log.d(TAG, "Time: $hour : $minute $timeOfDay")
                            }
                        )
                    }
                }
            }
        }
    }
}