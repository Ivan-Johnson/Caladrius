package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;

import edu.ua.cs.cs495.caladrius.caladrius.R;

public class ExtremeValue<T extends Comparable & Serializable> extends Condition {
    public enum extremeType {
        lessThan,
        lessThanOrEqual,
        equal,
        greaterThan,
        greaterThanOrEqual
    }

    protected String stat;
    protected T value;
    protected extremeType type;

    public ExtremeValue(String stat, T value, extremeType type) {
        this.stat = stat;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stat);
        switch (type) {
            case equal:
                sb.append(" = ");
                break;
            case lessThan:
                sb.append(" < ");
                break;
            case greaterThan:
                sb.append(" > ");
                break;
            case lessThanOrEqual:
                sb.append(" <= ");
                break;
            case greaterThanOrEqual:
                sb.append(" >= ");
                break;
            default:
                throw new RuntimeException("Type \"" + type + "\" was not a valid extremeType as of the writing of this message");
        }
        sb.append(" any single value");

        return sb.toString();
    }

    public static class ExtremeValueEditor extends DialogFragment
    {
        protected static final String ARG_EXTREMEVALUE = "ExtremeValueEditor EXTREMEVALUE";
        ExtremeValue ev;

        public static ExtremeValueEditor newInstance(ExtremeValue ev) {
            Bundle args = new Bundle();

            args.putSerializable(ARG_EXTREMEVALUE, ev);

            ExtremeValueEditor fragment = new ExtremeValueEditor();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle b = getArguments();
            this.ev = (ExtremeValue) b.getSerializable(ARG_EXTREMEVALUE);

            View rootView = inflater.inflate(R.layout.rss_condition_extremevalue_editor,
                    container, false);

            TextView tv = rootView.findViewById(R.id.ev_helloworld);
            tv.setText("This is the edit page for ExtremeValue conditions.\n" +
                    "Its hash is "+this.ev.hashCode());

            return rootView;
        }
    }

    @Override
    public DialogFragment makeEditor() {
        return ExtremeValueEditor.newInstance(this);
    }
}
