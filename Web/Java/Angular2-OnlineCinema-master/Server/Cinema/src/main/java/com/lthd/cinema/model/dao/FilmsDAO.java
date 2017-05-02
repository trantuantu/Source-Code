package com.lthd.cinema.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class FilmsDAO {
	private final static DBCollection collection = MongoUtils.db.getCollection("Film"); 
	
	public static String getFilmById(String id) {
		try {
			BasicDBObject query = new BasicDBObject()
					.append("id", id);
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0)
					.append("id", 0);
			
			return collection.findOne(query, fields).toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static String getListFilm() {
		try {
			BasicDBObject query = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0);

			BasicDBObject result = new BasicDBObject()
					.append("films", collection.find(query, fields).toArray());
			return result.toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static String getListFilmNowShowing() {
		try {
			BasicDBObject query = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0);

			List<DBObject> list = collection.find(query, fields).toArray();
			List<DBObject> films = new ArrayList();
			
			for (DBObject film : list)
				if (!ShowTimeDAO.isEmptyShowTime(film.get("id").toString()))
					films.add(film);
			
			BasicDBObject result = new BasicDBObject()
					.append("films", films);
			return result.toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static String getListFilmComingSoon() {
		try {
			BasicDBObject query = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0);

			List<DBObject> list = collection.find(query, fields).toArray();
			List<DBObject> films = new ArrayList();
			
			for (DBObject film : list)
				if (ShowTimeDAO.isEmptyShowTime(film.get("id").toString()))
					films.add(film);
			
			BasicDBObject result = new BasicDBObject()
					.append("films", films);
			return result.toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static String getByCinema(String cinema) {
		try {
			BasicDBObject query = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject()
					.append("_id", 0);

			List<DBObject> list = new ArrayList();
			for (DBObject obj : collection.find(query, fields).toArray()) 
				if (!ShowTimeDAO.isEmptyShowTime(obj.get("id").toString(), cinema))
					list.add(obj);
			
			BasicDBObject result = new BasicDBObject()
					.append("films", list);
			return result.toString();
		}
		catch (Exception ex) {
			System.out.println(ex);
			return "null";
		}
	}
	
	public static void save(List<Film> films) {
		collection.drop();
		int id = 0;
		for (Film film : films) {
			id++;
			BasicDBObject objFilm = new BasicDBObject() 
					.append("id", "" + id)
					.append("name", film.name)
					.append("genre", film.genre)
					.append("duration", film.time)
					.append("release", film.release)
					.append("img", film.img)
					.append("trailer", film.trailer);
			collection.insert(objFilm);
		}
	}
}
