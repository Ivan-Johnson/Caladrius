package edu.ua.cs.cs495.caladrius.server;

import java.io.Serializable;
import java.util.Objects;

public class ServerAccount implements Serializable
{
	private static final long serialVersionUID = 2723214037041506590L;
	String uuid;

	public ServerAccount(String uuid)
	{
		this.uuid = uuid;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ServerAccount)) {
			return false;
		}
		ServerAccount other = (ServerAccount) obj;
		boolean equal = true;

		equal = equal && Objects.equals(uuid, other.uuid);

		return equal;
	}
}
