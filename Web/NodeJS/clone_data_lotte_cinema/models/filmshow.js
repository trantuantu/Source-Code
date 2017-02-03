var mongoose = require('mongoose');

var FimlShowSchema = new mongoose.Schema({
	id_cinema: String,
	id_film: String,
	name: String
});

var FilmShows = mongoose.model('FilmShow', FimlShowSchema);

module.exports = FilmShows;