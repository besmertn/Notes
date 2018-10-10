package com.example.bessmertnyi.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> values = new ArrayList<>();

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView mainTextView;
        private ImageView mainImageView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mainTextView = itemView.findViewById(R.id.mainTextView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
        }

        public void bind(Note note) {
            mainTextView.setText(note.getMainText());
            mainImageView.setImageDrawable(note.getImage());
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(values.get(position));

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void setItems(Collection<Note> notes) {
        values.addAll(notes);
        notifyDataSetChanged();
    }

    public void vlearItems() {
        values.clear();
        notifyDataSetChanged();
    }
}
