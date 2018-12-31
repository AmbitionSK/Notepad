package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private  List<Note> mNoteList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View NoteView;
        TextView title;
        TextView note;

        public ViewHolder(View view){
            super(view);
            NoteView = view;
            title = (TextView) view.findViewById(R.id.note_title);
            note = (TextView) view.findViewById(R.id.note_note);
        }
    }

    public NoteAdapter(List<Note> NoteList){
        mNoteList = NoteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        // 点击查看详情
        holder.NoteView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Note note = mNoteList.get(position);
                int id = note.getId();
                String title = note.getTitle();
                String Note = note.getNote();
                Intent intent = new Intent(v.getContext(),NoteView.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("note",Note);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Note note = mNoteList.get(position);
        holder.title.setText(note.getTitle());
        holder.note.setText(note.getNote());
    }

    @Override
    public int getItemCount(){
        return mNoteList.size();
    }

}
