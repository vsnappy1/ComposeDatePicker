package com.vsnappy1.composecalendarexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vsnappy1.composecalendarexample.ui.theme.ComposeCalendarExampleTheme
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.timepicker.TimePicker
import com.vsnappy1.timepicker.enums.MinuteGap

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
                            modifier = Modifier.padding(16.dp),
                            onDateSelected = { year, month, day ->
                                Toast.makeText(this@MainActivity, "$year/$month/$day", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                        var text by remember{ mutableStateOf("") }
                        TimePicker(
                            onTimeSelected = { hour, minute ->
                                text = "$hour : $minute "
                            },
                            minuteGap = MinuteGap.FIVE
                        )
                        Text(text)
                    }
                }
            }
        }
    }
}