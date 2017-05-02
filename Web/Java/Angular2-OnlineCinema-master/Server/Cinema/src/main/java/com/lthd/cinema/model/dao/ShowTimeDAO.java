package com.lthd.cinema.model.dao;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class ShowTimeDAO {
	private final static DBCollection collection = MongoUtils.db.getCollection("ShowTime"); 
	
	public static String getById_Cinema(String id, String cinema) {
		try {
			BasicDBObject query = new BasicDBObject()
					.append("id_film", id)
					.append("cinema", cinema);
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0)
					.append("showtimes", 1);
			
			return collection.findOne(query, fields).toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static void save(String cinema, List<Film> films) {
		int id = 0;
		for (Film film : films) {
			id++;
			BasicDBObject objShowTimes = new BasicDBObject();
			
			for (String cine : film.showTimes.keySet()) {
				BasicDBObject objTimes = new BasicDBObject();
				
				for (List<String> showtime : film.showTimes.get(cine)) {
					String date = showtime.get(0);
					showtime.remove(0);
					
					objTimes.append(date, showtime);
				}
				
				objShowTimes.append(cine, objTimes);
			}
					
			BasicDBObject objFilm = new BasicDBObject() 
					.append("id_film", "" + id)
					.append("cinema", cinema)
					.append("showtimes", objShowTimes);
			collection.insert(objFilm);
		}
	}
	
	public static void clear() {
		collection.drop();
	}

	public static boolean isEmptyShowTime(String id, String cinema) {
		try {
			BasicDBObject query = new BasicDBObject()
					.append("id_film", id)
					.append("cinema", cinema);
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0)
					.append("showtimes", 1);
			
			return collection.findOne(query, fields).get("showtimes").toString().equals("{ }");
		}
		catch (Exception ex) {
			System.out.println(ex);
			return true;
		}
	}
	
	public static boolean isEmptyShowTime(String id) {
		try {
			BasicDBObject query = new BasicDBObject()
					.append("id_film", id);
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0)
					.append("showtimes", 1);
			
			List<DBObject> list = collection.find(query, fields).toArray();
			
			for (DBObject st : list)
				if (!st.get("showtimes").toString().equals("{ }"))
					return false;
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex);
			return true;
		}
	}
}
