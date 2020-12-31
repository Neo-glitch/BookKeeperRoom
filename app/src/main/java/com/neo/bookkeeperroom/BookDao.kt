package com.neo.bookkeeperroom

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface BookDao {

    @Insert
    fun insert(book: Book)

//    @Query("select * from books")
//    fun getAllBooks(): LiveData<List<Book>>

    // alt way of querying for booksList
    @get:Query("select * from books")
    val allBooks: LiveData<List<Book>>

    @Query("select * from books where book like :searchString or author like :searchString")
    fun getBooksByBookOrAuthor(searchString: String): LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}