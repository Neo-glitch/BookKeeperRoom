package com.neo.bookkeeperroom

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BookListAdapter(private val context: Context, private val onDeleteClickListener: OnDeleteClickListener) :
	RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

	interface OnDeleteClickListener{
		fun onDeleteClickListener(myBook: Book)
	}

	private var booksList: List<Book> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
		val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
		return BookViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
		val book = booksList[position]
		holder.setData(book.book, book.lastUpdated!!, position)
		holder.setListeners()
	}

	override fun getItemCount(): Int = booksList.size
	fun setBooks(it: List<Book>) {
		booksList = it
		notifyDataSetChanged()
	}

	inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private var pos: Int = 0

		fun setData(book: String, lastUpdated: Date?, position: Int){
			itemView.tvBook.text = book
			itemView.tvLastUpdated.text = getFormattedDate(lastUpdated)
			this.pos = position
		}

		private fun getFormattedDate(lastUpdated: Date?): CharSequence? {
			// used to just format date in readable form to man
			var time = "Last Updated"
			time += lastUpdated?.let {
				val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
				sdf.format(lastUpdated)
			} ?: "Not found"
			return time
		}

		fun setListeners() {
			itemView.setOnClickListener {
				val intent = Intent(context, EditBookActivity::class.java)
				intent.putExtra("id", booksList[pos].id)
				intent.putExtra("author", booksList[pos].author)
				intent.putExtra("book", booksList[pos].book)
				intent.putExtra("description", booksList[pos].description)
				intent.putExtra("lastUpdated", getFormattedDate(booksList[pos].lastUpdated!!))
				(context as AppCompatActivity).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE)
			}

			itemView.ivRowDelete.setOnClickListener {
				onDeleteClickListener.onDeleteClickListener(booksList[pos])
			}
		}
	}
}
