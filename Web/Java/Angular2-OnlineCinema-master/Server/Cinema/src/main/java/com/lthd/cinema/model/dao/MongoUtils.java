package com.lthd.cinema.model.dao;

import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoUtils {
	//private final static String HOST = "localhost";
	private final static String HOST = "node18365-cinematest.njs.jelastic.vps-host.net";
	private final static int PORT = 27017;
	private final static String DB_NAME = "Cinema";

	//public static DB db = new MongoClient(HOST, PORT).getDB(DB_NAME);
	private final static MongoCredential credential = MongoCredential.createCredential("cinema",DB_NAME, "cinema".toCharArray());
	public static DB db = new MongoClient(new ServerAddress(HOST), Arrays.asList(credential)).getDB(DB_NAME); 

}
