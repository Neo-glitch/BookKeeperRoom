package com.neo.bookkeeperroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_book.*

class EditBookActivity : AppCompatActivity() {

	var id: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_book)

		val bundle = intent.extras

		// retrieves info from intent starting this activity
		bundle?.let {
			id = bundle.getString("id")
			val book = bundle.getString("book")
			val author = bundle.getString("author")
			val description = bundle.getString("description")
			val lastUpdated = bundle.getString("lastUpdated")

			etAuthorName.setText(author)
			etBookName.setText(book)
			etDescription.setText(description)
			txvLastUpdated.text = lastUpdated
		}


		bSave.setOnClickListener {
			val updatedAuthor = etAuthorName.text.toString()
			val updatedBook = etBookName.text.toString()
			val updatedDescription = etDescription.text.toString()

			// pass info back to resultIntent to be sent to mainActivity
			val resultIntent = Intent()
			resultIntent.putExtra(ID, id)
			resultIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
			resultIntent.putExtra(UPDATED_BOOK, updatedBook)
			resultIntent.putExtra(UPDATED_DESCRIPTION, updatedDescription)
			setResult(Activity.RESULT_OK, resultIntent)
			finish()
		}

		bCancel.setOnClickListener {
			finish()
		}
	}

	companion object {
		const val ID = "book_id"
		const val UPDATED_AUTHOR = "author_name"
		const val UPDATED_BOOK = "book_name"
		const val UPDATED_DESCRIPTION = "description"
	}
}