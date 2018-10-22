package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class FitbitGraphViewAdapter extends BaseAdapter {
    FitbitGraphView fgv[];

    public FitbitGraphViewAdapter(Context cntxt, GraphViewGraph[][] graphTypes, String[][] defaultGraphStats, Integer[][] defaultGraphColors) {
        // TODO change the constructor to take fgv configs as an argument instead of the three 2D arrays
        int len = graphTypes.length;
        if (len != defaultGraphStats.length ||
                len != defaultGraphColors.length) {
            throw new IllegalArgumentException("arrays must all be the same length");
        }

        fgv = new FitbitGraphView[len];
        for (int c = 0; c < len; c++) {
            ArrayList<GraphViewGraph> types = new ArrayList<>();
            types.addAll(Arrays.asList(graphTypes[c]));

            ArrayList<String> stats = new ArrayList<>();
            stats.addAll(Arrays.asList(defaultGraphStats[c]));

            ArrayList<Integer> color = new ArrayList<>();
            color.addAll(Arrays.asList(defaultGraphColors[c]));

            fgv[c] = new FitbitGraphView(
                    cntxt, types, stats, color,
                    false, false, false, false);
        }
    }

    @Override
    public int getCount() {
        return fgv.length;
    }

    @Override
    public Object getItem(int i) {
        return fgv[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return fgv[i];
    }
}
