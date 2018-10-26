package edu.ua.cs.cs495.caladrius.server;

import java.io.Serializable;
import java.util.UUID;

public class ServerAccount implements Serializable
{
	UUID uuid;

	public ServerAccount()
	{
		uuid = UUID.randomUUID();
	}
}
