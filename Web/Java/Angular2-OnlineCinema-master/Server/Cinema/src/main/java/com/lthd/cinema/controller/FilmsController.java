package com.lthd.cinema.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lthd.cinema.model.BHDParser;
import com.lthd.cinema.model.CGVParser;
import com.lthd.cinema.model.FilmManager;
import com.lthd.cinema.model.FilmParser;
import com.lthd.cinema.model.GalaxyParser;
import com.lthd.cinema.model.LotteParser;
import com.lthd.cinema.model.dao.Film;
import com.lthd.cinema.model.dao.FilmsDAO;
import com.lthd.cinema.model.dao.ShowTimeDAO;

@Path("/films")
public class FilmsController {
	
	@Path("admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String set() throws IOException {
		FilmManager.films.clear();
		ShowTimeDAO.clear();
		
		FilmParser cinema = new CGVParser("https://www.cgv.vn/vn/");
		((CGVParser)cinema).getListFilm("https://www.cgv.vn/vn/movies/now-showing.html");
		((CGVParser)cinema).getListFilm("https://www.cgv.vn/vn/movies/coming-soon.html");
	
		cinema.parse();
		FilmsDAO.save(FilmManager.films);
		FilmManager.SortShowtimes();
		ShowTimeDAO.save("CGV", FilmManager.films);
		
		cinema = new GalaxyParser("https://www.galaxycine.vn/");
		
		FilmManager.clearShowTime();
		cinema.parse();
		FilmManager.SortShowtimes();
		ShowTimeDAO.save("Galaxy", FilmManager.films);
		
		cinema = new BHDParser("http://bhdstar.vn/");
		
		FilmManager.clearShowTime();
		cinema.parse();
		FilmManager.SortShowtimes();
		ShowTimeDAO.save("BHD", FilmManager.films);
		
		cinema = new LotteParser("http://lottecinemavn.com/CMSTemplates/MBMTemplate/FancyboxSchedule.aspx");
		
		FilmManager.clearShowTime();
		cinema.parse();
		FilmManager.SortShowtimes();
		ShowTimeDAO.save("Lotte", FilmManager.films);
		
		return "done";
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getById(@PathParam(value = "id") String id) throws IOException {
		return FilmsDAO.getFilmById(id);

	}
	
	@Path("")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getAll(@PathParam(value = "id") String id) throws IOException {
		return FilmsDAO.getListFilm();
	}
	
	@Path("{id}/{cinema}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getById_Cinema(@PathParam(value = "id") String id, 
			@PathParam(value = "cinema") String cinema) throws IOException {
		return ShowTimeDAO.getById_Cinema(id, cinema);

	}
	
	@Path("cinema/{cinema}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getById_Cinema(@PathParam(value = "cinema") String cinema) throws IOException {
		return FilmsDAO.getByCinema(cinema);

	}
	
	@Path("nowshowing")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getAllNowShowing(@PathParam(value = "id") String id) throws IOException {
		return FilmsDAO.getListFilmNowShowing();
	}
	
	@Path("comingsoon")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String getAllComingSoon(@PathParam(value = "id") String id) throws IOException {
		return FilmsDAO.getListFilmComingSoon();
	}
}
