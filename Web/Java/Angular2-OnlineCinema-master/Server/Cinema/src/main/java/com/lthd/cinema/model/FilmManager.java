package com.lthd.cinema.model;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.lthd.cinema.model.dao.Film;
import com.lthd.cinema.model.dao.SlideShow;

public class FilmManager {
	public static List<Film> films = new ArrayList();
	public static Map<String, List<SlideShow>> slideShows = new HashMap();
	
	public static void clearShowTime() {
		for (Film film : films)
			film.showTimes.clear();
	}
	
	public static void SortShowtimes() {
		for (Film film : films)
			for (String cine : film.showTimes.keySet()) {
				Comparator<List<String>> compDate = new Comparator<List<String>>() {
					public int compare(List<String> a, List<String> b) {
						String ya = a.get(0).substring(6, 10);
						String yb = b.get(0).substring(6, 10);
						if (!ya.equals(yb))
							return ya.compareTo(yb);
						
						String ma = a.get(0).substring(3, 5);
						String mb = b.get(0).substring(3, 5);
						if (!ma.equals(mb))
							return ma.compareTo(mb);
						
						String da = a.get(0).substring(0, 2);
						String db = b.get(0).substring(0, 2);
						
						return da.compareTo(db);
					}
				};

				Collections.sort(film.showTimes.get(cine), compDate);
				
				for(List<String> date : film.showTimes.get(cine)) {
					Comparator<String> compHour = new Comparator<String>() {
						public int compare(String a, String b) {
							
							if (a.length() == 10)
								return -1;
							if (b.length() == 10)
								return 1;
							return a.compareTo(b);
						}
					};

					Collections.sort(date, compHour);
				}
			}
	}
	
	private static String unicodeToAscii(String s) throws UnsupportedEncodingException {
		s = s.replaceAll("đ", "d");
        String s1 = Normalizer.normalize(s, Normalizer.Form.NFKD);
        String regex = Pattern.quote("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");
        String s2 = new String(s1.replaceAll(regex, "").getBytes("ascii"), "ascii");

        return s2. replaceAll("[?]", "");
    }
	
	public static Film searchFilmByName(String name) {
		for (Film film : FilmManager.films) {
			String _name = "";
			try {
				_name = unicodeToAscii(film.name.toLowerCase());
				name = unicodeToAscii(name.toLowerCase());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			if (name.contains(_name) || _name.contains(name))
				return film;
			
			_name = _name.replaceAll("\\(", "-");
			name = name.replaceAll("\\)", "-");
			String[] _words = _name.split("[:/-]");
			String[] words = name.split("[:/-]");
			for (String word : words)
				for (String _word: _words)
					if (_word.equals(word))
						return film;
		}
		return null;
	}
}
