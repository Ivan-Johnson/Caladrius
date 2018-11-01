package edu.ua.cs.cs495.caladrius;

import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = -6695844257063986467L;
	public FitbitAccount fAcc;
	public ServerAccount sAcc;

	public User(FitbitAccount fAcc, ServerAccount sAcc)
	{
		this.fAcc = fAcc;
		this.sAcc = sAcc;
	}
}