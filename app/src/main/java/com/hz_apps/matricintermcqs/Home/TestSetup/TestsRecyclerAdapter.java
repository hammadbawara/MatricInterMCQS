package com.hz_apps.matricintermcqs.Home.TestSetup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;

public class TestsRecyclerAdapter extends RecyclerView.Adapter<TestsRecyclerAdapter.myViewHolder> {

    private final Context context;
    private final  Test[] tests;
    private final SetClickListenerOnTest listener;

    public TestsRecyclerAdapter(Context context, Test[] tests, SetClickListenerOnTest listener) {
        this.context = context;
        this.tests = tests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Test test = tests[position];
        holder.TestNameView.setText(test.getTitle());
        holder.numberOfMCQsView.setText(String.valueOf(test.getNumberOfQuestions()));
    }

    @Override
    public int getItemCount() {
        return tests.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TestNameView, numberOfMCQsView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            TestNameView = itemView.findViewById(R.id.TestNameTView);
            numberOfMCQsView = itemView.findViewById(R.id.numberOfMCQsInTest);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }
    }

    public interface SetClickListenerOnTest {
        void onClick(int position);
    }
}
