var mongoose = require('mongoose');

var DateShowSchema = new mongoose.Schema({
	id_cinema: String,
	id_film: String,
	id_date:String,
	name: String
});

var DateShows = mongoose.model('DateShow', DateShowSchema);
module.exports = DateShows;