package com.messon.faculty.app.utils

enum class BillingRangeType(val value: String) {
    LAST_N_DAYS("last_n_days"),
    CUSTOM("custom"),
}


enum class PredefinedOptions(val value: String,val numDays: Int){
    LAST_7_DAYS("Last 7 days",7),
    LAST_30_DAYS("Last 30 days",30),
    LAST_60_DAYS("Last 60 days",60)
}