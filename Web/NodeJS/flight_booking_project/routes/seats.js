var express = require('express');
var router = express.Router();
var seats = require('../models/seats');
var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

// router.get('/', auth, function(req, res) {
//   seats.getSeat(req.query, res);
// });
router.get('/seatInfo', auth, function(req, res) {
  seats.getSeatInfo(req.query, res);
});
router.get('/listSeat', auth, function(req, res) {
  seats.getSeatByBookingCode(req.query, res);
});
router.post('/', function(req, res) {
  seats.insertSeat(req.body,res);
});

router.get('/', function(req, res) {
  seats.getSeat(req.query,res);
});

module.exports = router;
