package com.example.shopinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopinglist.data.ShopListRepositoryImpl
import com.example.shopinglist.domain.AddShopItemUseCase
import com.example.shopinglist.domain.EditShopItemUseCase
import com.example.shopinglist.domain.GetShopItemUseCase
import com.example.shopinglist.domain.ShopItem

class ShopItemViewModel: ViewModel() {

    private  val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishwork()
        }

    }

    fun editShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            //Редактируем только если _shopItem не NULL
            _shopItem.value?.let {
                //Получаем объект копированием существующего, но с новыми name и count
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishwork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?:""
    }

    private fun parseCount(inputCount: String?): Int{
       return try{
           inputCount?.trim()?.toInt() ?:0
       }catch(e: Exception){
           0
       }
    }

    private fun validateInput(name: String, count: Int): Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputName.value = true
            result = false
        }
        if (count <= 0){
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

   fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishwork(){
        _shouldCloseScreen.value = Unit
    }

}