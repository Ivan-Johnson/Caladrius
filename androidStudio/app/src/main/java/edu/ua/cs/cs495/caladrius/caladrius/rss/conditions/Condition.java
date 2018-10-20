package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.support.v4.app.DialogFragment;

import java.io.Serializable;

public abstract class Condition implements Serializable
{
    @Override
    public abstract String toString();

    public abstract DialogFragment makeEditor();
}
