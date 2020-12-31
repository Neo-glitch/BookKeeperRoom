package com.neo.bookkeeperroom

import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    companion object{
        val NEW_BOOK_ACTIVITY_REQUEST_CODE = 1
        val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
    }

    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val booksListAdapter = BookListAdapter(this, this)
        recyclerview.adapter = booksListAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
        }

        bookViewModel = ViewModelProviders.of(this)[BookViewModel::class.java]

        bookViewModel.allBooks.observe(this, androidx.lifecycle.Observer {
            it?.let {
                // passes list to needed observer if only is not null
                booksListAdapter.setBooks(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val id = UUID.randomUUID().toString()
            val authorName = data!!.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data.getStringExtra(NewBookActivity.NEW_BOOK)
            val description = data.getStringExtra(NewBookActivity.NEW_DESCRIPTION)
            val currentTIme = Calendar.getInstance().time

            val book = Book(id, authorName!!, bookName!!, description!!, currentTIme)
            bookViewModel.insert(book)

            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
        }else if(requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val id = data!!.getStringExtra(EditBookActivity.ID)
            val authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description = data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
            val currentTIme = Calendar.getInstance().time

            val book = Book(id!!, authorName!!, bookName!!, description!!, currentTIme)

            bookViewModel.update(book)

            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, R.string.not_saved, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // gets the searchView and set searchable config
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        // setting SearchResult Activity to show result of search
        val componentName = ComponentName(this, SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDeleteClickListener(myBook: Book) {
        bookViewModel.delete(myBook)
        Toast.makeText(this@MainActivity, R.string.deleted, Toast.LENGTH_SHORT).show()
    }
}