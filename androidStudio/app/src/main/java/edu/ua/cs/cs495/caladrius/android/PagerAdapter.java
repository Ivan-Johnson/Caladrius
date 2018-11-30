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
	protected SummaryPage sp = new SummaryPage();
	protected FeedList fl = new FeedList();

	public PagerAdapter(Context context, FragmentManager fm)
	{
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position)
	{
		switch(position) {
			case 0:
				return sp;
			case 1:
				return fl;
			default:
				throw new IllegalArgumentException();
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
