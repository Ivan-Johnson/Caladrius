package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment {
    public MainFragment() {
        // Empty public constructor
    }

    public final FitbitGraphView.GraphViewGraph[][] defaultGraphTypes = {
            {FitbitGraphView.GraphViewGraph.BarGraph},
            {FitbitGraphView.GraphViewGraph.PointsGraph},
            {FitbitGraphView.GraphViewGraph.PointsGraph},
            {FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph},
    };

    public final String[][] defaultGraphStats = {
            {"Heartrate"},
            {"CaloricBurn"},
            {"CaloASDF"},
            {"BPM", "asdf"},
    };

    public final Integer[][] defaultGraphColors = {
            {Color.CYAN},
            {Color.RED},
            {Color.BLUE},
            {Color.GREEN, Color.GRAY},
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.graph_list, container, false);

        LinearLayout ll = rootView.findViewById(R.id.list);
        assert defaultGraphStats.length == defaultGraphTypes.length;
        for (int c = defaultGraphStats.length - 1; c >= 0; c--) {
            ArrayList<FitbitGraphView.GraphViewGraph> graphTypes = new ArrayList<>();
            graphTypes.addAll(Arrays.asList(defaultGraphTypes[c]));

            ArrayList<String> stats = new ArrayList<>();
            stats.addAll(Arrays.asList(defaultGraphStats[c]));

            ArrayList<Integer> color = new ArrayList<>();
            color.addAll(Arrays.asList(defaultGraphColors[c]));

            FitbitGraphView fgv = new FitbitGraphView(getContext(),
                    graphTypes,
                    stats,
                    color,
                    false,
                    false,
                    false,
                    false
            );

            ll.addView(fgv, 0);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
