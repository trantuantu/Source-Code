var Films = require('../models/films');
var film = {
  insertFilms: function(listFilms)
  {
      var req = [];
      for(var i = 0 ; i < listFilms.length; i++){
      req = null;
      req = listFilms[i];
      var pduct = new Films(req);
          pduct.save(function(err){
          if(err)
            console.log(err);
          })
      }
      console.log(listFilms);
  },

  showFilms: function()
  {
       Films.find(function(err,data){
              if(err) console.error;
              console.log(data);
            }) 
  },
  getFilm: function(res)
  {
      Films.find(function(err,data){
              if(err)  return handleError(err);
              res.send(data);
              console.log(res);
      }) 
  }
}
module.exports = film;