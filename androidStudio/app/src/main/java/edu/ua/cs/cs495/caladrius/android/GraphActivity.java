package edu.ua.cs.cs495.caladrius.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GraphActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        String startDate = intent.getExtras().getString("startDate");
        String endDate = intent.getExtras().getString("endDate");
        String item_1 = intent.getExtras().getString("item_1");
        String item_2 = intent.getExtras().getString("item_2");

        final TextView queryInfoTextView = findViewById(R.id.queryInfo);
        queryInfoTextView.setText(
                  "QueryInfo:" +
                "\nStart date: "+startDate+
                "\nEnd date  : "+endDate+
                "\nItem_1     : "+item_1+
                "\nItem_2     : "+item_2);

    }
}
