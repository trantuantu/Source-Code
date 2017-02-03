var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var admin = require('../models/admin');
 var connection = require('../connection');

passport.use(new LocalStrategy(
  function(username, password, done) {
    //Console.log("Hello ooooo");
    //console.log(username);
    //console.log(password);
    admin.findUser(username, password, done);

    /*connection.acquire(function(err, con) {
          con.query('select * from admin where username = ?', username, function(err, result) {
            con.release();
            if (err) { return done(err); }
            if (!result) {
              return done(null, false, { message: 'Sai tên đăng nhập' });
            }
            if (!admin.validPassword(password)) {
              return done(null, false, { message: 'Sai mật khẩu. Nếu là lần đầu đăng nhập, xin hãy đặt lại password cho tài khoản này' });
            }
            admin.username = username;
            return done(null, result);
          });
        });*/
 /*   User.findOne({ username: username }, function (err, user) {
      if (err) { return done(err); }
      if (!user) {
        return done(null, false, { message: 'Incorrect username.' });
      }
      if (!user.validPassword(password)) {
        return done(null, false, { message: 'Incorrect password.' });
      }
      return done(null, user);
    });*/
  }
));