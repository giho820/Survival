package com.survivalsos.goldentime.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.survivalsos.goldentime.fragment.MainFirstFrag;
import com.survivalsos.goldentime.fragment.MainSecondFrag;

/**
 * Created by kiho on 2016. 6. 21..
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"재난 대처", "서바이벌"};

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
            return new MainSecondFrag();
            default:
                return new MainFirstFrag();

        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getItemPosition(Object object) {
        int position;

        if (object instanceof MainFirstFrag) {
            position = 0;
        } else {
            position = 1;
        }

        return position;
    }
}
