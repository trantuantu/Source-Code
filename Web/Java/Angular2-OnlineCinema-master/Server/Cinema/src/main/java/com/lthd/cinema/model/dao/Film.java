package com.lthd.cinema.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Film {
	public String name;
	public String genre;
	public String time;
	public String release;
	public String img;	
	public String trailer;
	public Map<String, List<List<String>>> showTimes;
	
	public Film() {
		showTimes = new HashMap();
	}
}
