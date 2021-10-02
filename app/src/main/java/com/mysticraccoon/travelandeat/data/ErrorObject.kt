package com.mysticraccoon.travelandeat.data

data class ErrorObject(
    val isError: Boolean = false,
    val errorType: ErrorType? = null
)

enum class ErrorType{
    NETWORK,
    SERVICE,
    UNKNOWN
}
