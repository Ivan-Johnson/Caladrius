package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class MainFragment extends Fragment {
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
        series.setColor(Color.GREEN);
        graph.addSeries(series);

        GraphView graph2 = rootView.findViewById(R.id.graph2);
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series2.setColor(Color.CYAN);
        graph2.addSeries(series2);

        GraphView graph3 = rootView.findViewById(R.id.graph3);
        PointsGraphSeries<DataPoint> series3 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -2),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series3.setColor(Color.RED);
        graph3.addSeries(series3);

        GraphView graph4 = rootView.findViewById(R.id.graph4);
        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 1),
                new DataPoint(2, 2),
                new DataPoint(3, 3),
                new DataPoint(4, 4)
        });
        graph4.addSeries(series4);

        GraphView graph5 = rootView.findViewById(R.id.graph5);
        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(2, 2),
                new DataPoint(2, 2),
                new DataPoint(4, 4),
                new DataPoint(5, 5),
                new DataPoint(6, 6)
        });
        graph5.addSeries(series5);
        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 5),
                new DataPoint(2, 4),
                new DataPoint(3, 3),
                new DataPoint(4, 2),
                new DataPoint(5, 1)
        });
        series6.setColor(Color.GREEN);
        graph5.addSeries(series6);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // start GraphActivity
        View graph = getView().findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
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
