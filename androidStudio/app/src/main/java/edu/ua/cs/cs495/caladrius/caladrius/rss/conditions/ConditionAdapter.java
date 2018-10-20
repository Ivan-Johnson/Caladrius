package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.ua.cs.cs495.caladrius.caladrius.R;


public class ConditionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context c;
    private ArrayList<Condition> conditions;
    private final FragmentManager fm;
    private int cvid;

    public ConditionAdapter(Context c, ArrayList<Condition> conditions, FragmentManager fm, int cvid) {
        this.c = c;
        this.conditions = conditions;
        this.fm = fm;
        this.cvid = cvid;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return conditions.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.rss_feed_item, null);
        }
        TextView text = view.findViewById(R.id.name);
        text.setText(conditions.get(i).toString());

        view.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                final Dialog fbDialogue = new Dialog(c, android.R.style.Theme_DeviceDefault);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.rss_feed_edit);
                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });

        return view;
    }

    @Override
    public Object getItem(int i) {
        return conditions.get(i);
    }
}
