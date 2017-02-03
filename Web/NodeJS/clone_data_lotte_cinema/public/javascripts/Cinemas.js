var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var cheerio = require('cheerio');
var nString = require('./NString');
var CinemaDB = require('../../api/Cinemas')
var Cinemas = {
    xhr: new XMLHttpRequest(),
    cities:[],
    getCinemas: function()
    {
        this.xhr.onreadystatechange = function() {
        var cinemas = [];
        if (this.readyState == 4 && this.status == 200) {
        $ = cheerio.load(this.responseText);
        for (var i = 0; i < $('.dn_sidesubp2 a.icon_partion_parent').length; i++)
        {
          var city = [];
          city = $('.dn_sidesubp2 a.icon_partion_parent').eq(i).text();
          Cinemas.cities.push(nString.normalDate(city));
        }
        var group = $('.dn_sidesubp2 div.dn_subside ul');
        var count = 0;
        for (var k = 0; k < $('.dn_sidesubp2 div.dn_subside ul').length; k++)
        {
            var cinema = [];
            cinema.push(Cinemas.cities[k]);
            for (var j = 0; j < $('.dn_sidesubp2 div.dn_subside ul').eq(k).children().length; j++)
            {
              cinema.push(nString.normalDate($('.dn_sidesubp2 div.dn_subside li').eq(count).text()));       
              count++;
            }
            cinemas.push(cinema);
        }
        CinemaDB.insertCinemas(cinemas);
        //CinemaDB.showCinemas();
      }
    }
      this.xhr.open("GET", "http://lottecinemavn.com/vi-vn/rap-phim/ho-chi-minh/nam-sai-gon.aspx#partion2", true);
      this.xhr.send(); 
  }
}
module.exports = Cinemas;