package com.said.contryapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.said.contryapp.model.Country
import com.said.contryapp.service.MyDatabase
import kotlinx.coroutines.launch

class DetailViewModel (application: Application) : BaseViewModel(application = application) {
    var countryLiveData  = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){
        launch {
            val dao = MyDatabase(getApplication()).countryDao()
            countryLiveData.value = dao.getCountry(uuid)
        }
    }
}