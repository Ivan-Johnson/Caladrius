package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainFragment extends Fragment {


    ImageView graph1;


    public MainFragment() {
        // Empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.graph_list, container, false);

        GraphView graph = rootView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        // get the button view
        graph1 = getView().findViewById(R.id.g1);
        // set a onclick listener for when the button gets clicked
        graph1.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(),
                        GraphActivity.class);
                startActivity(mainIntent);
            }
        });


        final Button viewAllDataButton = getView().findViewById(R.id.viewAllData);
        viewAllDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent (v.getContext(), AllData.class);
                startActivityForResult(nextScreen, 0);

            }
        });
    }


}
