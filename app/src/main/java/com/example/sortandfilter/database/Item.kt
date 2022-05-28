package com.example.sortandfilter.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "title")
    val title: String
)

@Dao
interface ItemDAO {
    @Insert
    fun insert(item: Item)

    @Query("select * from items")
    fun findAll(): List<Item>

    @Query("select * from items")
    fun findAllAsFlow(): Flow<List<Item>>

    // TODO update, delete, .etc.
}

@Singleton
class ItemRepository @Inject constructor(private val dao: ItemDAO) {
    fun insert(item: Item) = dao.insert(item)
    fun findAll() = dao.findAll()
    fun findAllAsFlow() = dao.findAllAsFlow()
}


