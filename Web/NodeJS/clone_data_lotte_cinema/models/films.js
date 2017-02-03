var mongoose = require('mongoose');

var FilmSchema = new mongoose.Schema({
	title: String,
	type: String,
	duration: String,
	actors: String,
	director: String,
	release_date: String
});
var Films = mongoose.model('Film', FilmSchema);
module.exports = Films;