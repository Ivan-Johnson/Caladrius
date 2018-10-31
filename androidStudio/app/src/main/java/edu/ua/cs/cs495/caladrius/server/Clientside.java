package edu.ua.cs.cs495.caladrius.server;


import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;

public class Clientside
{
	// TODO
	protected static long msLastSyncTime = Long.MIN_VALUE;
	protected static Condition[] conditions;

	public static int[] getFeedIDs(String userid) throws IOException
	{
		HttpClientBuilder hcb = HttpClientBuilder.create();
		HttpClient hc = hcb.build();

		long lNow = System.currentTimeMillis() / 1000L;

		HttpGet hg = new HttpGet("https://caladrius.ivanjohnson.net/webapi/config/feeds");
		hg.setHeader("MODDATE", Long.toString(lNow));
		hg.addHeader("USERUUID", userid);
		try {
			HttpResponse rsp = hc.execute(hg);
			StatusLine statusLine = rsp.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("Couldn't connect to server; got code "+statusCode);
			}
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			rsp.getEntity().writeTo(baos);
			byte[] bytes = baos.toByteArray();
			
			ByteArrayInputStream bais;
			Scanner s;
			
			bais = new ByteArrayInputStream(bytes);
			s = new Scanner(bais, "UTF-8");
			int numLines = 0;
			while(s.hasNextLine()) {
				numLines++;
				s.nextLine();
			}
			s.close();
			
			int feedids[] = new int[numLines]; 
			bais = new ByteArrayInputStream(bytes);
			s = new Scanner(bais, "UTF-8");
			while(s.hasNextLine()) {
				numLines--;
				feedids[numLines] = Integer.parseInt(s.nextLine());
			}
			s.close();
			
			return feedids;
		} finally {
			hg.releaseConnection();
		}
	}
	
	protected static String getFeedstring(String userid, int feed) throws IOException
	{
		HttpClientBuilder hcb = HttpClientBuilder.create();
		HttpClient hc = hcb.build();

		long lNow = System.currentTimeMillis() / 1000L;

		HttpGet hg = new HttpGet("https://caladrius.ivanjohnson.net/webapi/config/feed?id="+Integer.toString(feed));
		hg.setHeader("MODDATE", Long.toString(lNow));
		hg.addHeader("USERUUID", userid);
		try {
			HttpResponse rsp = hc.execute(hg);
			StatusLine statusLine = rsp.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("Couldn't connect to server; got code "+statusCode);
			}
			
			Scanner s = new Scanner(rsp.getEntity().getContent(), "UTF-8");
			s.useDelimiter("\\A");
			String str = s.hasNext() ? s.next() : "";
			s.close();
			return str;
		} finally {
			hg.releaseConnection();
		}
	}
	
	public static void setFeed(String userid, int feed, String base64) throws IOException
	{
		if (base64.length() > 100000) {
			throw new IllegalArgumentException("Given string is " + base64.length() + " char long; it must be less than 100000 to fit in the database.");
		}
		HttpClientBuilder hcb = HttpClientBuilder.create();
		HttpClient hc = hcb.build();

		long lNow = System.currentTimeMillis() / 1000L;

		HttpPut hp = new HttpPut("https://caladrius.ivanjohnson.net/webapi/config/feed?id=" + Integer.toString(feed));
		hp.setHeader("MODDATE", Long.toString(lNow));
		hp.addHeader("USERUUID", userid);
		EntityBuilder eb = EntityBuilder.create();
		eb.setBinary(base64.getBytes("UTF-8"));
		hp.setEntity(eb.build());
		
		try {
			HttpResponse rsp = hc.execute(hp);
			StatusLine statusLine = rsp.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException("Couldn't connect to server; got code " + statusCode);
			}
		} finally {
			hp.releaseConnection();
		}
	}
	
	public static Feed getFeed(String userid, int feed) throws IOException
	{
		String base64 = getFeedstring(userid, feed);
		byte bytes[] = Base64.getDecoder().decode(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			Feed f = (Feed) ois.readObject();
			return f;
		} catch (ClassNotFoundException cnfe) {
			return null;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}
	
	public static void setFeed(String userid, Feed f) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(bos);   
			out.writeObject(f);
			out.flush();
			byte[] bytes = bos.toByteArray();
			String base64 = Base64.getEncoder().encodeToString(bytes);
			setFeed(userid, f.id, base64);
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// NOP
			}
		}
	}

	public static void main(String args[]) throws Exception
	{
		final String UUID = "thisisauserid1";
		int feeds[] = getFeedIDs(UUID);
		for (int feed : feeds) {
			Feed f = new Feed("this is a name", "This is a URL");
			f.id = feed;
			//setFeed(UUID, f);
			StringBuilder sb = new StringBuilder();
			sb.append(feed);
			sb.append(": ");
			sb.append(getFeed(UUID, feed).url);
			System.out.println(sb.toString());
		}
	}
}
