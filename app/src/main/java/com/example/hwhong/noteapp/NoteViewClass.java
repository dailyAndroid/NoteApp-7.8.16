package com.example.hwhong.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hwhong on 8/6/16.
 */
public class NoteViewClass extends AppCompatActivity {

    private TextView title, message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view_layout);

        title = (TextView) findViewById(R.id.titleViewTextView);
        message = (TextView) findViewById(R.id.messageViewTextView);

        Intent intent = getIntent();

        String title_intent = intent.getStringExtra("title");
        String message_intent = intent.getStringExtra("message");

        title.setText(title_intent);
        message.setText(message_intent);
    }
}
