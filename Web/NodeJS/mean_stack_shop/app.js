//
// MAIN APP
//
var express   = require('express');
var totalViews = 0;
var onlineCount = 0;
var viewCount = 0;
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var app = express();
var mongoose = require('mongoose');
var passport = require('passport');

var socketio = require('socket.io');
var request = require('request');



/* require mongoose schema must before routes */
var mongoose = require('mongoose');

require('./models/ProductTypes');
require('./models/Users');
require('./config/passport');
require('./models/Banners');
require('./models/Intructions');
require('./models/ProductGroups');
require('./models/Products');
require('./models/ProductImages');
require('./models/Auctions');
var routes = require('./routes/index');
var users = require('./routes/users');

mongoose.connect("mongodb://localhost:27017/baymax");

 var db = mongoose.connection;
 
db.on("error", console.error.bind(console, "connection error"));
db.once("open", function(callback) {
  console.log("Connection succeeded.")
});


 //Authentication
var routes = require('./routes/index');
var users = require('./routes/users');
var app = express();

var server = require('http').createServer(app);
var io = socketio.listen(server);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, '/')));

app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/users', users);
app.use(passport.initialize());

var sess;

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


// app.post('/submit',function(req,res){
//   // g-recaptcha-response is the key that browser will generate upon form submit.
//   // if its blank or null means user has not selected the captcha, so return the error.
//   // if(req.body['g-recaptcha-response'] === undefined || req.body['g-recaptcha-response'] === '' || req.body['g-recaptcha-response'] === null) {
//   //   return res.json({"responseCode" : 1,"responseDesc" : "Please select captcha"});
//   // }
//   // Put your secret key here.
//   // var secretKey = "6LcX1v4SAAAAAD6gha77Uy7781vLwV1xkVhoejuQ";
//   // req.connection.remoteAddress will provide IP address of connected user.
//   var verificationUrl = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + req.body['g-recaptcha-response'] + "&remoteip=" + req.connection.remoteAddress;
//   // Hitting GET request to the URL, Google will respond with success or error scenario.
//   request(verificationUrl,function(error,response,body) {
//     body = JSON.parse(body);
//     // Success will be true or false depending upon captcha validation.
//     if(body.success !== undefined && !body.success) {
//       return res.json({"responseCode" : 1,"responseDesc" : "Failed captcha verification"});
//     }
//     return res.json({"responseCode" : 0,"responseDesc" : "Sucess"});
//   });
// });

io.sockets.on('connection', function(socket) {
  socket.on('new auction', function(data) {
    console.log('on: new auction');

    var newPrice = data.newPrice;
    var product = data.product;

    var t = Date.parse(product.expriretime) - Date.parse(new Date());
    t = 1;
    if (t < 0) {
      socket.emit('auction fail', {});
    }
    else {
      product.currentprice = newPrice;
      product.startingbid = newPrice;

      // update product in database
      console.log('emit: temp step');
      socket.emit('temp step', {updatedProduct: product});
    }
  });

  socket.on('temp step result', function(data) {
    console.log('on: temp step result');
    console.log('broadcast: auction success');

    io.sockets.emit('auction success', {});
  });
});

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers
server.listen(3000);
module.exports = app;
