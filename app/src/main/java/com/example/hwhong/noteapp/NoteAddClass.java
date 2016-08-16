package com.example.hwhong.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hwhong on 8/6/16.
 */
public class NoteAddClass extends AppCompatActivity {

    private EditText title, message;
    private Button button;
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_add_layout);

        title = (EditText) findViewById(R.id.titleEditText);
        message = (EditText) findViewById(R.id.messageEditText);
        button = (Button) findViewById(R.id.finishButton);

        db = new DatabaseHandler(this);

        //MainActivity.adapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toAddTitle = title.getText().toString();
                String toAddMessage = message.getText().toString();

                db.addNote(new Note(toAddTitle, toAddMessage));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
