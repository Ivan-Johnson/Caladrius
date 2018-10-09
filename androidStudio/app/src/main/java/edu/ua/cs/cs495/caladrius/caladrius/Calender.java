package edu.ua.cs.cs495.caladrius.caladrius;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;


public class Calender extends AppCompatActivity{

    private static final String TAG = "CalenderActivity";

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);
        mCalendarView = findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int i1, int i2) {
                Integer month = i1 + 1;
                String date = year + "/" + month + "/" + i2;
//                Log.d(TAG, "onSelectedDayChange: date" + date);
                Toast.makeText(mCalendarView.getContext(), "You pressed on : " + date, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
