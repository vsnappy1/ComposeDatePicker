# ComposeDatePicker
An Android Jetpack Compose library that provides a Composable Date Picker / Time Picker functionality.

[![](https://jitpack.io/v/vsnappy1/ComposeDatePicker.svg)](https://jitpack.io/#vsnappy1/ComposeDatePicker)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

## Setup
__Step 1.__ Add the JitPack repository to your build file (Add it in your root *__build.gradle__* at the end of repositories)
```kotlin
allprojects {
    repositories {
        ..
        maven { url 'https://jitpack.io' }
    }
}
```
__Note*__ In newer projects you can add it in *__settings.gradle__*

__Step 2.__ Add the dependency
```kotlin
dependencies {
    ..
    implementation 'com.github.vsnappy1:ComposeDatePicker:2.2.0'
}
```

## Usage
Adding a date picker or time picker is incredibly easy, requiring just two lines of code.

```kotlin
DatePicker(onDateSelected = { year, month, day ->
    
})
```
![DatePicker Gif](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/b56c58e4-ce7a-4c62-bb06-7e2e96a59b33)
![Screenshot_datepicker_1 (1)](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/740ec79f-d7d5-407b-9010-beab4774169e)



```kotlin
TimePicker(onTimeSelected = { hour, minute ->

})
```
![TimePicker Gif](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/15f21b3f-93ac-4f79-8174-46ed128cb7ff)
![Screenshot_timepicker](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/de0373a3-f7f6-42bd-9f4f-0a221ca68d98)


## Customization
The date and time picker offer extensive customization options, allowing users to modify the 
TextStyle, Color, Size, Shape, and other elements to align with their preferred theme.

### Date Picker
___
#### Set Custom Date
```kotlin
DatePicker(
    onDateSelected = { year, month, day ->
    },
    date = DatePickerDate(year = 2023, month = 0, day = 5)
)
```
Please note that the **_year_** should be within a range of <current_year> ± 100 (inclusive). Additionally, for the **_month_**, 
please keep in mind that 0 represents January, while 11 corresponds to December.

#### Set Selection Limit
```kotlin
DatePicker(
    onDateSelected = { year, month, day ->
    },
    selectionLimiter = SelectionLimiter(
        fromDate = DatePickerDate(year = 2023, month = 4, day = 7),
        toDate = DatePickerDate(year = 2023, month = 4, day = 21)
    )
)
```

#### Customize the Appearance
```kotlin
DatePicker(
    modifier = Modifier.padding(16.dp),
    onDateSelected = { year, month, day ->
    },
    configuration = DatePickerConfiguration.Builder()
        .height(height = 300.dp)
        .dateTextStyle(DefaultDatePickerConfig.dateTextStyle.copy(color = Color(0xFF333333)))
        .selectedDateTextStyle(textStyle = TextStyle(Color(0xFFFFFFFF)))
        .selectedDateBackgroundColor(color = Color(0xFF64DD17))
        .build()
)
```
In addition to **_dateTextStyle_**, **_selectedDateTextStyle_**, and **_selectedDateBackgroundColor_**, there are a total of 
20 attributes available for users to customize the appearance of the date picker.

<br>

### Time Picker
___
#### Set Custom Time
```kotlin
TimePicker(
    onTimeSelected = { hour, minute ->
    },
    time = TimePickerTime(
        hour = 12,
        minute = 45
    )
)
```

#### Set Is24Hour & MinuteGap
```kotlin
TimePicker(
    onTimeSelected = { hour, minute ->
    },
    is24Hour = true,
    minuteGap = MinuteGap.FIVE
)
```
The interval between consecutive items in the minute list is determined by the **_minuteGap_** parameter. When minuteGap is set to MinuteGap.FIVE, the minutes in the time picker will be displayed in increments of 5, such as 00, 05, 10,..., 55. The default value for minuteGap is MinuteGap.ONE, which means the minutes will be displayed in sequential order from 00 to 59.


#### Customize the Appearance
```kotlin
TimePicker(
    modifier = Modifier
        .padding(16.dp)
        .background(Color(0xFF1B5E20), RoundedCornerShape(8.dp)),
    onTimeSelected = { hour, minute ->
    },
    configuration = TimePickerConfiguration.Builder()
        .numberOfTimeRowsDisplayed(count = 5)
        .selectedTimeScaleFactor(scaleFactor = 1.4f)
        .build()
)
```
There are a total of 8 attributes available for users to customize the appearance of the time picker.


## Troubleshot

* If multiple date/time pickers are used, a unique **_id_** parameter should be included in the function call for each composable.

* When adjusting the height of a date/time picker, it is recommended to use **_TimePickerConfiguration.Builder().height()_** instead of **_Modifier.height()_** to ensure smooth rendering.
