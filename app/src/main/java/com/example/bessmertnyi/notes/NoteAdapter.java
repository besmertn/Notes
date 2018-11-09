package com.example.bessmertnyi.notes;

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
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> values = new ArrayList<>();

    NoteAdapter(OnNoteClickListener listener, NoteDao noteDao) {

        this.noteDao = noteDao;
        this.listener = listener;
    }

    private OnNoteClickListener listener;
    private NoteDao noteDao;


    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mainTextView;
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
        }

        void bind(Note note) {
            mainTextView.setText(note.getShortText());
            dateTimeTextView.setText(note.getDateTime());
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

    public Note getNote(int position) {
        return values.get(position);
    }

    public List<Note> getNotes() {
        return values;
    }

    public void setItems(Collection<Note> notes) {
        values.addAll(notes);
        notifyDataSetChanged();
    }

    public void setItems(final Note note) {
        values.add(note);
        Callable<Void> clb = new Callable<Void>() {
            @Override
            public Void call() {
                noteDao.insert(note);
                return null;
            }
        };
        //Completable.fromCallable(clb).subscribe();
        Completable.fromCallable(clb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        notifyDataSetChanged();
    }
    public void deleteItem(Note note) {
        values.remove(note);
        noteDao.delete(note);
        notifyDataSetChanged();
    }
    public void clearItems() {
        values.clear();
        notifyDataSetChanged();
    }

}
