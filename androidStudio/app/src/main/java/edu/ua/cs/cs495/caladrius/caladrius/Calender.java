package edu.ua.cs.cs495.caladrius.caladrius;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Calender extends AppCompatActivity{

    private static final String TAG = "CalenderActivity";

    private CalendarView mCalendarView;

    private String mItem_1 = "Item_1";
    private String mItem_2 = "Item_2";


//    /** EditText field to pick item to show on the graph */
    private Spinner mItemSpinner_1;
    private Spinner mItemSpinner_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);
        mCalendarView = findViewById(R.id.calendarView);
        final TextView startDateTextView = findViewById(R.id.start_date);
        final TextView endDateTextView = findViewById(R.id.end_date);

        final RadioGroup dateTypeRadioGroup = findViewById(R.id.select_type);


        String date = getBackupFolderName();
        startDateTextView.setText(String.format("%sth%s", date.substring(0, date.length() - 5), date.substring(date.length() - 5, date.length())));
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int i1, int i2) {
                Integer month = i1 + 1;
                String day;
                if (i2 < 10){
                    day = " " + String.valueOf(i2);
                }
                else{
                    day = String.valueOf(i2);
                }
                String date =  getMonthForInt(month)+ " " + day + "th " + year;
//                Log.d(TAG, "onSelectedDayChange: date" + date);
//                Toast.makeText(mCalendarView.getContext(), "You pressed on : " + date, Toast.LENGTH_SHORT).show();
//                switch (dateTypeRadioButton.getCheckedRadioButtonId()) {
//                    case R.id.R1:
//                        regAuxiliar = ultimoRegistro;
//                    case R.id.R2:
//                        regAuxiliar = objRegistro;
//                    default:
//                        regAuxiliar = null; // none selected
//                }

                if(dateTypeRadioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = dateTypeRadioGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String dateType = selectedRadioButton.getText().toString();
                    Toast.makeText(getApplicationContext(), dateType+" is selected", Toast.LENGTH_SHORT).show();
                    if (dateType.equals(getString(R.string.single_day))){
                        startDateTextView.setText(date);
                    }
                    else if (dateType.equals(getString(R.string.several_days))){
                        endDateTextView.setText(date);
                    }
                }

            }
        });


        mItemSpinner_1 = findViewById(R.id.spinner_item_1);
        mItemSpinner_2 = findViewById(R.id.spinner_item_2);

//        mItemSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }


    public static String getBackupFolderName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);
    }

    static String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month.substring(0,3);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the item to show on the graph.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter itemSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_item_options, R.layout.spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        itemSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        mItemSpinner_1.setAdapter(itemSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mItemSpinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            public void onNothingSelected(AdapterView<?> parent) {
                mItem_1 = "Error";
            }
        });


        // Apply the adapter to the spinner
        mItemSpinner_2.setAdapter(itemSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mItemSpinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            public void onNothingSelected(AdapterView<?> parent) {
                mItem_2 = "Error";
            }
        });


    }

}
