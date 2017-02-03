var querystring = require('querystring');
var http = require('http');
var fs = require('fs');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var request = require('requestretry');
var request1 = require('request');
var cheerio = require('cheerio');
var mUrl = 'http://www.lottecinemavn.com/vi-vn/buoc-1.aspx';
var mCookies = request1.jar();
var FilmScheduleDB = require('../../api/FilmSchedule');
var schedule = require('node-schedule');
var FilmSchedule = {
	getFilmSchedule: function()
	{
		console.log("start request");
		request({uri: mUrl,jar: mCookies,timeout: 120000,maxAttempts: 5,retryStrategy: request.RetryStrategies.HTTPOrNetworkError}, function(err, response, body) {
		if ( err || response.statusCode != '200'){
	    	console.log("Lỗi không truy cập được trang1");
	    	return;
		}
		var cinemas = FilmSchedule.parseListCinemas(body);
		var id_cinemas = FilmSchedule.parseIdListCinemas(body);
		FilmScheduleDB.insertCinemasIntoDb(cinemas,id_cinemas);
		
		var a = [];
		count = 0;
		for (var i in cinemas) {
			FilmSchedule.getListFilms(cinemas[i],id_cinemas[i],mUrl, function(cid,id_cid, id_films) {
				 for(var i in id_films){
					FilmSchedule.getListDateShow(cid,id_cid,id_films[i],mUrl, function(cid,id_cid, id_film,id_dates) {
						for(var j in id_dates){
							FilmSchedule.getListTimeShow(cid,id_cid,id_film,id_dates[j],mUrl, function(cid,id_cid, id_film,id_date,times) {
								//console.log(times);
								count += times.length;
								for(var i in times){
									console.log("--------------------");
									console.log("cinema: "+cid);
									console.log("id_film: "+id_film);
									console.log("id_date: "+ id_date);
									console.log(times[i]);
								}
								console.log(count);
							});
						}
					});
				 } 
			});
		}
	}).on('error', function(e){
	    	console.log(e)
	  	}).end();
	},
	
	parseListCinemas: function(body) {
		var cinemas = [];
		if(body){
			var $ = cheerio.load(body);
			var cinemas = [];
		    $('#ctl00_plcMain_lboxCinema').children().each(function() {
		       cinemas.push($(this).text());
		    });
			// parse
		}
		else{
			console.log("khong lay duoc cinemas");
		}
		return cinemas;
		
	},
	parseIdListCinemas: function(body) {
		var id_cinemas = [];
		if(body){
			var $ = cheerio.load(body);
	        $('#ctl00_plcMain_lboxCinema').children().each(function() {
	        	id_cinemas.push($(this).attr('value'));
	        });
		// parse
		}
		else
		{
			console.log("khong lay duoc id_cinemas");
		}
		return id_cinemas;
	},
	getListFilms: function(cid,id_cid,Url, callback) {
		var mCookies = request1.jar();
		var cookie = FilmSchedule.setCookieString(cid,id_cid,"","","");
	    var c = request1.cookie(cookie);
	    mCookies.setCookie(c, Url);
	    var a = [];
		request({uri: Url,jar: mCookies, timeout: 120000,maxAttempts: 5,retryStrategy: request.RetryStrategies.HTTPOrNetworkError},function(err, response, body) {
			if ( err || response.statusCode != '200'){
	    		//console.log(err);
	    		console.log("Lỗi không truy cập được trang2");
	    		console.log(mCookies.getCookies(mUrl));
	    		a.push('fsdfsdfs');
	    		//return;
			}else{
				//response.send(body);
				var id_films = FilmSchedule.parseIdListFilms(body);
				var films = FilmSchedule.parseListFilms(body);
				FilmScheduleDB.insertFimlsIntoDb(id_cid,id_films,films);
				callback.call(this, cid,id_cid, id_films);
			}
		}).on('error', function(e){
	   			console.log(e)
	  		}).end();
	},
	parseIdListFilms:function(body) {
		var id_films = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxMovie').children().each(function() {
		        id_films.push($(this).attr('value'));
		    });
		}
		return id_films;
	},
	parseListFilms:function(body) {
		var films = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxMovie').children().each(function() {
		        films.push($(this).text());
		    });
		}
		return films;
	},
	parseIdListDate: function(body) {
		var date = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxDate').children().each(function() {
	        	date.push($(this).attr('value'));
	  		});
		}
		return date;
	},
	parseListDate:function(body) {
		var date = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxDate').children().each(function() {
	       		date.push($(this).text());
	  		});
		}
		return date;
	},
	parseIdListTime: function(body) {
		var Times = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxSchedule').children().each(function() {
	        	Times.push($(this).attr('value'));
	    	});
		}
		return Times;
	},
	parseListTime: function(body) {
		var Times = [];
		if(body){
			var $ = cheerio.load(body);
			$('#ctl00_plcMain_lboxSchedule').children().each(function() {
	        	Times.push($(this).text());
	    	});
		}
		return Times;
	},
	setCookieString: function(booking_schema,cinemaCode,movieCode,playDate,playTime){
		var str = "";
		str = "booking=schema=" +booking_schema + "&cinemaCode=" + cinemaCode + "&movieCode=" + movieCode + "&playDate="+ playDate + "&playTime="+ playTime + "&seatParent=1&searChild=&listSeat=";
		return str;
	},
	getListDateShow: function(cid,id_cid,id_fiml,Url, callback){
		var mCookies = request1.jar();
		var cookie = FilmSchedule.setCookieString(cid,id_cid,id_fiml,"","");
		//console.log(cookie);
	    var c = request1.cookie(cookie);
	    mCookies.setCookie(c, Url);
		request({uri: Url,jar: mCookies, timeout: 120000,maxAttempts: 5,retryStrategy: request.RetryStrategies.HTTPOrNetworkError},function(err, response, body) {
			if ( err || response.statusCode != '200'){
				//console.log(err);
	    		console.log("Lỗi không truy cập được trang3");
	    		console.log(mCookies.getCookies(mUrl));
	    		//return;
			}
			else{
				var id_dates = FilmSchedule.parseIdListDate(body);
				var dates = FilmSchedule.parseListDate(body);
				FilmScheduleDB.insertDatesIntoDb(id_cid,id_fiml,id_dates,dates);
				callback.call(this, cid,id_cid,id_fiml,id_dates);
			}
			
		}).on('error', function(e){
	    		console.log(e)
	  		}).end();
	},
	getListTimeShow: function(cid,id_cid,id_fiml,id_date,Url, callback){
		var mCookies = request1.jar();
		var cookie = FilmSchedule.setCookieString(cid,id_cid,id_fiml,id_date,"");
		//console.log(cookie);
	    var c = request1.cookie(cookie);
	    mCookies.setCookie(c, Url);
		request({uri: Url,jar: mCookies, timeout: 120000, maxAttempts: 5,retryStrategy: request.RetryStrategies.HTTPOrNetworkError},function(err, response, body) {
			if ( err || response.statusCode != '200'){
				//console.log(err);
	    		console.log("Lỗi không truy cập được trang4");
	    		console.log(mCookies.getCookies(mUrl));
	    		//return;
			}
			else
			{	
				var id_times = FilmSchedule.parseIdListTime(body);
				var times = FilmSchedule.parseListTime(body);
				FilmScheduleDB.insertTimesIntoDb(id_cid,id_fiml,id_date,id_times,times)
				callback.call(this, cid,id_cid,id_fiml,id_date,times);
			}
		}).on('error', function(e){
	    		console.log(e)
	  		}).end();
	},
	setCookieString: function(booking_schema,cinemaCode,movieCode,playDate,playTime){
	  var str = "";
	  str = "booking=schema=" +booking_schema + "&cinemaCode=" + cinemaCode + "&movieCode=" + movieCode + "&playDate="+ playDate + "&playTime="+ playTime + "&seatParent=1&searChild=&listSeat=";
	  return str;
	},
	show: function()
	{
		FilmScheduleDB.showCinemasFromDb();
	},
	 requestDataSchedule: function()
  	{
	    FilmSchedule.getFilmSchedule();//thêm zô
	    var rule = new schedule.RecurrenceRule();
	    rule.second = new schedule.Range(0, 59, 59);//cú 1 phút request lại 1 lần.
	    var scheduleJobRequest= schedule.scheduleJob(rule,function(){
	    FilmSchedule.getFilmSchedule();
  		});
  	}
}
module.exports = FilmSchedule;