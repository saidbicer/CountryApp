package com.said.contryapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.said.contryapp.model.Country

@Dao
interface CountryDao {
    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    @Query(value = "select * from Country")
    suspend fun getAllCountries() : List<Country>

    @Query(value = "select * from Country where uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query(value = "delete from country")
    suspend fun deleteAllCountries()
}