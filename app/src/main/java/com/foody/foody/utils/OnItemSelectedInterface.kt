package com.foody.foody.utils

interface OnItemSelectedInterface {
    suspend fun onItemClick(position: Int)
}