package com.example.hwhong.noteapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ListView listview;
    public static NoteAdapter adapter;
    private ArrayList<Note> notesList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        listview = (ListView) findViewById(R.id.listView);

        notesList = db.getAllNotes();
        adapter = new NoteAdapter(getApplicationContext(), notesList);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteAddClass.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = db.getNote(i);
                Intent intent = new Intent(getApplicationContext(), NoteViewClass.class);
                intent.putExtra("title", note.getTitle().toString());
                intent.putExtra("message", note.getContent().toString());

                startActivity(intent);
            }
        });

        listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note toRemove = notesList.get(i);
                notesList.remove(i);
                db.deleteNote(toRemove);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}
