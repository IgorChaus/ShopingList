package com.example.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shopinglist.R
import com.example.shopinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity: AppCompatActivity() {

//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var buttonSave: Button
//
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFIND_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parsIntent()
//        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
//        initViews()
//        addTextChangeListeners()
        launchRightMode()
//        obderverViewModel()
    }

//    private fun obderverViewModel() {
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//            tilCount.error = message
//        }
//
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceModeEdit(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
        else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }
//
//    private fun addTextChangeListeners() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//        })
//    }
//
//    private fun launchEditMode(){
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this){
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//        buttonSave.setOnClickListener {
//            viewModel.editShopItem(etName.text?.toString(), etCount.text.toString())
//        }
//    }
//
//    private fun launchAddMode(){
//        buttonSave.setOnClickListener {
//            viewModel.addShopItem(etName.text?.toString(), etCount.text.toString())
//        }
//    }
//
    private fun parsIntent(){
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFIND_ID)
        }

    }
//
//    private fun initViews(){
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.save_button)
//    }
//
//
    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""



        fun newIntentAddedItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, ShopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, ShopItemId)
            return intent
        }
    }
}