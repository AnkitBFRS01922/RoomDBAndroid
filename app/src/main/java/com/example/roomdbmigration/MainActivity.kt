package com.example.roomdbmigration

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.roomdbmigration.ProductDatabase.AppCallback
import com.example.roomdbmigration.ProductDatabase.Product
import com.example.roomdbmigration.ProductDatabase.ProductAdapter
import com.example.roomdbmigration.ProductDatabase.ProductDao
import com.example.roomdbmigration.ProductDatabase.ProductDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var productDao: ProductDao
    lateinit var rvProduct: RecyclerView
    lateinit var productList: List<Product>
    lateinit var productAdapter: ProductAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var editCallback: AppCallback
    lateinit var deleteCallback: AppCallback
    lateinit var btnAddProduct: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Products"
        }

        initProductDatabase()
        setupRecyclerView()
        initViews()
    }


    private fun initProductDatabase(){
        val productDb = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").allowMainThreadQueries().build()

        productDao = productDb.productDao()
    }

    private fun setupRecyclerView(){
        editCallback = object : AppCallback {
            override fun run(product: Product) {
                showProductDialog(product, false, object : AppCallback {
                    override fun run(product: Product) {
                        updateProduct(product)
                    }
                })
            }
        }

        deleteCallback = object : AppCallback{
            override fun run(product: Product) {
                showDeleteConfirmation(product)
            }
        }

        rvProduct = findViewById(R.id.rv_products)
        layoutManager = LinearLayoutManager(this@MainActivity)
        productList = productDao.getProducts()
        productAdapter = ProductAdapter(productList, editCallback, deleteCallback)
        rvProduct.adapter = productAdapter
        rvProduct.layoutManager = layoutManager
    }

    private fun initViews(){
        btnAddProduct = findViewById(R.id.fab_products)

        btnAddProduct.setOnClickListener {
            showProductDialog(Product("", 0, ""), true, object : AppCallback {
                override fun run(product: Product) {
                    addProduct(product)
                }
            })
        }
    }

    private fun updateProduct(product: Product){
        productDao.updateProduct(product)
        updateProductList()
//        runBlocking {
//            launch{
//                productDao.updateProduct(product)
//                runOnUiThread {
//                    productList = productDao.getProducts()
//                    productAdapter.notifyDataSetChanged()
//                }
//            }
//        }
    }

    private fun deleteProduct(product: Product){
        productDao.deleteProduct(product)
        updateProductList()
//        runBlocking {
//            launch{
//                productDao.deleteProduct(product)
//                runOnUiThread {
//                    productList = productDao.getProducts()
//                    productAdapter.notifyDataSetChanged()
//                }
//            }
//        }
    }

    private fun addProduct(product: Product){
        productDao.addProduct(product)
        updateProductList()
//        runBlocking {
//            coroutineScope{
//                launch {
//                    productDao.addProduct(product)
//                    runOnUiThread {
//                        productList = productDao.getProducts()
//                        productAdapter.notifyDataSetChanged()
//                    }
//                }
//            }
//        }
    }

    private fun updateProductList(){
        (productList as ArrayList<Product>).clear()
        val tempProductList = productDao.getProducts()

        for(product in tempProductList){
            (productList as ArrayList<Product>).add(product)
        }

        productAdapter.notifyDataSetChanged()
    }

    private fun showProductDialog(product: Product, addProduct: Boolean, appCallback: AppCallback){
        val productDialog: ProductDialog = ProductDialog(product, addProduct, appCallback)
        productDialog.show(supportFragmentManager, "show dialog")
        productDialog.isCancelable = true
    }

    private fun showDeleteConfirmation(product: Product){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Are you sure, you want to delete this product?")
        builder.setPositiveButton("YES", object: DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                deleteProduct(product)
            }
        })

        builder.setNegativeButton("NO", object: DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {

            }
        })

        builder.create().show()
    }
}