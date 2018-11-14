package com.example.bessmertnyi.notes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
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
    private List<Note> valuesCopy = new ArrayList<>();

    NoteAdapter(OnNoteClickListener listener, Context context, DatabaseHandler dbHandler) {
        this.listener = listener;
        valuesCopy.addAll(values);
        this.context = context;
        this.dbHandler = dbHandler;
    }

    private OnNoteClickListener listener;
    private Context context;
    private DatabaseHandler dbHandler;


    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mainTextView;
        private TextView statusTextVIew;
        private TextView dateTimeTextView;
        private ImageView mainImageView;

        private OnNoteClickListener listener;

        NoteViewHolder(View itemView, OnNoteClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            mainTextView = itemView.findViewById(R.id.mainTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
            statusTextVIew = itemView.findViewById(R.id.statusTextView);
        }

        void bind(Note note) {
            mainTextView.setText(note.getShortText());
            dateTimeTextView.setText(note.getDateTime());
            statusTextVIew.setText(context.getResources().getStringArray(R.array.status_array)[note.getStatus()]);
            byte[] imageBytes = note.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            mainImageView.setImageBitmap(bmp);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(values.get(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    Note getNote(int position) {
        return values.get(position);
    }

    public List<Note> getNotes() {
        return valuesCopy;
    }

    void clearAll() {
        valuesCopy.clear();
        values.clear();
        notifyDataSetChanged();
    }

    void setItems(Collection<Note> notes) {
        valuesCopy.addAll(notes);
        values.addAll(notes);
        notifyDataSetChanged();
    }

    void setItems(Note note) {
        values.add(note);
        valuesCopy.add(note);
        dbHandler.insertNote(note);
        notifyDataSetChanged();
    }

    void deleteItem(Note note) {
        values.remove(note);
        valuesCopy.remove(note);
        dbHandler.deleteNote(note);
        notifyDataSetChanged();
    }

    void filterNotes(final int status) {
        values.clear();
        if (status == -1) {
            values.addAll(valuesCopy);
        } else{
            for(Note note: valuesCopy){
                if (note.getStatus() == status) {
                    values.add(note);
                }
            }
        }
        notifyDataSetChanged();
    }

}
