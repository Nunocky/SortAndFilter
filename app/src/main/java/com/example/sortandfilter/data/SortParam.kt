package com.example.sortandfilter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SortParam(
    val field: Int = 0, // 0(id), 1(title)
    val order: Int = 0 // 0(ascend), 1(descend)
) : Parcelable