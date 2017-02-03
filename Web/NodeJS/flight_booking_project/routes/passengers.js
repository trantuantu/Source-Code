var express = require('express');
var router = express.Router();
var passengers = require('../models/passengers');
var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/:flight/:date', function(req, res) {
  passengers.getPassengersFromFlight(req.params.flight, req.params.date, res);
});
router.put('/', function(req, res) {
  passengers.addPassengers(req.body, res);
});
router.get('/', auth, function(req, res) {
  passengers.getPassengersWithId(req.query, res);
});

module.exports = router;