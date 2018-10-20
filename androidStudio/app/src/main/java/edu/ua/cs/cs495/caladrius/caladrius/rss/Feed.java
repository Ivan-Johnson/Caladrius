package edu.ua.cs.cs495.caladrius.caladrius.rss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.Condition;
import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.ExtremeValue;

public class Feed implements Serializable {
    String name;
    String url;
    ArrayList<Condition> conditions;

    public Feed(String name, String url, ArrayList<Condition> conditions)
    {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }

        this.conditions = conditions;
        this.url = url;
        this.name = name;
    }

    public Feed(String name, String url, Condition[] conditions)
    {
        this(
                name,
                url,
                new ArrayList<>(Arrays.asList(conditions))
        );
    }

    public Feed(String name, String url)
    {
        this(name, url, new ArrayList<Condition>());

        // TODO: remove these
        Condition c = new ExtremeValue<>("BPM",0, ExtremeValue.extremeType.lessThan);
        this.conditions.add(0,c);
        c = new ExtremeValue<>("Stepcount",100, ExtremeValue.extremeType.equal);
        this.conditions.add(0,c);
        c = new ExtremeValue<>("Weight",80, ExtremeValue.extremeType.greaterThanOrEqual);
        this.conditions.add(0, c);
    }
}
