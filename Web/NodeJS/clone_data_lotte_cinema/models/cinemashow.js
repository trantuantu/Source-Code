var mongoose = require('mongoose');

var CinemaShowSchema = new mongoose.Schema({
	name: String,
	id: String
});

var CinemaShows = mongoose.model('CinemaShow', CinemaShowSchema);

module.exports = CinemaShows;