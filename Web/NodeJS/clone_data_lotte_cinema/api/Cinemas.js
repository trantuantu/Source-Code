var Cinemas = require('../models/cinema');
var cinema = {
  insertCinemas: function(listCinemas)
  {
        var req = [];
        for(var i = 0 ; i < listCinemas.length; i++){
           var temt = [];
           temt['city'] = listCinemas[i][0];
           temt['cinema'] = "";
          for( var j = 1 ; j < listCinemas[i].length; j ++){
             console.log(listCinemas[i][j]);
                if(j == listCinemas[i].length - 1)
                   temt['cinema'] += listCinemas[i][j];
                 else
                 {
                   temt['cinema'] += listCinemas[i][j] + '-';
                 }
          }
        req = temt;
        var pduct = new Cinemas(req);
            pduct.save(function(err){
            if(err)
              console.log(err);
            })
            console.log(req);
        }
  },
  showCinemas: function()
  {
      Cinemas.find(function(err,data){
                if(err) console.error;
                console.log(data);
              }) 
  },
  getCinemas: function(res)
  {
      Cinemas.find(function(err,data){
                if(err) console.error;
                res.send(data);
              }) 
  }
}
module.exports = cinema;