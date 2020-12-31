package com.neo.bookkeeperroom

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SearchViewModel(application: Application) : AndroidViewModel(application) {

	private val bookRepository = BookRepository(application)

	init {
	}

	fun update(book: Book) {
		bookRepository.update(book)
	}

	fun delete(book: Book) {
		bookRepository.delete(book)
	}

	fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>>?{
		return bookRepository.getBooksByBookOrAuthor(searchQuery)
	}
}
