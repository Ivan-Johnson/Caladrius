package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import java.io.Serializable;

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
}
