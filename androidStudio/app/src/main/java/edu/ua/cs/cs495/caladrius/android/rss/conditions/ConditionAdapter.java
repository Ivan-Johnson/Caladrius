package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;

import java.util.ArrayList;


public class ConditionAdapter extends BaseAdapter
{
	private static final String OUR_TAG = "FeedAdapter";
	protected ClickHandler ch;
	private LayoutInflater inflater;
	private ArrayList<Condition> conditions;
	public ConditionAdapter(ArrayList<Condition> conditions, LayoutInflater inflater, ClickHandler ch)
	{
		this.conditions = conditions;
		this.ch = ch;
		this.inflater = inflater;
	}

	public ConditionAdapter(ArrayList<Condition> conditions, Context c, ClickHandler ch)
	{
		this(conditions, (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE), ch);
	}

	@Override
	public int getCount()
	{
		return conditions.size();
	}

	@Override
	public Object getItem(int i)
	{
		return conditions.get(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup)
	{
		if (view == null) {
			view = inflater.inflate(R.layout.rss_feed_item, null);
		}
		TextView text = view.findViewById(R.id.name);
		text.setText(ConditionDescriber.describe(conditions.get(i)));

		view.setOnClickListener((View v) -> ch.onClick(i, conditions.get(i)));

		return view;
	}

	public void setItem(int i, Condition cond)
	{
		conditions.set(i, cond);
		this.notifyDataSetInvalidated();
	}

	public void removeItem(int i)
	{
		conditions.remove(i);
		notifyDataSetInvalidated();
	}

	public void addItem(Condition cond)
	{
		conditions.add(cond);
		notifyDataSetInvalidated();
	}

	public ArrayList<Condition> getConditions()
	{
		return conditions;
	}

	public interface ClickHandler
	{
		void onClick(int i, Condition cond);
	}
}
