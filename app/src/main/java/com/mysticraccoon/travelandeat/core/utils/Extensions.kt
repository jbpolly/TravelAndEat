package com.mysticraccoon.travelandeat.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun String.toDoubleOrNullFromUS(): Double? {
    val decimalFormat = DecimalFormat.getInstance(Locale.US) as DecimalFormat
    return decimalFormat.parse(this)?.toDouble()
}

fun Double.formatWithSeparators(): String {
    return (NumberFormat.getNumberInstance(Locale.US) as DecimalFormat)
        .apply {
            applyPattern("###,###,###,###,##0.00")
        }
        .format(this)
}

/*
* throttleLatest works similar to debounce but it operates on time intervals and returns
* the latest data for each one, which allows you to get and process intermediate data if you need to.
* */
fun <T> throttleLatest(
    intervalMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    var latestParam: T
    return { param: T ->
        latestParam = param
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                delay(intervalMs)
                latestParam.let(destinationFunction)
            }
        }
    }
}

/*
*
* throttleFirst is useful when you need to process the first call right away and then skip subsequent
* calls for some time to avoid undesired behavior (avoid starting two identical activities on Android, for example).
* */
fun <T> throttleFirst(
    skipMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                destinationFunction(param)
                delay(skipMs)
            }
        }
    }
}

/*
* debounce helps to detect the state when no new data is submitted for some time, effectively allowing you to process a data when the input is completed.
* */
fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}