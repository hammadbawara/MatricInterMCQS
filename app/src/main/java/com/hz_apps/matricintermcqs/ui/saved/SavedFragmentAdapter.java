package com.hz_apps.matricintermcqs.ui.saved;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SavedFragmentAdapter extends FragmentStateAdapter {
    public SavedFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new SavedTest();
            case 1:
                return new BookmarkedQuestions();
        }
        return new SavedTest();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
