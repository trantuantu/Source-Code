var express = require('express');
var router = express.Router();
var prices = require('../models/prices');

router.get('/', function(req, res) {
  prices.getPrices(req.query, res);
});

module.exports = router;