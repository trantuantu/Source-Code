package com.lthd.cinema.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lthd.cinema.model.dao.Film;
import com.lthd.cinema.model.dao.SlideShow;

public class CGVParser extends FilmParser{
	public CGVParser(String url) {
        super();
        this.url = url;
    }

    @Override
    public void parse() throws IOException {
    	Document doc = Jsoup.connect("https://www.cgv.vn/vn/theaters/showtimes/index/city/vn-08/name").get();
        Elements elements = doc.select("ul[id=vn-08]").get(0).children();
        
        for (int i = 0; i < elements.size(); i++) {
        	try {
	        	String dataShowTimes = elements.get(i).child(0).attr("onclick");
	            dataShowTimes = dataShowTimes.substring(dataShowTimes.indexOf("https"), dataShowTimes.lastIndexOf("'")).replaceFirst("','", "?form_key=");
	            String cine = elements.get(i).child(0).text();
	            
	            URL _url = new URL(dataShowTimes);
	            URLConnection urlconnection = _url.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
	            String inputLine;
	            StringBuilder sb = new StringBuilder();
	            while ((inputLine = in.readLine()) != null)
	            	sb.append(inputLine);
	            in.close();
	            
	            Map<String, String> showtime = new HashMap<String, String>();
	        	JSONObject obj = new JSONObject(sb.toString());
	        	JSONObject data = obj.getJSONObject("data");
	        	Iterator<String> date = data.keySet().iterator();
	        	while (date.hasNext()) {
	        		try {
	        			String d = date.next();
	        			Calendar cal = Calendar.getInstance();
	        			cal.setTimeInMillis(Long.valueOf(d) * 1000);
	        			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        			String strDate = dateFormat.format(cal.getTime());
	        			
	        			JSONObject dateObj = data.getJSONObject(d);
	            		Iterator<String> films = dateObj.keySet().iterator();
	            		while (films.hasNext()){
	            			JSONObject filmObj = dateObj.getJSONObject(films.next());
	            			filmObj = filmObj.getJSONObject(filmObj.keySet().iterator().next());
	            			
	            			JSONObject movie = filmObj.getJSONObject("movie");
	            			Film film = null;
	            			String filmName = movie.getString("name");
	                		for (int j = 0; j < FilmManager.films.size(); j++)
	                			if (FilmManager.films.get(j).name.equals(filmName)) {
	                				film = FilmManager.films.get(j);
	                				break;
	                			}
	                		if (film == null) 
	                			continue;
	                		 
	                		JSONObject session = filmObj.getJSONObject("session");
	                		session = session.getJSONObject(session.keySet().iterator().next());
	                		
	                		List<List<String>> times;
	                		if (!film.showTimes.containsKey(cine))
	                			times = new ArrayList();
	                		else 
	                			times = film.showTimes.get(cine);
	                		Iterator<String> st = session.keySet().iterator();
	                		List<String> _times = new ArrayList();
	                		_times.add(strDate);
	                		while (st.hasNext()) {
	                			String time = st.next();
	                			_times.add(session.getJSONObject(time).getString("time"));
	                		}
	                		times.add(_times);
	                		film.showTimes.put(cine, times);
	            		}
	        		}
	        		catch (Exception ex) {
	        			System.out.println(ex);
	        		}
	        	}
		    }
		    catch (Exception ex) {
		    	System.out.println(ex);
		    }
        }
    }
    
    public void getListFilm(String url) throws IOException {
    	Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("ul[class=products-grid products-grid--max-4-col]").get(0).children();
       
        for (int i = 0; i < elements.size(); i++) {
        	Film film = new Film();
        	try{
        		if (elements.get(i).select("h2[class=product-name]").size() > 0)
        			film.name = elements.get(i).select("h2[class=product-name]").get(0).child(0).attr("title");
        		if (elements.get(i).select("a[class=product-image]").size() > 0)
	        		film.img = elements.get(i).select("a[class=product-image]").get(0).child(0).attr("src");
        		if (elements.get(i).select("div[class=movie-genre]").size() > 0)
		        	film.genre = elements.get(i).select("div[class=movie-genre]").get(0).child(1).text();  
		        if (elements.get(i).select("div[class=movie-actress]").size() > 0)
		        	film.time = elements.get(i).select("div[class=movie-actress]").get(0).child(1).text(); 
		        if (elements.get(i).select("div[class=movie-release]").size() > 0)
		        	film.release = elements.get(i).select("div[class=movie-release]").get(0).child(1).text(); 
		        try {
			        Document doc2 = Jsoup.connect(elements.get(i).select("h2[class=product-name]").get(0).child(0).attr("href")).get();
			        Elements elements2 = doc2.select("div[id=movie-trailer]");
			        film.trailer = elements2.get(0).text().replaceAll(" [?]autoplay=1&", "?");	

		        }
		        catch (Exception ex) {
		        	System.out.println(ex);
		        	film.trailer = "";
		        }
        	}
        	catch (Exception ex) {
        		System.out.println(ex);
        	}
	        
        	FilmManager.films.add(film);
        }
    	
    }
    
    @Override
    public void getSlideShow() throws IOException {
    	Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("ul[id=home_big_banner]").get(0).children();
       
        List<SlideShow> slides = new ArrayList(); 
        for (int i = 0; i < elements.size(); i++) 
        	slides.add(new SlideShow(elements.get(i).select("a").attr("href"), elements.get(i).select("img").attr("src")));
        FilmManager.slideShows.put("CGV", slides);
    }
}
