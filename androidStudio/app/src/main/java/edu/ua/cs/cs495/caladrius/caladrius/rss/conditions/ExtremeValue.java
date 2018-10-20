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
}
