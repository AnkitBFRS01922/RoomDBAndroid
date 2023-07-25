package com.example.roomdbmigration

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdbmigration.ProductDatabase.AppCallback
import com.example.roomdbmigration.ProductDatabase.Product
import com.example.roomdbmigration.ProductDatabase.ProductDao

@Database(entities = [Product::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}