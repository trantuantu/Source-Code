var mongoose = require('mongoose');

var CinemaSchema = new mongoose.Schema({
	city: String,
	cinema: String
});
var Cinemas = mongoose.model('Cinema', CinemaSchema);

module.exports = Cinemas;