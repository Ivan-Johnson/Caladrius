package edu.ua.cs.cs495.caladrius.caladrius.rss;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.Condition;

public class Feed {
    String url;
    ArrayList<Condition> conditions;

    public Feed(String url, ArrayList<Condition> conditions)
    {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }

        this.conditions = conditions;
        this.url = url;
    }

    public Feed(String url, Condition[] conditions)
    {
        this(
                url,
                new ArrayList<>(Arrays.asList(conditions))
        );
    }

    public Feed(String url)
    {
        this(url, new ArrayList<Condition>());
    }
}
