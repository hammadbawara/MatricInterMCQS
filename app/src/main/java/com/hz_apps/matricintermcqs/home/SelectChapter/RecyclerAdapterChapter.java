package com.hz_apps.matricintermcqs.home.SelectChapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;

public class RecyclerAdapterChapter extends RecyclerView.Adapter<RecyclerAdapterChapter.myViewHolder> {
    private final Context context;
    private final String[] chapter_names;
    private final ChapterViewOnClick listener;

    public RecyclerAdapterChapter(Context context, String[] chapter_names, ChapterViewOnClick listener) {
        this.context = context;
        this.chapter_names = chapter_names;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapterChapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterChapter.myViewHolder holder, int position) {
            holder.chapter_name_TVi.setText(chapter_names[position]);
            holder.chapter_number.setText((position + 1) +".");
    }

    @Override
    public int getItemCount() {
        return chapter_names.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chapter_name_TVi, chapter_number;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            chapter_name_TVi = itemView.findViewById(R.id.chapter_name_TVi);
            chapter_number = itemView.findViewById(R.id.chapter_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }
    interface ChapterViewOnClick{
        void onClick(View view, int position);
    }
}
