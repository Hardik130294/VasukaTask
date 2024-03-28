package com.hardik.challengearraylistfilter.data.remote.dto

sealed class PurchaseOrder {
    data class IntegerValue(val value: Int) : PurchaseOrder()
    data class BooleanValue(val value: Boolean) : PurchaseOrder()
}