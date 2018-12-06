package edu.ua.cs.cs495.caladrius.rss.condition;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;

public interface Condition extends Serializable
{
	public ArrayList<String> getMatches(JSONArray data);
	public String getStat();
}
