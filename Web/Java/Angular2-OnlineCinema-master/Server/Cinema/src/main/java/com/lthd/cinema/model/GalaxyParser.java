package com.lthd.cinema.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lthd.cinema.model.dao.Film;
import com.lthd.cinema.model.dao.SlideShow;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TuNam1995 on 19/05/2016.
 */
public class GalaxyParser extends FilmParser{
	
	private Document doc; 

    public GalaxyParser(String url) {
        super();
        this.url = url;
        try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void parse() throws IOException {
    	
    	Element element = doc.select("div[class=sub-2]").get(0).child(0);
    	String ajaxFilm = element.attr("data-ajax");
    	
    	Elements elements = element.select("ul[class=items]").get(0).children();
    	
    	for (int i = 0; i < elements.size(); i++) {
    		Film film = FilmManager.searchFilmByName(elements.get(i).text());
    		if (film == null)
    			continue;
	    	OkHttpClient client = new OkHttpClient();
	    	RequestBody body = new FormEncodingBuilder()
	                .add(element.attr("data-param"), elements.get(i).attr("data-id"))
	                .build();
	    	Request request = new Request.Builder()
		      .url(ajaxFilm)
		      .post(body)
		      .build();
	
			Response response = client.newCall(request).execute();
			loadFilm(element.attr("data-param"), elements.get(i).attr("data-id"), response.body().string(), film);
			
    	}
    	
    }

	private void loadFilm(String dataParam, String dataId, String data, Film film) throws IOException {
    	Element element = doc.select("div[class=sub-2]").get(0).child(1);
    	String ajaxShowtime = element.attr("data-ajax");
    	
    	Document doc = Jsoup.parse(data);
		Elements elements = doc.select("li");
		for (int i = 0; i < elements.size(); i++) 
			if (!elements.get(i).text().equals("Galaxy Bến Tre") 
			&& !elements.get(i).text().equals("Galaxy Đà Nẵng")) {
				OkHttpClient client = new OkHttpClient();
		    	RequestBody body = new FormEncodingBuilder()
		    			.add(dataParam, dataId)
		                .add(element.attr("data-param"), elements.get(i).attr("data-id"))
		                .build();
		    	Request request = new Request.Builder()
			      .url(ajaxShowtime)
			      .post(body)
			      .build();
		
				Response response = client.newCall(request).execute();
				addShowTimes(response.body().string(), film, elements.get(i).text());
			}
    }
    
    private void addShowTimes(String data, Film film, String cine) {
    	Document doc = Jsoup.parse(data);
		Elements elements = doc.select("div[class=item-wrap]");
		List<List<String>> times = new ArrayList();
		for (int i = 0; i < elements.size(); i++) {
			List<String> time = new ArrayList();
			String date = elements.get(i).select("h4").text();
			time.add(date.substring(date.indexOf(", ") + 2, date.length()).replaceAll("-", "/"));
			Elements elements2 = elements.get(i).select("li");
			for (int j = 0; j < elements2.size(); j++) 
				time.add(elements2.get(j).text());
			times.add(time);
		}
		film.showTimes.put(cine, times);
	}

	@Override
    public void getSlideShow() throws IOException {
        Elements elements = doc.select("div[class=slide-item]");
       
        List<SlideShow> slides = new ArrayList(); 
        for (int i = 0; i < elements.size(); i++)
        	slides.add(new SlideShow(elements.get(i).select("a").get(elements.get(i).select("a").size() - 1).attr("href"), 
        			elements.get(i).select("img").attr("src")));
        FilmManager.slideShows.put("Galaxy", slides);
    }
}
