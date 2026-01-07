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

<br>

__Step 2.__ Add the dependency
```kotlin
dependencies {
    ..
    implementation 'com.github.vsnappy1:ComposeDatePicker:2.2.0'
}
```
<br>

## Usage
Adding a date picker or time picker is incredibly easy, requiring just two lines of code.

```kotlin
DatePicker(onDateSelected = { year, month, day ->
    
})
```
![DatePicker Gif](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/b56c58e4-ce7a-4c62-bb06-7e2e96a59b33)
![Screenshot_datepicker_1 (1)](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/740ec79f-d7d5-407b-9010-beab4774169e)

<br>

```kotlin
TimePicker(onTimeSelected = { hour, minute ->

})
```
![TimePicker Gif](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/15f21b3f-93ac-4f79-8174-46ed128cb7ff)
![Screenshot_timepicker](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/de0373a3-f7f6-42bd-9f4f-0a221ca68d98)

<br>

## Customization
The date and time picker offer extensive customization options, allowing users to modify the 
TextStyle, Color, Size, Shape, and other elements to align with their preferred theme.

<br>

### Date Picker
___

#### Set Custom Date :

Please note that the **_year_** should be within a range of <current_year> ± 100 (inclusive). Additionally, for the **_month_**, 
please keep in mind that 0 represents January, while 11 corresponds to December.

```kotlin
DatePicker(
    onDateSelected = { year, month, day ->
    },
    date = DatePickerDate(year = 2023, month = 0, day = 5)
)
```
<br>

#### Set Custom Day Names :

Please note that the days parameter must contain exactly 7 values, starting from Sunday and ending with Saturday.
This enables consumers to localize weekday labels or provide custom formats (e.g., abbreviated or non-English day names).

```kotlin
DatePicker(
    onDateSelected = { year, month, day ->
    },
    days = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb") // Spanish
)
```
<br>

#### Set Selection Limit :

Please note that the selectionLimiter restricts date selection to the specified range (inclusive).
Any dates outside the fromDate and toDate boundaries will be disabled and cannot be selected by the user.

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
<br>

#### Customize the Appearance :

The configuration parameter allows extensive customization of the date picker’s appearance.
In addition to dateTextStyle, selectedDateTextStyle, and selectedDateBackgroundColor, there are 20 configurable attributes available—covering dimensions, typography, colors, and layout behavior.

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
<br>

### Time Picker
___

#### Set Custom Time :

Please note that the hour must be within the range 0–23, and the minute must be within 0–59 (inclusive).
If no custom time is provided, the time picker defaults to the current system time.

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
<br>

#### Set Is24Hour & MinuteGap :

The is24Hour parameter controls whether the time picker uses a 24-hour or 12-hour format.
The minuteGap parameter defines the interval between consecutive minute values.

When minuteGap is set to MinuteGap.FIVE, the minute list is displayed in 5-minute increments (e.g., 00, 05, 10, …, 55).
By default, minuteGap is MinuteGap.ONE, which displays minutes sequentially from 00 to 59.

```kotlin
TimePicker(
    onTimeSelected = { hour, minute ->
    },
    is24Hour = true,
    minuteGap = MinuteGap.FIVE
)
```
<br>

#### Customize the Appearance :

The configuration parameter provides fine-grained control over the appearance and behavior of the time picker.
A total of 8 configurable attributes are available, allowing customization of layout, scaling, spacing, and visual emphasis.

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
<br>

## Troubleshot

* If multiple date/time pickers are used, a unique **_id_** parameter should be included in the function call for each composable.

* When adjusting the height of a date/time picker, it is recommended to use **_TimePickerConfiguration.Builder().height()_** instead of **_Modifier.height()_** to ensure smooth rendering.
