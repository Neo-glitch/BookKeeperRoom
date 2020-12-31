package com.neo.bookkeeperroom

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class BookRepository(application: Application) {
    private val bookDao: BookDao
    val allBooks: LiveData<List<Book>>

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun insert(book: Book){
        InsertAsyncTask(bookDao).execute(book)
    }

    fun update(book: Book){
        updateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        deleteAsyncTask(bookDao).execute(book)
    }

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>>?{
        return bookDao.getBooksByBookOrAuthor(searchQuery)
    }

    companion object{

        // could have used executor, coroutines, or rx java sha
        private class InsertAsyncTask(private val bookDao: BookDao):
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg p0: Book?): Void? {
                bookDao.insert(p0[0]!!)
                return null
            }

        }

        private class updateAsyncTask(private val bookDao: BookDao):
            AsyncTask<Book, Void, Void>(){
            override fun doInBackground(vararg p0: Book?): Void? {
                bookDao.update(p0[0]!!)
                return null
            }
        }

        private class deleteAsyncTask(private val bookDao: BookDao):
            AsyncTask<Book, Void, Void>(){
            override fun doInBackground(vararg p0: Book?): Void? {
                bookDao.delete(p0[0]!!)
                return null
            }
        }
    }

}