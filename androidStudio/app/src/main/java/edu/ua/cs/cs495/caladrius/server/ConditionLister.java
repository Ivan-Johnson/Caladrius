package edu.ua.cs.cs495.caladrius.server;

/**
 * This file is compiled into a separate jar file to be run on the server.
 *
 * It lists the days on which a feed's conditions were tripped. The feed is specified by the two arguments that this file expects:
 * 1: userid
 * 2: feedid
 *
 * Each line of the output of this file specifies a single day that triggered at least one conditions, and is formatted as follows:
 * YYYY-MM-DD [title]
 *
 * @author Ivan Johnson
 */
public class ConditionLister
{
	public static void main(String args[])
	{
		if (args.length != 2) {
			System.err.println("Usage: " + ConditionLister.class.getSimpleName() + " <userid> <feedid>");
			System.exit(1);
		}

		System.out.println("2018-12-05 arg0: " + args[0] + "; arg1: "+args[1]);
	}
}
