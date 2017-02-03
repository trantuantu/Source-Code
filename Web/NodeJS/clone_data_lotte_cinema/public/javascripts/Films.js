var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var cheerio = require('cheerio');
var nString = require('./NString');
var FilmDB = require('../../api/Films');
var Films = {
  xhr: new XMLHttpRequest(),
  getFilms: function()
  {
      this.xhr.onreadystatechange = function() {
      var films = [];
      if (this.readyState == 4 && this.status == 200) {
      $ = cheerio.load(this.responseText);
      $('.dn_lmvinfo a').attr('href');
      for (var i = 0; i < $('.dn_lmvinfo a').length; i++)
      {
        var film = [];
        film['title'] = $('.dn_lmvinfo a').eq(i).attr('title');
        film['type'] = nString.normalString($('.dn_lmvinfo li span.dn_fonti11').eq(i * 5 + 0).text().replace('\r\n', ''));
        film['duration'] = nString.normalString($('.dn_lmvinfo li span.dn_fonti11').eq(i * 5 + 1).text().replace('\r\n', ''));
        film['actors'] = nString.normalString($('.dn_lmvinfo li span.dn_fonti11').eq(i * 5 + 2).text().replace('\r\n', ''));
        film['director'] = nString.normalString($('.dn_lmvinfo li span.dn_fonti11').eq(i * 5 + 3).text().replace('\r\n', ''));
        film['release_date'] = nString.normalDate($('.dn_lmoviesr .dn_lmoviesr2').eq(i).text());
        films.push(film);
      }
        FilmDB.insertFilms(films);
        //FilmDB.showFilms();
      }
      //console.log(films);
    }
    this.xhr.open("GET", "http://lottecinemavn.com/vi-vn/phim-%C4%91ang-chieu.aspx", true);
    this.xhr.send();
  }
}
module.exports = Films;