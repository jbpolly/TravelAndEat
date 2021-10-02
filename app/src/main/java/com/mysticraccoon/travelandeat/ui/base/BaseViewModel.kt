package com.mysticraccoon.travelandeat.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(app: Application): AndroidViewModel(app) {

    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

}