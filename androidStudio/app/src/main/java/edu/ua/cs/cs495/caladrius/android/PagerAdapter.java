package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.ua.cs.cs495.caladrius.android.rss.FeedList;

public class PagerAdapter extends FragmentPagerAdapter
{
	final int PAGE_COUNT = 2;
	protected Context mContext;

	public PagerAdapter(Context context, FragmentManager fm)
	{
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position)
	{
		if (position == 0) {
			return new SummaryPage();
		} else {
			return new FeedList();
		}
	}

	@Override
	public int getCount()
	{
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		if (position == 0) {
			return mContext.getString(R.string.category_home);
		} else {
			return mContext.getString(R.string.category_rss);
		}
	}
}
