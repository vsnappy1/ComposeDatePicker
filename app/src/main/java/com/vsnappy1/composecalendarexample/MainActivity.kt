package com.vsnappy1.composecalendarexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vsnappy1.composecalendar.ui.DatePicker
import com.vsnappy1.composecalendarexample.ui.theme.ComposeCalendarExampleTheme

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
                    Column() {
                        DatePicker(
                            modifier = Modifier
                                .padding(16.dp),
                            onDateSelected = { year, month, day ->
//                                Toast.makeText(
//                                    this@MainActivity,
//                                    "$month/$day/$year",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            },
//                        date = ComposeCalendarDate(2005, 8,16)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCalendarExampleTheme {
        DatePicker()
    }
}