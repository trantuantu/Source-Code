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
public class BHDParser extends FilmParser{
	
	private Document doc; 
	private String ajaxUrl = "http://bhdstar.vn/wp-admin/admin-ajax.php";

    public BHDParser(String url) {
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
    	Document doc = Jsoup.connect("http://bhdstar.vn/lich-chieu-theo-phim/").get();	  	
    	Elements elements = doc.select("ul[class=slides bhd-lich-chieu-chon-phim]").get(0).children();
    	
    	for (int i = 0; i < elements.size(); i++) {
    		Film film = FilmManager.searchFilmByName(elements.get(i).select("span[class=movie--name]").text());
    		if (film == null)
    			continue;
	    	OkHttpClient client = new OkHttpClient();
	    	RequestBody body = new FormEncodingBuilder()
	                .add("action", "bhd_lichchieu_chonphim")
	                .add("movies_id", elements.get(i).select("a").attr("data-id"))
	                .build();
	    	Request request = new Request.Builder()
		      .url(ajaxUrl)
		      .post(body)
		      .build();
	
			Response response = client.newCall(request).execute();
			addShowTimes(response.body().string(), film);
			
			
    	}
    }
    
    private void addShowTimes(String data, Film film) {
    	Document doc = Jsoup.parse(data);
		Elements elements = doc.select("ul[class=list--showtimes-cinema]").get(0).children();

		for (int i = 0; i < elements.size(); i++) {
			String cine = elements.get(i).select("h4[class=title]").text();
			if (cine.equals("BHD STAR VINCOM PHẠM NGỌC THẠCH"))
				continue;
			List<List<String>> times = new ArrayList();
			try{
				Elements elements2 = elements.get(i).select("li[class=item--film-type]");
				for (Element es : elements2) {
					for (Element element : es.select("ul")) {
						String date = element.attr("class").substring(11, 21);
						date = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
						
						List<String> time = null;
						
						for (List<String> t : times)
							if (t.get(0).equals(date)) {
								time = t;
								break;
							}
						if (time == null)
							time = new ArrayList();
						time.add(date);
						for (Element hour : element.children())
							time.add(hour.text().substring(0, 5));
						times.add(time);
					}
				}	
				film.showTimes.put("BHD STAR " + cine, times);
			}
			catch(Exception ex) {
				System.out.println(ex);
			}
			
		}
	}

	@Override
    public void getSlideShow() throws IOException {
		Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("ul[class=slides]").get(0).children();
       
        List<SlideShow> slides = new ArrayList(); 
        for (int i = 0; i < elements.size(); i++)
        	slides.add(new SlideShow(elements.get(i).select("a").attr("href"), elements.get(i).select("img").attr("src")));
        FilmManager.slideShows.put("BHD", slides);
    }
}
