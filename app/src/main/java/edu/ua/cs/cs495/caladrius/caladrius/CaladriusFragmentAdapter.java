package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CaladriusFragmentAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    protected Context mContext;

    public CaladriusFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MainFragment();
        } else {
            return new RssFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_home);
        } else  {
            return mContext.getString(R.string.category_rss);
        }
    }
}
