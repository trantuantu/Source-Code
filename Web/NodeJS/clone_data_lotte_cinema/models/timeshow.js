var mongoose = require('mongoose');

var TimeShowSchema = new mongoose.Schema({
	id_cinema: String,
	id_film: String,
	id_date:String,
	id_time: String,
	name: String
});

var TimeShows = mongoose.model('TimeShow', TimeShowSchema);
module.exports = TimeShows;