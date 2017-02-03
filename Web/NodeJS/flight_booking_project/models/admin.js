 var connection = require('../connection');
var crypto = require('crypto');
var jwt = require('jsonwebtoken');

var Admin = {
  username : "",
  generateJWT : function() {
     // set expiration to 60 days
    var today = new Date();
    var exp = new Date(today);
    exp.setDate(today.getDate() + 60);
    return jwt.sign({
      username: Admin.username,
      exp: parseInt(exp.getTime() / 1000),
    }, 'SECRET');
  },
 setPassword : function(oldUsername, username, password, res) {
    Admin.username = username;
   
    console.log(password);
    var salt = crypto.randomBytes(16).toString('hex');
      console.log(salt);
    var hash = crypto.pbkdf2Sync(password, salt, 1000, 64).toString('hex');

    connection.acquire(function(err, con) {
          con.query('update admin set username = ?, hash = ?, salt = ? where username = ?', [username, hash, salt, oldUsername], function(err, result) {
            con.release();
            if (err)
              console.log(err);
            res.json({token: Admin.generateJWT()})
          });
        });
  },
  validPassword : function(username, password, done) {
     connection.acquire(function(err, con) {
          con.query('select salt from admin where username = ?', username, function(err, result) {
            //con.release();
          if (err)
              console.log(err);
           // console.log(result['salt']);
            var hash = crypto.pbkdf2Sync(password, result[0]['salt'], 1000, 64).toString('hex');
            con.query('select hash from admin where username = ?', username, function(err, result) {
              con.release();
              if (err)
                console.log(err);
               if (hash == result[0]['hash'])
               {
                  Admin.username = username;
                  return done(null, result);
               } else return done(null, false, { message: 'Sai mật khẩu' }); 
            });
          });
        });
  },
  findUser : function(username, password, done)
  {
     connection.acquire(function(err, con) {
          con.query('select * from admin where username = ?', username, function(err, result) {
            con.release();
            if (err) { return done(err); }
            if (typeof result[0] == 'undefined') {
              return done(null, false, { message: 'Sai tên đăng nhập' });
            }
            Admin.validPassword(username, password, done);
          });
        });
  }
}

module.exports = Admin;