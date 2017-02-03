var express = require('express');
var router = express.Router();
var cinemas = require('../api/Cinemas');
router.get('/', function(req, res) {
  cinemas.getCinemas(res);
});
module.exports = router;
