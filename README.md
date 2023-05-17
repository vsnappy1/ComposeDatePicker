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
    implementation 'com.github.vsnappy1:ComposeDatePicker:1.2.2'
}
```


## Usage
Adding a date picker or time picker is incredibly easy, requiring just two lines of code.

For date picker
```kotlin
DatePicker(onDateSelected = { year, month, day ->
    
})
```

For time picker
```kotlin
TimePicker(onTimeSelected = { hour, minute, timeOfDay ->

})
```

## Customization
The date and time picker offer extensive customization options, allowing users to modify the 
TextStyle, Color, Size, Shape, and other elements to align with their preferred theme.

<br>

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
Please note that the *year* should be within a range of <current_year> ± 100 (inclusive). Additionally, for the *month*, 
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
In addition to dateTextStyle, selectedDateTextStyle, and selectedDateBackgroundColor, there are a total of 
20 attributes available for users to customize the appearance of the date picker.

<br>

### Time Picker
___
#### Set Custom Time
```kotlin
TimePicker(
    onTimeSelected = { hour, minute, timeOfDay ->
    },
    time = ComposeTimePickerTime.TwelveHourTime(
        hour = 12,
        minute = 45,
        timeOfDay = TimeOfDay.AM
    )
)
```

#### Set Is24Hour & MinuteGap
```kotlin
TimePicker(
    onTimeSelected = { hour, minute, timeOfDay ->
    },
    is24Hour = true,
    minuteGap = MinuteGap.ONE
)
```
The *minuteGap* parameter determines the interval between consecutive items in the list. 
For example, if set to MinuteGap.FIVE, the minutes in the time picker will be displayed in the order 00, 05, 10,..., 60.


#### Customize the Appearance
```kotlin
TimePicker(
    modifier = Modifier
        .padding(16.dp)
        .background(Color(0xFF1B5E20), RoundedCornerShape(8.dp)),
    onTimeSelected = { hour, minute, timeOfDay ->
    },
    configuration = TimePickerConfiguration.Builder()
        .numberOfTimeRowsDisplayed(count = 5)
        .selectedTimeScaleFactor(scaleFactor = 1.4f)
        .build()
)
```
There are a total of 8 attributes available for users to customize the appearance of the time picker.
