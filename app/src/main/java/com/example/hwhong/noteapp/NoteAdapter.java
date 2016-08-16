package com.example.hwhong.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hwhong on 8/6/16.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    public static class ViewHolder {
        TextView titleTextView;
    }

    public NoteAdapter(Context context, ArrayList<Note> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            //save viewholder
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.titleRowTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTextView.setText(note.getTitle());

        return convertView;
    }


}