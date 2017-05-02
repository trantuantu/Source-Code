package com.lthd.cinema.model;

import java.io.IOException;

public abstract class FilmParser {
	public String url;
	public abstract void parse() throws IOException;
	public abstract void getSlideShow() throws IOException;
}
