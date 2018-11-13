package com.example.bessmertnyi.notes;

import android.content.Context;
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

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private List<AudioModel> values = new ArrayList<>();
    private List<AudioModel> valuesCopy = new ArrayList<>();

    AudioAdapter(OnNoteClickListener listener, Context context) {

        this.listener = listener;
        valuesCopy.addAll(values);
        this.context = context;
    }

    private OnNoteClickListener listener;
    private Context context;


    class AudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView audioNameTextView;
        private TextView audioStatusTextView;
        private TextView audioArtistTextView;
        private ImageView audioMainImageView;

        private OnNoteClickListener listener;

        AudioViewHolder(View itemView, OnNoteClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            audioNameTextView = itemView.findViewById(R.id.audioNameTextView);
            audioArtistTextView = itemView.findViewById(R.id.audioArtistTextView);
            audioMainImageView = itemView.findViewById(R.id.audioMainImageView);
            audioStatusTextView = itemView.findViewById(R.id.audioStatusTextView);
        }

        void bind(AudioModel item) {
            audioNameTextView.setText(item.getaName());
            audioArtistTextView.setText(item.getaArtist() + " | " + item.getaAlbum());
            //audioStatusTextView.setText(context.getResources().getStringArray(R.array.status_array)[item.getStatus()]);
            //byte[] imageBytes = item.getImage();
            //Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            //audioMainImageView.setImageBitmap(bmp);
            audioMainImageView.setImageDrawable(context.getDrawable(R.drawable.ic_music_note));
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_view, parent, false);
        return new AudioViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        holder.bind(values.get(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    AudioModel getAudio(int position) {
        return values.get(position);
    }

    public List<AudioModel> getNotes() {
        return valuesCopy;
    }

    void setAudioList(Collection<AudioModel> items) {
        valuesCopy.clear();
        values.clear();
        valuesCopy.addAll(items);
        values.addAll(items);
        notifyDataSetChanged();
    }

    void setItems(Collection<AudioModel> items) {
        valuesCopy.addAll(items);
        values.addAll(items);
        notifyDataSetChanged();
    }

    void setItem(final AudioModel item) {
        values.add(item);
        valuesCopy.add(item);

        notifyDataSetChanged();
    }

    void deleteItem(final AudioModel item) {
        values.remove(item);
        valuesCopy.remove(item);

        notifyDataSetChanged();
    }

    void filterAudios(final int status) {
        /*values.clear();
        if (status == -1) {
            values.addAll(valuesCopy);
        } else{
            for(Note note: valuesCopy){
                if (note.getStatus() == status) {
                    values.add(note);
                }
            }
        }*/
        notifyDataSetChanged();
    }

}
