package edu.ua.cs.cs495.caladrius.server;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

public class ServerAccount implements Serializable
{
	private static final long serialVersionUID = 2723214037041506590L;
	String uuid;

	protected String randomUUID()
	{
		UUID uuid = UUID.randomUUID();
		BigInteger bi = new BigInteger(uuid.toString().replace("-", ""), 16);
		return String.format("%039d", bi);
	}

	public ServerAccount()
	{
		uuid = randomUUID();
	}
}
