package edu.ua.cs.cs495.caladrius.server;


import android.util.Log;
import edu.ua.cs.cs495.caladrius.User;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Scanner;

/**
 * Clientside is a java wrapper around Caladrius' server's web API.
 */
public class Clientside
{
	public OkHttpClient client;

	public Clientside()
	{
		client = new OkHttpClient.Builder()
			.build();
	}

	/**
	 * The inverse operation of base64FromSerializable, except that it returns the object as type Object instead of Serializable.
	 * @param base64
	 * @return
	 * @throws IOException
	 */
	public static Object objectFromBase64(String base64) throws IOException
	{
		byte bytes[] = Base64.getDecoder()
		                     .decode(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (ClassNotFoundException cnfe) {
			return null;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	/**
	 * Serializes the given object into an array of bytes, then returns a base64 string that represents those bytes.
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static String base64FromSerializable(Serializable s) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(s);
			out.flush();
			byte[] bytes = bos.toByteArray();
			String base64 = Base64.getEncoder()
			                      .encodeToString(bytes);
			return base64;
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// NOP
			}
		}
	}

	/**
	 * Returns true iff r was sucessful. Otherwise, prints an error message to os and returns false.
	 * @param r
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public static boolean assertCode(Response r, PrintStream os) throws IOException
	{
		boolean pass = r.code() == 200;
		if (!pass) {
			os.println("CODE:    " + r.code());
			os.println("MESSAGE: " + r.message());
			os.print("<BODY>\n" + r.body()
			                       .string() + "</BODY>\n");
		}
		return pass;
	}

	/**
	 * Queries the Caladrius database and returns the feedids of every feed owned by the given user.
	 * @param sa
	 * @return
	 * @throws IOException
	 */
	public String[] getFeedIDs(ServerAccount sa) throws IOException
	{
		String userid = sa.uuid;
		final String URL = "https://caladrius.ivanjohnson.net/webapi/config/feeds";

		Request request = new Request.Builder()
			.url(URL)
			.addHeader("useruuid", userid)
			.build();

		Response r = client.newCall(request)
		                   .execute();

		byte[] bytes = r.body()
		                .bytes();

		ByteArrayInputStream bais;
		Scanner s;

		bais = new ByteArrayInputStream(bytes);
		s = new Scanner(bais, "UTF-8");
		int numLines = 0;
		while (s.hasNextLine()) {
			numLines++;
			s.nextLine();
		}
		s.close();

		String feedids[] = new String[numLines];
		bais = new ByteArrayInputStream(bytes);
		s = new Scanner(bais, "UTF-8");
		while (s.hasNextLine()) {
			numLines--;
			feedids[numLines] = s.nextLine();
		}
		s.close();
		if (numLines != 0) {
			throw new RuntimeException("Server did not yield expected number of feeds");
		}

		return feedids;
	}

	/**
	 * Queries the Caladrius server and returns the base64 string that represents the given feed
	 * @param userid
	 * @param feeduuid
	 * @return
	 * @throws IOException
	 */
	protected String getFeedstring(String userid, String feeduuid) throws IOException
	{
		final String URL_BASE = "https://caladrius.ivanjohnson.net/webapi/config/feed";
		final String URL_QUERY_KEY = "id";

		final String url = URL_BASE + "?" + URL_QUERY_KEY + "=" + feeduuid;

		long lNow = System.currentTimeMillis() / 1000L;

		Request request = new Request.Builder()
			.url(url)
			.addHeader("useruuid", userid)
			.addHeader("MODDATE", Long.toString(lNow))
			.build();

		Scanner s;
		Response r = client.newCall(request)
		                   .execute();
		boolean b = assertCode(r, System.err);
		if (!b) {
			throw new IOException();
		}

		s = new Scanner(r.body()
		                 .byteStream(), "UTF-8");
		s.useDelimiter("\\A");

		String str = s.hasNext() ? s.next() : ""; // base64: ..., lastModify: ...
		String attribute = str.split(",")[0];     // base64: ...
		String base64 = attribute.split(" ")[1];  // ...

		s.close();
		return base64;
	}

	/**
	 * Stores the given base64 string in the Caladrius database as the representation of the specified feed
	 * @param sa
	 * @param feedid
	 * @param base64
	 * @throws IOException
	 */
	public void setFeed(ServerAccount sa, String feedid, String base64) throws IOException
	{
		String userid = sa.uuid;
		final String URL_BASE = "https://caladrius.ivanjohnson.net/webapi/config/feed";
		final String URL_QUERY_KEY = "id";
		final String url = URL_BASE + "?" + URL_QUERY_KEY + "=" + feedid;

		long lNow = System.currentTimeMillis() / 1000L;

		if (base64.length() >= 100000) {
			throw new IllegalArgumentException("Given string is " + base64.length() +
				" char long; it must be less than 100000 to fit in the database.");
		}

		MediaType mt = MediaType.parse("text/plain; charset=utf-8");

		RequestBody body = RequestBody.create(mt, base64.getBytes("UTF-8"));

		Request request = new Request.Builder()
			.url(url)
			.put(body)
			.addHeader("useruuid", userid)
			.addHeader("MODDATE", Long.toString(lNow))
			.build();

		Response r = client.newCall(request)
		                   .execute();
		boolean b = assertCode(r, System.err);
		if (!b) {
			throw new IOException();
		}
	}

	/**
	 * Returns the base64 string stored in the Caladrius database for the specified userid
	 * @param userid
	 * @return
	 */
	public String getBase64user(String userid)
	{
		final String URL = "https://caladrius.ivanjohnson.net/webapi/config/user";

		Request request = new Request.Builder()
			.url(URL)
			.addHeader("useruuid", userid)
			.build();

		Scanner s = null;
		try {
			Response r = client.newCall(request)
			                   .execute();
			boolean b = assertCode(r, System.err);
			if (!b) {
				throw new IOException();
			}

			s = new Scanner(r.body()
			                 .byteStream(), "UTF-8");
			s.useDelimiter("\\A");
		} catch (IOException e) {
			return null;
		}

		String base64 = s.next();

		s.close();
		return base64.trim();
	}

	/**
	 * Returns the specified feed object from the Caladrius server's database
	 * @param sa
	 * @param uuid
	 * @return
	 * @throws IOException
	 */
	public Feed getFeed(ServerAccount sa, String uuid) throws IOException
	{
		String userid = sa.uuid;
		String base64 = getFeedstring(userid, uuid);
		return (Feed) objectFromBase64(base64);
	}

	/**
	 * Stores the given feed object in Caladrius' server's database
	 * @param sa
	 * @param f
	 * @throws IOException
	 */
	public void setFeed(ServerAccount sa, Feed f) throws IOException
	{
		if (f == null) {
			throw new NullPointerException("Cannot set feed to null");
		}
		setFeed(sa, f.uuid, base64FromSerializable(f));
	}

	/**
	 * Delete the specified feed from Caladrius' Server's database
	 * @param sa
	 * @param feedid
	 * @throws IOException
	 */
	public void deleteFeed(ServerAccount sa, String feedid) throws IOException
	{
		final String URL_BASE = "https://caladrius.ivanjohnson.net/webapi/config/feed";
		final String URL_QUERY_KEY = "id";
		final String url = URL_BASE + "?" + URL_QUERY_KEY + "=" + feedid;

		Log.e(Clientside.class.getSimpleName(), "useruuid: " + sa.uuid + "; url: " + url);

		Request request = new Request.Builder()
			.url(url)
			.addHeader("useruuid", sa.uuid)
			.delete()
			.build();

		Response r = client.newCall(request)
		                   .execute();


		if (r.code() != 200) {
			Log.e(Clientside.class.getSimpleName(),
				"Feed deletion failed with code " + r.code() + " and body: \n" + r.body()
				                                                                  .toString());
			throw new IOException();
		}
		return;
	}

	/**
	 * Fetches the user object from Caladrius' Server's database
	 *
	 * @param sa
	 * @return
	 * @throws IOException
	 */
	public User getUser(ServerAccount sa) throws IOException
	{
		String base64 = getBase64user(sa.uuid);
		if (base64 == null) {
			return null;
		}
		return (User) objectFromBase64(base64);
	}

	/**
	 * Stores the given User in Caladrius' Server's database
	 * @param sa
	 * @param user
	 * @throws IOException
	 */
	public void setUser(ServerAccount sa, User user) throws IOException
	{
		String userid = sa.uuid;
		final String URL = "https://caladrius.ivanjohnson.net/webapi/config/user";

		String base64 = base64FromSerializable(user);
		if (base64.length() >= 100000) {
			throw new IllegalArgumentException("Given string is " + base64.length() +
				" char long; it must be less than 100000 to fit in the database.");
		}

		MediaType mt = MediaType.parse("text/plain; charset=utf-8");

		RequestBody body = RequestBody.create(mt, base64.getBytes("UTF-8"));

		Request request = new Request.Builder()
			.url(URL)
			.put(body)
			.addHeader("useruuid", userid)
			.build();

		Response r = client.newCall(request)
		                   .execute();
		boolean b = assertCode(r, System.err);
		if (!b) {
			throw new IOException();
		}
	}
}
