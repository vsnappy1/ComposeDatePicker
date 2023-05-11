package com.vsnappy1.datepicker.enums

enum class Days(val abbreviation: String, val value: String, val number: Int) {
    SUNDAY("SUN", "Sunday", 1),
    MONDAY("MON", "Monday", 2),
    TUESDAY("TUE", "Tuesday", 3),
    WEDNESDAY("WED", "Wednesday", 4),
    THURSDAY("THU", "Thursday", 5),
    FRIDAY("FRI", "Friday", 6),
    SATURDAY("SAT", "Saturday", 7);

    companion object {
        fun get(number: Int): Days {
            return when (number) {
                1 -> SUNDAY
                2 -> MONDAY
                3 -> TUESDAY
                4 -> WEDNESDAY
                5 -> THURSDAY
                6 -> FRIDAY
                else -> SATURDAY
            }
        }
    }
}