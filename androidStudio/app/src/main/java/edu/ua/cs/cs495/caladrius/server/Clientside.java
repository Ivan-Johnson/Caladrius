package edu.ua.cs.cs495.caladrius.server;

import edu.ua.cs.cs495.caladrius.rss.condition.Condition;

import java.io.SyncFailedException;

public class Clientside
{
	// TODO
	protected static long msLastSyncTime = Long.MIN_VALUE;
	protected static Condition[] conditions;

	/**
	 * Asks the Caladrius server what time this user's conditions were last updated at.
	 * @return
	 */
	protected static long msLastServerUpdate() {
		throw new UnsupportedOperationException("Not implemented");
		// TODO actually query the Caladrius server for condition metadata
	}

	public static Condition[] getConditions() throws SyncFailedException
	{
		if (msLastSyncTime < msLastServerUpdate()) {
			return conditions;
		}
		throw new UnsupportedOperationException("Not implemented");
		// TODO actually query the Caladrius server for condition data
	}

	public static void pushConditions() throws SyncFailedException
	{
		throw new UnsupportedOperationException("Not implemented");
		// TODO attempt to push conditions to the server
		// if server responds with some sort of out-of-date message, throw SyncFailed.
	}
}
