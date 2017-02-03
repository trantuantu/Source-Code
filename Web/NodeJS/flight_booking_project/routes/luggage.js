var express = require('express');
var router = express.Router();
var luggage = require('../models/luggage');
var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

router.get('/', auth, function(req, res) {
  luggage.getLuggage(req.query, res);
});

router.post('/', function(req, res) {
  luggage.addLuggage(req.body, res);
});

module.exports = router;