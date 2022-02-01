package com.hz_apps.matricintermcqs.saved.SavedTest;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.SavedTest;
import com.hz_apps.matricintermcqs.Database.UserDatabase;
import com.hz_apps.matricintermcqs.R;

import java.util.List;

public class SavedTestRecyclerAdapter extends RecyclerView.Adapter<SavedTestRecyclerAdapter.myViewHolder> {

    private final Context context;
    private final List<SavedTest> savedTestList;
    private final SavedTestClickListener listener;
    private final SavedTestLongClickListener LongClickListener;

    public SavedTestRecyclerAdapter(Context context, List<SavedTest> savedTestList, SavedTestClickListener listener,
                                    SavedTestLongClickListener LongClickListener) {
        this.context = context;
        this.savedTestList = savedTestList;
        this.listener = listener;
        this.LongClickListener = LongClickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_test_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        SavedTest savedTest = savedTestList.get(position);
        holder.saved_test_title.setText(savedTest.getTestTitle());
    }

    @Override
    public int getItemCount() {
        return savedTestList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView saved_test_title;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            saved_test_title = itemView.findViewById(R.id.saved_test_title_TView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            LongClickListener.onLongClick(getAdapterPosition());
            return false;
        }
    }

    public interface SavedTestClickListener{
        void onClick(int position);
    }

    public interface SavedTestLongClickListener{
        void onLongClick(int position);
    }
}
