var express = require('express');
var router = express.Router();
var flights = require('../models/flights');
var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/', auth, function(req, res) {
  flights.getFlights(res);
});

router.post('/', auth, function(req, res) {
	flights.addFlight(req.body, res);
})
router.get('/search', function(req, res) {
  flights.searchFlights(req.query,res);
});
router.put('/', auth, function(req, res) {
  flights.updateFlight(req.body,res);
});
router.delete('/', auth, function(req, res) {
  flights.deleteFlight(req.query,res);
});
router.get('/info', auth, function(req, res) {
  flights.getFlightsInfo(res);
});
router.post('/info', auth, function(req, res) {
  flights.addFlightInfo(req.body,res);
});
router.put('/info', auth, function(req, res) {
  flights.updateFlightInfo(req.body,res);
});
router.delete('/info', auth, function(req, res) {
  flights.deleteFlightInfo(req.query,res);
});
router.get('/detail/:id', function(req, res) {
  flights.getFlightDetail(req.params.id, res);
});
router.post('/detail', function(req, res) {
  flights.addFlightDetail(req.body, res);
});
router.get('/luggage', function(req, res) {
  flights.getLuggagePrice(req.query,res, res);
});

module.exports = router;