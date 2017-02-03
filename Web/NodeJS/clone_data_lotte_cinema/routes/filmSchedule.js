var express = require('express');
var router = express.Router();
var filmSchedule = require('../api/FilmSchedule');
router.get('/', function(req, res) {
  filmSchedule.getFilmSchedule(req.query, res);
});

router.get('/cinema', function(req, res) {
  filmSchedule.getCinemaSchedule(req.query, res);
});

module.exports = router;
