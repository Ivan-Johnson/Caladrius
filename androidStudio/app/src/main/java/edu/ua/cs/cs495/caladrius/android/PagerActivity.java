package edu.ua.cs.cs495.caladrius.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * PagerActivity is the home page for the app, it have toolbar with calender page and
 * edit page, also have two page fragment switch by using sliding tabs, one is summary
 * page, another is rss page.
 *
 * @author Hansheng Li
 */
public class PagerActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_activity);
		// Find the view pager that will allow the user to swipe between fragments
		ViewPager viewPager = findViewById(R.id.viewpager);

		PagerAdapter adapter =
			new PagerAdapter(this, getSupportFragmentManager());

		// Set the adapter onto the view pager
		viewPager.setAdapter(adapter);

		// Give the TabLayout the ViewPager
		TabLayout tabLayout = findViewById(R.id.sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);
	}



}
