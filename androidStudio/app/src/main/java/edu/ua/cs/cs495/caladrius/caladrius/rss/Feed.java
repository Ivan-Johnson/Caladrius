package edu.ua.cs.cs495.caladrius.caladrius.rss;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.Condition;
import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.ExtremeValue;

public class Feed implements Serializable {
    String name;
    String url;
    ArrayList<Condition> conditions;
    Context cntxt;

    public Feed(Context cntxt, String name, String url, ArrayList<Condition> conditions)
    {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }

        this.conditions = conditions;
        this.url = url;
        this.name = name;
        this.cntxt = cntxt;
    }

    public Feed(Context cntxt, String name, String url, Condition[] conditions)
    {
        this(
                cntxt,
                name,
                url,
                new ArrayList<>(Arrays.asList(conditions))
        );
    }

    public Feed(Context cntxt, String name, String url)
    {
        this(cntxt, name, url, new ArrayList<Condition>());

        // TODO: remove these
        Condition c = new ExtremeValue<>(this.cntxt,"BPM",0, ExtremeValue.extremeType.lessThan);
        this.conditions.add(0,c);
        c = new ExtremeValue<>(this.cntxt,"Stepcount",100, ExtremeValue.extremeType.equal);
        this.conditions.add(0,c);
        c = new ExtremeValue<>(this.cntxt,"Weight",80, ExtremeValue.extremeType.greaterThanOrEqual);
        this.conditions.add(0, c);
    }
}
