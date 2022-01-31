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

    public SavedTestRecyclerAdapter(Context context, List<SavedTest> savedTestList, SavedTestClickListener listener) {
        this.context = context;
        this.savedTestList = savedTestList;
        this.listener = listener;
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

        holder.itemView.setOnLongClickListener(view -> {
            showUserDeleteDialog(savedTest);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return savedTestList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView saved_test_title;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            saved_test_title = itemView.findViewById(R.id.saved_test_title_TView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }
    }

    public interface SavedTestClickListener{
        void onClick(int position);
    }

    private void showUserDeleteDialog(SavedTest savedTest) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Do you really want to delete?");
        dialog.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        dialog.setPositiveButton("Delete", (dialogInterface, i) -> {
            UserDatabase userDatabase = new UserDatabase(context);
            userDatabase.deleteSavedTest(savedTest.getId(), savedTest.getTableName());
            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }).show();
    }
}
