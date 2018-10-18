package edu.ua.cs.cs495.caladrius.caladrius;

import java.util.ArrayList;
import java.util.Arrays;

public class RSSFeed {
    String url;
    ArrayList<RssCondition> conditions;

    public RSSFeed(String url, ArrayList<RssCondition> conditions)
    {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }

        this.conditions = conditions;
        this.url = url;
    }

    public RSSFeed(String url, RssCondition[] conditions)
    {
        this(
                url,
                new ArrayList<>(Arrays.asList(conditions))
        );
    }

    public RSSFeed(String url)
    {
        this(url, new ArrayList<RssCondition>());
    }
}
