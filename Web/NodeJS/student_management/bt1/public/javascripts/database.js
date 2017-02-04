var mysql = require('mysql');
var database =  {
    conn : null,
    getConnection : function () {
       if (this.conn == null)
       {
       	//Change the database parameters (if need) to connect
	       	this.conn = mysql.createConnection({
	  		host: "us-cdbr-iron-east-04.cleardb.net",
	  		user: "b6dace4b6c1296",
	  		password: "03a580b6",
	  		database: "heroku_366abcf4d70cca7",
	  		multipleStatements: true
	  });
		  this.conn.connect(function(err){
		  if(err){
		    console.log('Can not connect to database. Please check mysql service is on');
		    return;
		  }
		  	console.log('Connected to database');
	  });

	  	return this.conn;
	   }else return this.conn;
	}
}
module.exports = database;