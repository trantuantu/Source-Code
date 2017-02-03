var express = require('express');
var router = express.Router();
var films = require('../api/Films');
router.get('/', function(req, res) {
  films.getFilm(res);
});
module.exports = router;