# ComposeDatePicker
An Android Jetpack Compose library that provides a Date & Time Picker functionality.

[![](https://jitpack.io/v/vsnappy1/ComposeDatePicker.svg)](https://jitpack.io/#vsnappy1/ComposeDatePicker)

## Setup
Step 1. Add the JitPack repository to your build file (Add it in your root build.gradle at the end of repositories)
```kotlin
allprojects {
    repositories {
        ..
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```kotlin
dependencies {
    ..
    implementation 'com.github.vsnappy1:ComposeDatePicker:2.0.0'
}
```

## Usage
Adding a date picker or time picker is incredibly easy, requiring just two lines of code.

```kotlin
DatePicker(onDateSelected = { year, month, day ->
    
})
```
![Screenshot_datepicker_1 (1)](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/740ec79f-d7d5-407b-9010-beab4774169e) -
![Screenshot_datepicker_2](https://github.com/vsnappy1/ComposeDatePicker/assets/42217840/daa6f19f-be5b-46ab-8844-58b0e61c6545)

```kotlin
TimePicker(onTimeSelected = { hour, minute ->

})
```
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
    date = ComposeDatePickerDate(year = 2023, month = 0, day = 5)
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
        fromDate = ComposeDatePickerDate(year = 2023, month = 4, day = 7),
        toDate = ComposeDatePickerDate(year = 2023, month = 4, day = 21)
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
    time = ComposeTimePickerTime(
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
The **_minuteGap_** parameter determines the interval between consecutive items in the list. 
For example, if set to MinuteGap.FIVE, the minutes in the time picker will be displayed in the order 00, 05, 10,..., 60.


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
