package edu.ua.cs.cs495.caladrius.rss.condition;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A condition stores the situations underwhich an RSS feed should have items added to it.
 */
public interface Condition extends Serializable
{
	/**
	 * @param data a json array where each element is a single day of the stat specified by getStat. Each element
	 * 	has two values; "value" and "dateTime"
	 * @return an arraylist of strings to be added to the RSS feed
	 */
	ArrayList<String> getMatches(JSONArray data);

	/**
	 * @return the name of the stat whose data should be passed to getMatches
	 */
	String getStat();
}
