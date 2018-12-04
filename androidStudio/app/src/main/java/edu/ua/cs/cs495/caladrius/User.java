package edu.ua.cs.cs495.caladrius;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

public class User implements Serializable
{
	private static final long serialVersionUID = -6695844257063986467L;
	public FitbitAccount fAcc;
	public ServerAccount sAcc;

	public User()
	{ /* NOP */ }

	public User(FitBitOAuth2AccessToken token)
	{
		this();
		initialize(token);
	}

	public void initialize(FitBitOAuth2AccessToken token)
	{
		this.fAcc = new FitbitAccount(token);

		String uuid;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA512");
			byte[] output = digest.digest(token.getUserId().getBytes("UTF-8"));
			uuid = Base64.getEncoder().encodeToString(output);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			throw new RuntimeException("Device does not support required algorithms");
		}

		this.sAcc = new ServerAccount(uuid);
	}
}
