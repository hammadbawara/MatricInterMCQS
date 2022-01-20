package com.hz_apps.matricintermcqs.home.SelectBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {

    private final Context context;
    private final String[] names;
    private final int[] images;
    private final SubjectViewOnClick listener;

    public RecyclerAdapter(Context context, String[] names, int[] images, SubjectViewOnClick listener) {
        this.context = context;
        this.names = names;
        this.images = images;
        this.listener = listener;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.subject_name.setText(names[position]);
        holder.subject_image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView subject_name;
        ImageView subject_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            subject_name = itemView.findViewById(R.id.subject_name);
            subject_image = itemView.findViewById(R.id.subject_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }
    interface SubjectViewOnClick{
        void onClick(View v, int position);
    }
}
