package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

public abstract class Condition implements Serializable
{
	@Override
	public abstract String toString();

	public abstract Intent makeEditorIntent(Context cntxt);
}
