var express = require('express');
var router = express.Router();
var passport = require('passport');
var jwt = require('express-jwt');
var admin = require('../models/admin');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/register', function(req, res, next){
  if(!req.body.username || !req.body.password || !req.body.nusername || !req.body.npassword || !req.body.cnpassword){
    return res.status(400).json({message: 'Xin hãy điền đầy đủ thông tin'});
  }
  if (req.body.cnpassword != req.body.npassword)  return res.status(400).json({message: 'Mật khẩu mới và xác nhận mật khẩu mới không trùng khớp'});
  //var user = new User();

  console.log('calling passport');
  passport.authenticate('local', function(err, user, info){
    if(err){ return next(err); }
    if(user){
      //return res.json({token: admin.generateJWT()});
      //console.log("Reset password");
      admin.setPassword(req.body.username, req.body.nusername, req.body.npassword, res);
    } else {
      return res.status(401).json(info);
    }
  })(req, res, next);
  //user.username = req.body.username;

  

  /*user.save(function (err){
    if(err){ return next(err); }

    return res.json({token: user.generateJWT()})
  });*/
});

router.post('/login', function(req, res, next){
  if(!req.body.username || !req.body.password){
    return res.status(400).json({message: 'Xin hãy điền đầy đủ thông tin'});
  }
console.log('calling passport');
  passport.authenticate('local', function(err, user, info){
    if(err){ return next(err); }
    if(user){
      return res.json({token: admin.generateJWT()});
    } else {
      return res.status(401).json(info);
    }
  })(req, res, next);
});

module.exports = router;
