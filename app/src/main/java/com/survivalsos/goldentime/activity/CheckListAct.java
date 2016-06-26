package com.survivalsos.goldentime.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ParentAct;
import com.astuetz.PagerSlidingTabStrip;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.TestBaseAdapter;
import com.survivalsos.goldentime.fragment.CheckListAllFrag;
import com.survivalsos.goldentime.util.DebugUtil;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CheckListAct extends ParentAct implements
        AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TestBaseAdapter mAdapter;
    private StickyListHeadersListView stickyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PagerSlidingTabStrip pagerTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_tab_strip);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        pagerTabStrip.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3));
        pagerTabStrip.setIndicatorColor(getResources().getColor(R.color.c_fff92e14));
        if (Definitions.LatoBlack != null)
            pagerTabStrip.setTypeface(Definitions.LatoBlack, Typeface.NORMAL);
        pagerTabStrip.setTextColorResource(R.color.text_color);
        pagerTabStrip.setShouldExpand(true);
        pagerTabStrip.setDividerColor(getResources().getColor(android.R.color.transparent));
        pagerTabStrip.setViewPager(mViewPager);

        mAdapter = new TestBaseAdapter(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
        Toast.makeText(this, "Header " + headerId + " currentlySticky ? " + currentlySticky, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DebugUtil.showToast(this, "Item " + position + " clicked!");
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        private int[] menuArray = {
                R.drawable.check_list_t_add,
                R.drawable.check_list_t_add,
                R.drawable.check_list_t_add
        };


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return CheckListAllFrag.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

        @Override
        public int getPageIconResId(int position) {
            int resid = 0;
            if (menuArray != null && menuArray.length > position)
                resid = menuArray[position];
            return resid;
        }
    }
}
