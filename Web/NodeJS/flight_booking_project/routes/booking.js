var express = require('express');
var router = express.Router();
var booking = require('../models/booking');
var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/info', auth, function(req, res) {
  booking.getBookingInfo(req.query, res);
});
router.get('/infobyflight', auth, function(req, res) {
  booking.getBookingInfoByFlight(req.query, res);
});
router.get('/', auth, function(req, res) {
  booking.getBooking(res);
});
router.put('/', function(req, res) {
  booking.bookFlight(req.body, res);
});
router.put('/:id', function(req, res) {
  booking.confirmBooking(req.body, res);
});
router.delete('/', function(req, res) {
  booking.cancelBooking(req.body, res);
});

module.exports = router;