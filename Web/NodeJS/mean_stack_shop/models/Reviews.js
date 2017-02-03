var mongoose = require('mongoose');

var ReviewSchema = new mongoose.Schema({
	id: String,
	name: String,
	time: Date,
	rating: String,
	content: String,
	productid: String
});

var Reviews = mongoose.model('Review', ReviewSchema);
module.exports = Reviews;
