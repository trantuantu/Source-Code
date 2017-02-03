var mysql = require('mysql');

function Connection() {
  this.pool = null;

  this.init = function() {
    this.pool = mysql.createPool({
      connectionLimit: 10,
      host: 'us-cdbr-iron-east-04.cleardb.net',
      user: 'b910cc01cd6a32',
      password: '4e9839e3',
      database: 'heroku_2bc32d1ba97c635'
    });
  };

  this.acquire = function(callback) {
    this.pool.getConnection(function(err, connection) {
      callback(err, connection);
    });
  };
}

module.exports = new Connection();