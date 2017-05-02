package com.lthd.cinema.controller;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/account")
public class AccountController {
	private final String SECRECT = "AMENIC";
	private final long EXPIRE = 24 *60 * 60 * 1000;
	private final String TOPIC_NAME = "cinemanotification";
	private final String AUTHEN = "key=AAAAQOZqd-Q:APA91bH-CMvvYGiiCbRU6yU0WAaCHri7NO_efC2I31U9oxNIns3i4kp5vNgV0-lWBtD3dtmKajlx7Y6l3SbpnjRUdOV_IzA8xrJBr_ABHfL6b_yELkt7PbSh1C4HsXtgkX4x99A11_nS";
	public static final com.squareup.okhttp.MediaType JSON = com.squareup.okhttp.MediaType.parse("application/json");
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String login(@FormParam(value = "id") String id) throws IOException {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		 
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	 
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRECT);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	 
	    JwtBuilder builder = Jwts.builder()
	                                .setIssuedAt(now)
	                                .setIssuer(id)
	                                .signWith(signatureAlgorithm, signingKey);
	 
	    long expMillis = nowMillis + EXPIRE	;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
      
	    return builder.compact();
	}
	
	@Path("dosomething")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String dosomething(@FormParam(value = "token") String token) throws IOException {
		String result;
		
		try {
			 Claims claims = Jwts.parser()         
		       .setSigningKey(DatatypeConverter.parseBase64Binary(SECRECT))
		       .parseClaimsJws(token).getBody();
			 result = claims.getIssuer();
		}
		catch (ExpiredJwtException exception) {
			result = "expired";
		}
			    
	    return result;
	}
	
	@Path("notification/subscribe")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String subscribe(@FormParam(value = "token") String token) throws IOException {
		String url = "https://iid.googleapis.com/iid/v1/" + token + "/rel/topics/" + TOPIC_NAME;
		
		OkHttpClient client = new OkHttpClient();
    	RequestBody body = new FormEncodingBuilder()
                .build();
    	Request request = new Request.Builder()
	      .url(url)
	      .addHeader("Content-Type", "application/json")
	      .addHeader("Authorization", AUTHEN)
	      .post(body)
	      .build();

		Response response = client.newCall(request).execute();
		
	
	    return String.valueOf(response.code());
	}
	
	@Path("notification/put")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String put(@FormParam(value = "title") String title, 
			@FormParam(value = "content") String content,
			@FormParam(value = "link") String link,
			@FormParam(value = "token") String token) throws IOException {
		String url = "https://fcm.googleapis.com/fcm/send";
		
		String nofication = 
				"{ \"notification\": {" +
						"\"title\": \"" + title + "\"," +
						"\"body\": \"" + content + "\"," +
						"\"icon\": \"cinema-logo.png\"," +
						"\"click_action\" : \"" + link + "\"" +
					"}," +
			  		"\"to\" : \"/topics/" + TOPIC_NAME + "\"" +
				"}";
		
		OkHttpClient client = new OkHttpClient();
    	RequestBody body = RequestBody.create(JSON, nofication);
    	Request request = new Request.Builder()
	      .url(url)
	      .addHeader("Content-Type", "application/json")
	      .addHeader("Authorization", AUTHEN)
	      .post(body)
	      .build();

		Response response = client.newCall(request).execute();
		
		return String.valueOf(response.code());
	}
}
