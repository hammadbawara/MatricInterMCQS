package com.hz_apps.matricintermcqs.home.HomeMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.FragmentHomeMainBinding;

public class BooksRecyclerView extends RecyclerView.Adapter<BooksRecyclerView.myViewHolder> {

    private final Context context;
    private final String[] names;
    private final int[] images;

    public BooksRecyclerView(Context context, String[] names, int[] images) {
        this.context = context;
        this.names = names;
        this.images = images;
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
        holder.itemView.setOnClickListener(view -> {
            NavDirections action = HomeMainFragmentDirections.actionChooseSubjectToFragmentSelectChapter(HomeMainFragment.SelectedClass, position+1);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView subject_name;
        ImageView subject_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            subject_name = itemView.findViewById(R.id.subject_name);
            subject_image = itemView.findViewById(R.id.subject_icon);
        }
    }
}
