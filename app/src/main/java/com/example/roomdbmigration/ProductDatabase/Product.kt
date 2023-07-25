package com.example.roomdbmigration.ProductDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @ColumnInfo("product_name") var productName: String,
    @ColumnInfo(name = "product_cost") var productCost: Int,
    @ColumnInfo(name = "product_descriptioin") var productDescription: String
    ){

    @PrimaryKey(autoGenerate = true) var id: Int = 0
        set(value) {
            field = value
        }
}