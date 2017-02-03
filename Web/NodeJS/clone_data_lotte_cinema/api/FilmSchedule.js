var FilmShow = require('../models/filmshow');
var DateShow = require('../models/DateShow');
var TimeShow = require('../models/TimeShow');
var CinemaShow = require('../models/CinemaShow');


var filmschedule = {
  findInsertAndUpdateCinema:function(cinema,id_cinema){
  CinemaShow.findOne({'id': id_cinema},function(err, data) {
    if(data == null){
      var req = [];
      //console.log(i);
      req['name'] = cinema;
      req['id'] = id_cinema;
      console.log(cinema);
      console.log(id_cinema);
      
      var pduct = new CinemaShow(req);
        pduct.save(function(err){
          if(err)
              console.log(err);
        })
    }
    else{
      if(data.name != cinema){
        CinemaShow.findOneAndUpdate({'id': id_cinema }, {$set:{name: cinema}}, {new: true}, function(err, doc) {
          if(!err)
            console.log("update:"+" id_cinema: " + id_cinema);
        });
      }

    }
  });
},
findInsertAndUpdateFilm:function(id_cinema,id_film,fiml){
  FilmShow.findOne({'id_cinema': id_cinema,'id_film':id_film},function(err, data) {
    if(data == null){
      var req = [];
      req['id_cinema'] = id_cinema;
      req['id_film'] = id_film;
      req['name'] = fiml;
      var pduct = new FilmShow(req);
      pduct.save(function(err){
        if(err)
            console.log(err);
      })
    }
    else{
      if(data.name != fiml){
        FilmShow.findOneAndUpdate({'id_cinema': id_cinema,'id_film':id_film}, {$set:{name: fiml}}, {new: true}, function(err, doc) {
          if(!err)
            console.log("update:"+" id_cinema: " + id_cinema + " id_film: " +  id_film );
        });
      }
    }
  });
},

findInsertAndUpdateDate:function(id_cinema,id_film,id_date,date){
  DateShow.findOne({'id_cinema': id_cinema,'id_film':id_film,'id_date': id_date},function(err, data) {
    if(data == null){
      var req = [];
      req['id_cinema'] = id_cinema;
      req['id_film'] = id_film;
      req['id_date'] = id_date;
      req['name'] = date;
      var pduct = new DateShow(req);

      pduct.save(function(err){
          if(err)
              console.log(err);
      })
    }
    else{
      if(data.name != date){
        DateShow.findOneAndUpdate({'id_cinema': id_cinema,'id_film':id_film,'id_date': id_date}, {$set:{name: date}}, {new: true}, function(err, doc) {
          if(!err)
            console.log("update:"+" id_cinema: " + id_cinema + " id_film: " +  id_film + " id_date: " + id_date );
        });
      }
    }
  });
},
findInsertAndUpdateTime:function(id_cinema,id_film,id_date,id_time,time){
  TimeShow.findOne({'id_cinema': id_cinema,'id_film':id_film,'id_date': id_date,'id_time': id_time},function(err, data) {
    if(data == null){
      var req = [];
      req['id_cinema'] = id_cinema;
      req['id_film'] = id_film;
      req['id_date'] = id_date;
      req['id_time'] = id_time;
      req['name'] = time;
      var pduct = new TimeShow(req);

      pduct.save(function(err){
          if(err)
              console.log(err);
      })
    }
    else{
      if(data.name != time){
        TimeShow.findOneAndUpdate({'id_cinema': id_cinema,'id_film':id_film,'id_date': id_date}, {$set:{name: time}}, {new: true}, function(err, doc) {
          if(!err)
            console.log("update:"+" id_cinema: " + id_cinema + " id_film: " +  id_film + " id_date: " + id_date + " id_time: " + id_time);
        });
      }
    }
  });
},
    insertFimlsIntoDb: function(id_cinema,id_films,fimls){
   for(var i in fimls){
    filmschedule.findInsertAndUpdateFilm(id_cinema,id_films[i],fimls[i]);
  }
  },
  insertDatesIntoDb: function(id_cinema,id_film,id_dates,dates){
   for(var i in dates){
    filmschedule.findInsertAndUpdateDate(id_cinema,id_film,id_dates[i],dates[i])
  }
       
  },
  insertTimesIntoDb: function(id_cinema,id_film,id_date,id_time,times){
    for(var i in times){
    filmschedule.findInsertAndUpdateTime(id_cinema,id_film,id_date,id_time[i],times[i]);
    }
  },
  getFilmSchedule: function(query, res)
  {
    CinemaShow.findOne({'name': query.cinema},
    function(err, data){
      var idCinema = data['id'];
      console.log(idCinema);
      //console.log(query.date);
      DateShow.find(
      {'id_date': query.date, 'id_cinema': idCinema},
      {'id_film': 1}
      ,function(err,data){
        var filmIds = [];
        console.log(data);
        for (var i = 0; i < data.length; i++)
          filmIds.push(data[i]['id_film']);
         console.log(filmIds);

          FilmShow.find( {id_film: { $in: filmIds},'id_cinema': idCinema}, {'name' : 1}, function(err, d)
          {
              console.log(d);
              res.send(d);
          })
      })
    })    //if(err)  return handleError(err);
     //res.send(data);
    //console.log(data);
  },
   getCinemaSchedule: function(query, res)
  {
    console.log(query.film);
      FilmShow.findOne({'name': query.film}, {'id_film' : 1},
      function(err, data){
      var idFilm = data['id_film'];
       console.log(idFilm);
      //console.log(data);
      DateShow.find(
      {'id_date': query.date, 'id_film': idFilm},
      {'id_cinema': 1}
      ,function(err,data){
        var cinemaIds = [];
        for (var i = 0; i < data.length; i++)
        cinemaIds.push(data[i]['id_cinema']);
         console.log(cinemaIds);
          CinemaShow.find( {id: { $in: cinemaIds}}, {'name' : 1}, function(err, d)
          {
              console.log(d);
              res.send(d);
          })
      })
    })    //if(err)  return handleError(err);
     //res.send(data);
    //console.log(data);
  },
  insertCinemasIntoDb: function(cinemas,id_cinemas){
      for(var i = 0 ; i < cinemas.length; i++){
        filmschedule.findInsertAndUpdateCinema(cinemas[i],id_cinemas[i]);
    }
  },
  showCinemasFromDb: function(){
    console.log("hdd");
    DateShow.find(function(err,data){
        if(err) console.error;
          //res.json(data);
          console.log(data);
          console.log(data.length);
      })
  }
}
module.exports = filmschedule;