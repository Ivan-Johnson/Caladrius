package edu.ua.cs.cs495.caladrius.rss.condition;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public interface Condition extends Serializable
{
	public ArrayList<String> getMatches(JSONArray data);

	public String getStat();
}
