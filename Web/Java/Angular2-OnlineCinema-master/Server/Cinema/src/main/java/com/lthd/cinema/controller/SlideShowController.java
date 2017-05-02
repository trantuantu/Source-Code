package com.lthd.cinema.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.lthd.cinema.model.BHDParser;
import com.lthd.cinema.model.CGVParser;
import com.lthd.cinema.model.FilmManager;
import com.lthd.cinema.model.FilmParser;
import com.lthd.cinema.model.GalaxyParser;
import com.lthd.cinema.model.dao.SlideShowDAO;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Path("/slideshows")
public class SlideShowController {
	
	@Path("admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String set() throws IOException {
		FilmManager.slideShows.clear();
		
		FilmParser cinema = new CGVParser("https://www.cgv.vn/vn/");
		cinema.getSlideShow();
		
		cinema = new GalaxyParser("https://www.galaxycine.vn/");
		cinema.getSlideShow();
		
		cinema = new BHDParser("http://bhdstar.vn/");
		cinema.getSlideShow();
		
		SlideShowDAO.save(FilmManager.slideShows);
		
		return "done";
	}
	
	@Path("{cinema}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String get(@PathParam(value = "cinema") String cinema) throws IOException {
		return SlideShowDAO.get(cinema);
	}
}
