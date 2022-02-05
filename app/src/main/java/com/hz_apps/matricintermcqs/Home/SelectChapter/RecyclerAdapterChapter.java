package com.hz_apps.matricintermcqs.Home.SelectChapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;

import java.util.List;

public class RecyclerAdapterChapter extends RecyclerView.Adapter<RecyclerAdapterChapter.myViewHolder> {
    private final Context context;
    private final List<BookChapter> chapterList;
    private final ChapterViewOnClick listener;
    private final boolean CheckBoxSelected;

    public RecyclerAdapterChapter(Context context, List<BookChapter> chapterList, ChapterViewOnClick listener, boolean CheckBoxSelected) {
        this.context = context;
        this.chapterList = chapterList;
        this.listener = listener;
        this.CheckBoxSelected = CheckBoxSelected;
    }

    @NonNull
    @Override
    public RecyclerAdapterChapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterChapter.myViewHolder holder, int position) {
        if (CheckBoxSelected){
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.chapter_number.setVisibility(View.INVISIBLE);
        }
        BookChapter chapter = chapterList.get(position);
        holder.chapter_name_TVi.setText(chapter.getChapterName());
        holder.chapter_number.setText(chapter.getChapterNo() + ".");
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {

        TextView chapter_name_TVi, chapter_number;
        CheckBox checkBox;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_name_TVi = itemView.findViewById(R.id.chapter_name_TVi);
            chapter_number = itemView.findViewById(R.id.chapter_number);
            checkBox = itemView.findViewById(R.id.checkBox_select_chapter);
            checkBox.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition(), chapterList.get(getAdapterPosition()).getChapterNo());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            listener.CheckChangeListener(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.longClick(getAdapterPosition());
            return true;
        }
    }
    interface ChapterViewOnClick{
        void onClick(View view, int position, int chapter);
        void CheckChangeListener(int position);
        void longClick(int position);
    }
}
