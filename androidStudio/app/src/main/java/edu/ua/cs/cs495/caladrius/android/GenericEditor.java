package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class GenericEditor extends AppCompatActivity
{
	protected final boolean alwaysSave;
	protected final String title;
	protected abstract Fragment makeFragment();

	/**
	 * @param alwaysSave when true, save automatically without prompting the user; regardless of what shouldConfirmCancel returns
	 */
	protected GenericEditor(String title, boolean alwaysSave)
	{
		this.title = title;
		this.alwaysSave = alwaysSave;
	}

	@Override
	public void onBackPressed()
	{
		onCancelClick();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);

		Toolbar tb = findViewById(R.id.editor_toolbar);
		tb.setTitle(title);
		if (alwaysSave) {
			tb.setNavigationIcon(R.drawable.back_button);
		} else {
			tb.setNavigationIcon(R.drawable.ic_baseline_cancel_24px);
		}
		//NOTE: gotta set the support action bar BEFORE setting the navigation on click listener
		setSupportActionBar(tb);
		tb.setNavigationOnClickListener((View v) -> {
			onCancelClick();
		});

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.editor_container);

		if (fragment == null) {
			fragment = makeFragment();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.editor_container, fragment);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (alwaysSave) {
			return false;
		} else {
			getMenuInflater().inflate(R.menu.editor_menu, menu);
			return true;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case R.id.editor_save:
			// TODO support failed saves
			save();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onCancel()
	{
		// NOP; subclass might want to do something though.
	}

	protected abstract void doSave();

	/**
	 * Determines whether or not the user will be asked for confirmation before navigating away without saving.
	 *
	 * @return false if the user should be forced to confirm their intentions before navigating away
	 */
	protected boolean shouldConfirmCancel()
	{
		return true;
	}

	private void cancel()
	{
		onCancel();
		finish();
	}

	private void save()
	{
		doSave();
		finish();
	}

	protected CharSequence getConfirmationTitle(Context cntxt)
	{
		return cntxt.getText(R.string.editor_confirmation_defaulttitle);
	}

	protected CharSequence getConfirmationMessage(Context cntxt)
	{
		return cntxt.getText(R.string.editor_confirmation_defaultmessage);
	}

	private void onCancelClick()
	{
		if (alwaysSave) {
			save();
			return;
		}

		if (!shouldConfirmCancel()) {
			cancel();
		}
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setIcon(android.R.drawable.ic_dialog_alert);
		adb.setTitle(getConfirmationTitle(this));
		adb.setMessage(getConfirmationMessage(this));
		adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				cancel();
			}
		});
		adb.setNegativeButton(android.R.string.no, null);
		adb.show();
	}
}
