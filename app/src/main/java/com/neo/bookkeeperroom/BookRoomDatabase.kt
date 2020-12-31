package com.neo.bookkeeperroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Book::class], version = 3)
@TypeConverters(DateTypeConverter::class )  // used if we want to use a typeConverter in db
abstract class BookRoomDatabase : RoomDatabase() {

    // dao obj
    abstract fun bookDao(): BookDao

    // creates room db
    companion object {
        private var bookRoomInstance: BookRoomDatabase? = null

        val MIGRATION_1_2: Migration = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table books " +
                        " add column description text default 'add description' " +
                        " not null")
            }
        }

        // migration plan
        val MIGRATION_2_3: Migration = object: Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table books " +
                        " add column last_updated integer default null")
            }
        }

        // fun that creates db or return single instance when need be
        fun getDatabase(context: Context): BookRoomDatabase? {
            if (bookRoomInstance == null) {
                synchronized(BookRoomDatabase::class.java) {
                    if (bookRoomInstance == null) {
                        bookRoomInstance =
                            Room.databaseBuilder<BookRoomDatabase>(
                                context.applicationContext,
                                BookRoomDatabase::class.java, "book_database"
                            )
                                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                                .build()
                    }
                }
            }
            return bookRoomInstance
        }
    }
}