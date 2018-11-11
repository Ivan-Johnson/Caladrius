package edu.ua.cs.cs495.caladrius.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class QueryEditor extends AppCompatActivity
{

	private static final String TAG = "CalenderActivity";

	private CalendarView mCalendarView;

	private String mStartDate = "N/A";
	private String mEndDate = "N/A";

	private String mItem_1 = "N/A";
	private String mItem_2 = "N/A";

	//    /** EditText field to pick item to show on the graph */
	private Spinner mItemSpinner_1;
	private Spinner mItemSpinner_2;

	static String getMonthForInt(int m)
	{
		List<String> monthStr = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		return monthStr.get(m);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_editor);
		mCalendarView = findViewById(R.id.calendarView);

		Toolbar myToolbar = findViewById(R.id.query_editor_toolbar);
		setSupportActionBar(myToolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Query data");

		if(getSupportActionBar() != null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		}

		final TextView startDateTextView = findViewById(R.id.start_date);
		final TextView endDateTextView = findViewById(R.id.end_date);

		final RadioGroup dateTypeRadioGroup = findViewById(R.id.select_type);

		final Button submitBut = findViewById(R.id.submitCalender);

		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		String date = getMonthForInt(month) + " " + day + " " + year;
		mStartDate = date;
		startDateTextView.setText(String.format("%sth%s",
				date.substring(0, date.length() - 5),
				date.substring(date.length() - 5, date.length())));

		mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
		{
			@Override
			public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int i1, int i2)
			{
				Integer month = i1;
				String day;
				if (i2 < 10) {
					day = " " + String.valueOf(i2);
				} else {
					day = String.valueOf(i2);
				}
				String date = getMonthForInt(month) + " " + day + " " + year;
				String dateShow = getMonthForInt(month) + " " + day + "th " + year;

				if (dateTypeRadioGroup.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getApplicationContext(), "Please select dateType",
							Toast.LENGTH_SHORT)
							.show();
				} else {
					// get selected radio button from radioGroup
					int selectedId = dateTypeRadioGroup.getCheckedRadioButtonId();

					// find the radiobutton by returned id
					RadioButton selectedRadioButton = findViewById(selectedId);
					String dateType = selectedRadioButton.getText()
							.toString();

					Toast.makeText(getApplicationContext(), dateType + " is selected",
							Toast.LENGTH_SHORT)
							.show();

					if (dateType.equals(getString(R.string.single_day))) {
						startDateTextView.setText(dateShow);
						mStartDate = date;
					} else if (dateType.equals(getString(R.string.several_days))) {
						endDateTextView.setText(dateShow);
						mEndDate = date;
					}
				}
			}
		});


		mItemSpinner_1 = findViewById(R.id.spinner_item_1);
		mItemSpinner_2 = findViewById(R.id.spinner_item_2);
		setupSpinner();

		submitBut.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent submitPage = new Intent(v.getContext(), QueryActivity.class);
				submitPage.putExtra("startDate", mStartDate);
				submitPage.putExtra("endDate", mEndDate);
				submitPage.putExtra("item_1", mItem_1);
				submitPage.putExtra("item_2", mItem_2);
				startActivityForResult(submitPage, 0);
			}
		});
	}

	/**
	 * Setup the dropdown spinner that allows the user to select the item to show on the graph.
	 */
	private void setupSpinner()
	{
		// Create adapter for spinner. The list options are from the String array it will use
		// the spinner will use the default layout
		ArrayAdapter itemSpinnerAdapter = ArrayAdapter.createFromResource(this,
				R.array.array_item_options, R.layout.spinner_item);

		// Specify dropdown layout style - simple list view with 1 item per line
		itemSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

		// Apply the adapter to the spinner
		mItemSpinner_1.setAdapter(itemSpinnerAdapter);

		// Set the integer mSelected to the constant values
		mItemSpinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				String selection = (String) parent.getItemAtPosition(position);
				if (!TextUtils.isEmpty(selection)) {
					if (selection.equals(getString(R.string.item_bpm))) {
						mItem_1 = getResources().getString(R.string.item_bpm);
					} else if (selection.equals(getString(R.string.item_steps))) {
						mItem_1 = getResources().getString(R.string.item_steps);
					} else {
						mItem_1 = "Error";
					}
				}
			}

			// Because AdapterView is an abstract class, onNothingSelected must be defined
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				mItem_1 = "Error";
			}
		});

		// Apply the adapter to the spinner
		mItemSpinner_2.setAdapter(itemSpinnerAdapter);

		// Set the integer mSelected to the constant values
		mItemSpinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				String selection = (String) parent.getItemAtPosition(position);
				if (!TextUtils.isEmpty(selection)) {
					if (selection.equals(getString(R.string.item_bpm))) {
						mItem_2 = getResources().getString(R.string.item_bpm);
					} else if (selection.equals(getString(R.string.item_steps))) {
						mItem_2 = getResources().getString(R.string.item_steps);
					} else {
						mItem_2 = "Error";
					}
				}
			}

			// Because AdapterView is an abstract class, onNothingSelected must be defined
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				mItem_2 = "Error";
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}