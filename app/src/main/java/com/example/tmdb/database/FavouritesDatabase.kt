package com.example.tmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.dao.FavouritesDao
import com.example.tmdb.models.ResultsItem

@Database(entities = [ResultsItem::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}