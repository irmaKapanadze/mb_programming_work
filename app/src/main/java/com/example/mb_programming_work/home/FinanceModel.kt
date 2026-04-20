package com.example.mb_programming_work.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//gavxade 'parcelable',rom moxdes misi fragmentebs shoris gadacema
@Parcelize
data class FinanceModel(
    val salary: Double,
    val rent: Double,
    val food: Double,
) : Parcelable