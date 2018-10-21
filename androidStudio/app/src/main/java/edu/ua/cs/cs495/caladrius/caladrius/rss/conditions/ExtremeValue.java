package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;

import edu.ua.cs.cs495.caladrius.caladrius.Caladrius;
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
        sb.append(' ');
        int tmp;
        switch (type) {
            case equal:
                tmp = R.string.cmp_eq;
                break;
            case lessThan:
                tmp = R.string.cmp_lt;
                break;
            case greaterThan:
                tmp = R.string.cmp_gt;
                break;
            case lessThanOrEqual:
                tmp = R.string.cmp_lte;
                break;
            case greaterThanOrEqual:
                tmp = R.string.cmp_gte;
                break;
            default:
                throw new RuntimeException("Type \"" + type + "\" was not a valid extremeType as of the writing of this message");
        }
        Context cntxt = Caladrius.getContext();
        sb.append(cntxt.getText(tmp));
        sb.append(' ');
        sb.append(cntxt.getText(R.string.ev_singleValue));

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

            Spinner sp = rootView.findViewById(R.id.ev_type);
            sp.setAdapter(
                    ArrayAdapter.createFromResource(
                            getContext(),
                            R.array.rss_conditions_extremevalue_boundarytype,
                            R.layout.spinner_item
                    )
            );

            return rootView;
        }
    }

    @Override
    public DialogFragment makeEditor() {
        return ExtremeValueEditor.newInstance(this);
    }
}
