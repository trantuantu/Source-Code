var express = require('express');
var router = express.Router();
var ticketInfo = require('../models/ticketInfo');

router.post('/', function(req, res) {
	ticketInfo.insertPassword(req.body, res);
});
router.post('/verify', function(req, res) {
	ticketInfo.verify(req.body, res);
});
module.exports = router;