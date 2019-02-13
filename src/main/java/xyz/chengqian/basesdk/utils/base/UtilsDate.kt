package xyz.chengqian.basesdk.utils.base

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by chenAstro 2017/9/26.
 */

object UtilsDate {


    fun getDate(date: Date): String {
        val sf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sf.format(date)
    }

    fun getDate(date: Date, pattern: String): String {
        val sf = SimpleDateFormat(pattern, Locale.getDefault())
        return sf.format(date)
    }

    fun getDate(date: String, pattern: String): Date {
        val sf = SimpleDateFormat(pattern, Locale.getDefault())
        return sf.parse(date)
    }

    val year: String
        get() {
            val date = Date()
            val sf = SimpleDateFormat("yyyy", Locale.getDefault())
            return sf.format(date)
        }
    val month: String
        get() {
            val date = Date()
            val sf = SimpleDateFormat("MM", Locale.getDefault())
            return sf.format(date)
        }
    val day: String
        get() {
            val date = Date()
            val sf = SimpleDateFormat("dd", Locale.getDefault())
            return sf.format(date)
        }

    //获取当日0点时间戳
    val dateStartTimeMillis: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

    //获取当日24点时间戳
    val dateEndTimeMillis: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 24)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

    //时间戳转换成字符窜
    fun getDateToString(time: Long): String {
        val date = Date(time)
        val sf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sf.format(date)
    }

    //将字符串转为时间戳
    fun getStringToDate(time: String): Long {
        val sf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var date = Date()
        try {
            date = sf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date.time
    }

    //时间戳转换为发布时间
    fun timeMillsToString(timeMills: Long?): String {
        val currentTimeMillis = System.currentTimeMillis()
        val time = (currentTimeMillis - timeMills!!) / 1000
        val s = when {
            time / 60 < 1 -> "刚刚"
            time / 60 < 60 -> (time / 60).toString() + "分钟前"
            time / (60 * 60) < 24 -> (time / (60 * 60)).toString() + "小时前"
            time / (60 * 60 * 24) < 30 -> (time / (60 * 60 * 24)).toString() + "天前"
            time / (60 * 60 * 24 * 30) < 12 -> (time / (60 * 60 * 24 * 30)).toString() + "月前"
            else -> (time / (60 * 60 * 24 * 30 * 12)).toString() + "年前"
        }

        return s
    }

    //时间戳转化为评论时间
    @SuppressLint("SimpleDateFormat")
    fun timeMillsToDate(timeMills: Long?): String {
        var s = ""

        val dateString = getDateToString(timeMills!!)
        val currentString = getDateToString(System.currentTimeMillis())
        try {
            val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)

            if (timeMills > dateStartTimeMillis && timeMills < dateEndTimeMillis) {//今天
                val l = (timeMills - dateStartTimeMillis) / 1000
                if (l % 3600 / 60 < 10) {
                    s = (l / 3600).toString() + ":0" + l % 3600 / 60
                } else {
                    s = (l / 3600).toString() + ":" + l % 3600 / 60
                }
            } else if (timeMills < dateStartTimeMillis && timeMills > dateStartTimeMillis - 24 * 3600 * 1000) {//昨天
                val l = (timeMills - (dateStartTimeMillis - 24 * 3600 * 1000)) / 1000
                if (l % 3600 / 60 < 10) {
                    s = "昨天 " + l / 3600 + ":0" + l % 3600 / 60
                } else {
                    s = "昨天 " + l / 3600 + ":" + l % 3600 / 60
                }
            } else if (dateString.substring(0, 4).equals(currentString.substring(0, 4), ignoreCase = true)) {//今年
                val substring = dateString.substring(5, dateString.length)
                val l = date.time
                val time = (timeMills - l) / 1000
                if ((time and 3600) / 60 < 10) {
                    s = substring + " " + time / 3600 + ":0" + time % 3600 / 60
                } else {
                    s = substring + " " + time / 3600 + ":" + time % 3600 / 60
                }

            } else {//不是今年
                val l = date.time
                val time = (timeMills - l) / 1000
                if ((time and 3600) / 60 < 10) {
                    s = dateString + " " + time / 3600 + ":0" + time % 3600 / 60
                } else {
                    s = dateString + " " + time / 3600 + ":" + time % 3600 / 60
                }

            }

        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return s
    }

    fun getDatePicker(dialog: DatePickerDialog): DatePicker {
        return dialog.datePicker
    }
}
