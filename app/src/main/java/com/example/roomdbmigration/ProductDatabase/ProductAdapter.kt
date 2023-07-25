package com.example.roomdbmigration.ProductDatabase

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbmigration.R

class ProductAdapter(list: List<Product>, edit: AppCallback, delete: AppCallback) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productList: List<Product>
    var editCallback: AppCallback
    var deleteCallback: AppCallback

    init {
        productList = list
        editCallback = edit
        deleteCallback = delete
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvProductName:TextView
        var tvProductCost:TextView
        var tvProductDesc:TextView

        var btnProductEdit:Button
        var btnProductDelete:Button

        init {
            tvProductName = itemView.findViewById(R.id.tv_product_name)
            tvProductCost = itemView.findViewById(R.id.tv_product_cost)
            tvProductDesc = itemView.findViewById(R.id.tv_product_description)

            btnProductEdit = itemView.findViewById(R.id.btn_product_edit)
            btnProductDelete = itemView.findViewById(R.id.btn_product_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvProductName.text = productList[position].productName
        holder.tvProductCost.text = productList[position].productCost.toString()
        holder.tvProductDesc.text = productList[position].productDescription

        val index = position

        holder.btnProductEdit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.d("app log", "button edit clicked")
                editCallback.run(productList[index])
            }
        })

        holder.btnProductDelete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.d("app log", "button delete clicked")
                deleteCallback.run(productList[index])
            }
        })
    }
}