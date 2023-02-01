package com.example.mtoilet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtoilet.data.Repository

class MToiletViewModel : ViewModel() {
    private var fetchedUrl = MutableLiveData<Boolean>()
    private var fetchedPaymentStatus = MutableLiveData<Boolean>()
    fun fetchedUrl() : LiveData<Boolean>{
        return fetchedUrl
    }
    fun fetchedPaymentStatus() : LiveData<Boolean>{
        return fetchedPaymentStatus
    }
    fun initializeValue(){
        fetchedUrl.value = false
        fetchedPaymentStatus.value = false
    }

    fun getUrl(deviceId : Int){
        val repository = Repository()
        fetchedUrl.value = repository.getUrl(deviceId)
    }
    fun getCheckPay(id : String){
        val repository = Repository()
        fetchedPaymentStatus.value = repository.getCheckPay(id)
    }
}