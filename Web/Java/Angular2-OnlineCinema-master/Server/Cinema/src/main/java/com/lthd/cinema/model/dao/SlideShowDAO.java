package com.lthd.cinema.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class SlideShowDAO {
	private final static DBCollection collection = MongoUtils.db.getCollection("SlideShow"); 
	
	public static String get(String cine) {
		try {
			BasicDBObject query = new BasicDBObject()
					.append("cinema", cine);
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0)
					.append("slideShows", 1);
			
			return collection.findOne(query, fields).toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static void save(Map<String, List<SlideShow>> slideShows) {
		collection.drop();

		for (String cine : slideShows.keySet()) {
			List<DBObject> listSlideShow = new ArrayList();
			for (SlideShow slideShow : slideShows.get(cine)) 
				listSlideShow.add(new BasicDBObject()
										.append("url", slideShow.url)
										.append("image", slideShow.img));
			
			BasicDBObject objSlideShows = new BasicDBObject()
					.append("cinema", cine)
					.append("slideShows", listSlideShow);	
			collection.insert(objSlideShows);
		}
		
	}
}
