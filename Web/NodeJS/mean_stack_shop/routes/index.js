var express = require('express');
var router = express.Router();

var auction = require('../controllers/api/auction');
var banner = require('../controllers/api/banner');
var contact = require('../controllers/api/contact');
var intruction = require('../controllers/api/intruction');
var productgroup = require('../controllers/api/productgroup');
var productimage = require('../controllers/api/productimage');
var passport = require('passport');
var jwt = require('express-jwt');
var mongoose = require('mongoose');
var router = express.Router();
var User = mongoose.model('User');
var product = require('../controllers/api/product');
var producttype = require('../controllers/api/producttype');
var user = require('../controllers/api/user');
var review = require('../controllers/api/review');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/api/products', product.getAll);
router.get('/api/product', product.create);
router.get('/api/product/type', product.readType);
router.route('/api/product/:id')
      .get(product.read)
      .put(product.update)
      .delete(product.delete);

router.get('/api/auctions', auction.getAll);
router.get('/api/auction',auction.create);
router.route('/api/auction/:id')
      .get(auction.read)
      .put(auction.update)
      .delete(auction.delete);

router.get('/api/banners', banner.getAll);
router.get('/api/banner',banner.create);
router.route('/api/banner/:id')
      .get(banner.read)
      .put(banner.update)
      .delete(banner.delete);

router.get('/api/contacts', contact.getAll);
router.get('/api/contact',contact.create);
router.route('/api/contact/:id')
      .get(contact.read)
      .put(contact.update)
      .delete(contact.delete);

router.get('/api/intructions', intruction.getAll);
router.get('/api/intruction',intruction.create);
router.route('/api/intruction/:id')
      .get(intruction.read)
      .put(intruction.update)
      .delete(intruction.delete);

router.get('/api/productgroups', productgroup.getAll);
router.get('/api/productgroup',productgroup.create);
router.route('/api/productgroup/:id')
      .get(productgroup.read)
      .put(productgroup.update)
      .delete(productgroup.delete);

router.get('/api/productimages', productimage.getAll);
router.get('/api/productimage',productimage.create);
router.route('/api/productimage/:id')
      .get(productimage.read)
      .put(productimage.update)
      .delete(productimage.delete);
router.route('/api/getproductimages/:id')
      .get(productimage.readByProduct);


router.get('/api/producttypes', producttype.getAll);
router.get('/api/producttype',producttype.create);
router.route('/api/producttype/:id')
      .get(producttype.read)
      .put(producttype.update)
      .delete(producttype.delete);

router.get('/api/users', user.getAll);
router.get('/api/user',user.create);
router.route('/api/user/:id')
      .get(user.read)
      .put(user.update)
      .delete(user.delete);

router.get('/api/reviews', review.getAll);
router.post('/api/review',review.create);
router.route('/api/review/:id')
      .get(review.read)
      .put(review.update)
      .delete(review.delete);
router.route('/api/getproductreviews/:id')
      .get(review.readByProduct);

//getproductreviews

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});


//Account router
 router.post('/account', function(req, res, next){
   if(!req.body.username || !req.body.password){
     return res.status(400).json({message: 'Please fill out all fields'});
   }
  //Get info in http request (req) (see on angularApp.js)
  var user = new User();

  user.username = req.body.username;
  user.name = req.body.name;
  user.bankid = req.body.bankid;

  user.setPassword(req.body.password)
  //Save user to mongodb
  user.save(function (err){
    if(err){ return next(err); }
    //Return json token for authentication.register (see on angularApp.js)
    return res.json({token: user.generateJWT()})
  });
});

router.post('/login', function(req, res, next){
  if(!req.body.username || !req.body.password){
    return res.status(400).json({message: 'Please fill out all fields'});
  }

console.log('calling passport)');
  passport.authenticate('local', function(err, user, info){
    if(err){ return next(err); }

    if(user){
      return res.json({token: user.generateJWT()});
    } else {
      return res.status(401).json(info);
    }
  })(req, res, next);
});


module.exports = router;