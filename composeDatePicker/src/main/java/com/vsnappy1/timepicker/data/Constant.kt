package com.vsnappy1.timepicker.data

import com.vsnappy1.timepicker.enums.MinuteGap

internal object Constant {

    private const val repeatCount: Int = 200

    private fun findHours(is24Hour: Boolean): List<String> {
        if (is24Hour) return listOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23"
        )
        return listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    }

    fun getHours(is24Hour: Boolean): List<String> {
        val list = mutableListOf<String>()
        for (i in 1..repeatCount) {
            list.addAll(findHours(is24Hour))
        }
        return list
    }

    fun getMiddleOfHour(is24Hour: Boolean): Int {
        return if (is24Hour) 24 * (repeatCount / 2)
        else 12 * (repeatCount / 2) - 1
    }

    private fun findMinutes(minuteGap: MinuteGap): List<String> {
        var value = 0
        val list = mutableListOf<String>()
        while (value < 60) {
            list.add("${if (value < 10) "0" else ""}${value}")
            value += minuteGap.gap
        }
        return list
    }

    fun getMinutes(minuteGap: MinuteGap): List<String> {
        val list = mutableListOf<String>()
        for (i in 1..repeatCount) {
            list.addAll(findMinutes(minuteGap))
        }
        return list
    }

    fun getMiddleOfMinute(minuteGap: MinuteGap): Int {
        return if (minuteGap.gap == 1) 60 * (repeatCount / 2)
        else if (minuteGap == MinuteGap.FIVE) 12 * (repeatCount / 2)
        else 6 * (repeatCount / 2)
    }

    fun getNearestNextMinute(minute: Int, minuteGap: MinuteGap): Int {
        if (minuteGap.gap == 1) return minute
        var value = 0
        while (value < minute) {
            value += minuteGap.gap
        }
        return if (value >= 60) 0
        else value
    }

    fun getTimesOfDay(): List<String> {
        return listOf("AM", "PM")
    }
}