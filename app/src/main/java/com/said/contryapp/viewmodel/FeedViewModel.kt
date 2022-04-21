package com.said.contryapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.said.contryapp.model.Country
import com.said.contryapp.service.CountryAPIService
import com.said.contryapp.service.MyDatabase
import com.said.contryapp.util.Preff
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application = application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = Preff.invoke(getApplication()).getTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = MyDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from sqlite", Toast.LENGTH_LONG).show()
        }

    }

     fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                        countryError.value = true
                        countryLoading.value = false
                    }

                })
        )
        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_LONG).show()
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(countryList: List<Country>) {
        launch {
            val dao = MyDatabase.invoke(getApplication()).countryDao()
            dao.deleteAllCountries()
            val idList = dao.insertAll(*countryList.toTypedArray())
            var i = 0
            while (i < idList.size){
                countryList[i].uuid = idList[i].toInt()
                i++
            }

            showCountries(countryList)
        }

        Preff.invoke(getApplication()).saveTime(System.nanoTime())
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}