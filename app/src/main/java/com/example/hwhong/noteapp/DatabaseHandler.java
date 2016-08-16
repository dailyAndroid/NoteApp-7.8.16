package com.example.hwhong.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwhong on 8/6/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notesManager";

    // Contacts table name
    private static final String TABLE_OF_NOTES = "notes";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DATABASE_TABLE = "CREATE TABLE "
                + TABLE_OF_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_NOTES);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    //!--------- Below to Handle CRUD Operations (Create, Read, Update, Delete)--------------!

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle()); // Note Title
        values.put(KEY_CONTENT, note.getContent()); // Note Message

        Log.d("add", note.getTitle());

        // Inserting Row
        db.insert(TABLE_OF_NOTES, null, values);
        db.close(); // Closing database connection
    }

    //accepts a single id and returns the corresponding row item to this ID
    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OF_NOTES, new String[]
                { KEY_ID, KEY_TITLE, KEY_CONTENT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //the first constructor, fetching the contents of the row
        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return note;
    }

    //returns all notes in an arraylist format
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OF_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));

                // Adding contact to list
                noteArrayList.add(note);
            } while (cursor.moveToNext());
        }

        // return contact list
        return noteArrayList;
    }

    //returns the total number of notes in the database
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OF_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    //updates a single note object i the database
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());

        // updating row
        return db.update(TABLE_OF_NOTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId())});
    }

    //deleting a note
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OF_NOTES, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }
}
