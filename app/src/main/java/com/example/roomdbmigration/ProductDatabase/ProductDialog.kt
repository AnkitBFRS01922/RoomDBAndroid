package com.example.roomdbmigration.ProductDatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.roomdbmigration.R

class ProductDialog(product: Product, addProduct: Boolean, appCallback: AppCallback) : DialogFragment() {

    var product: Product
    var addProduct: Boolean
    var appCallback: AppCallback
    lateinit var etProductName: TextView
    lateinit var etProductCost: TextView
    lateinit var btnAddUpdate: Button
    lateinit var etProductDescription: TextView

    init {
        this.product = product
        this.addProduct = addProduct
        this.appCallback = appCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etProductName = view.findViewById(R.id.et_product_name)
        etProductCost = view.findViewById(R.id.et_product_cost)
        etProductDescription = view.findViewById(R.id.et_product_desc)
        btnAddUpdate = view.findViewById(R.id.btn_add_update)

        if(addProduct){
            btnAddUpdate.text = "Add Product"
        }else{
            btnAddUpdate.text = "Update Product"
        }

        etProductName.text = product.productName
        etProductCost.text = product.productCost.toString()
        etProductDescription.text = product.productDescription
        btnAddUpdate.setOnClickListener {
            product.productName = etProductName.text.toString()
            product.productCost = etProductCost.text.toString().toInt()
            product.productDescription = etProductDescription.text.toString()
            appCallback.run(product)
            dismiss()
        }
    }
}