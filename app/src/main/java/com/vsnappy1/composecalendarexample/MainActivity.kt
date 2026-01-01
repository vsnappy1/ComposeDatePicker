package com.vsnappy1.composecalendarexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import com.vsnappy1.datepicker.data.model.DefaultDate
import com.vsnappy1.timepicker.TimePicker

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
                        var timeString by remember { mutableStateOf("") }
                        var dateString by remember {
                            DefaultDate.defaultDate.let {
                                mutableStateOf("${it.year}/${it.month + 1}/${it.day}")
                            }
                        }

                        DatePicker(
                            modifier = Modifier.padding(16.dp),
                            onDateSelected = { year, month, day ->
                                dateString = "$year/${month + 1}/$day"
                            }
                        )
                        TimePicker(
                            modifier = Modifier.padding(16.dp),
                            onTimeSelected = { hour, minute ->
                                timeString = "$hour : $minute "
                            }
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(timeString)
                                Text(dateString)
                            }
                        }
                    }
                }
            }
        }
    }
}