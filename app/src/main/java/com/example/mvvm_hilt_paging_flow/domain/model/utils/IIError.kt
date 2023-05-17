package com.example.mvvm_hilt_paging_flow.domain.model.utils

import android.content.Context
import androidx.paging.LoadState
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.utils.isNetworkAvailable
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class IIError (cause:Throwable):Throwable(cause)
class NetworkError(cause: Throwable):IIError(cause)
class UnknownError(cause: Throwable):IIError(cause)


fun Throwable.toIIError():IIError=
    when(this){
        is IIError->this
        is UnknownHostException -> NetworkError(this)
        is HttpException -> NetworkError(this)
        else -> com.example.mvvm_hilt_paging_flow.domain.model.utils.UnknownError(this)

    }

fun LoadState.Error.toIIError():IIError =
         error.toIIError()

fun IIError.getHumanReadableText(context:Context) =
    when(this){
        is NetworkError ->{
            if(!context.isNetworkAvailable()){
                R.string.no_internet
            }else{
                R.string.general_network_error
            }
        }
        else  -> {
            R.string.unknown_error
        }
    }.let {
        context.getString(it)
    }
