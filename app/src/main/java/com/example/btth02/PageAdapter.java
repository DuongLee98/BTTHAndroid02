package com.example.btth02;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter
{
    public int numTab;
    public AuthorPage ap;
    public BookPage bp;
    public SearchingPage sp;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTab = behavior;
        this.ap = new AuthorPage();
        this.bp = new BookPage();
        this.sp = new SearchingPage();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return this.ap;
            case 1:
                return this.bp;
            case 2:
                return this.sp;
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.numTab;
    }

    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Author";
            case 1:
                return "Book";
            case 2:
                return "Searching";
        }
        return null;
    }
}
