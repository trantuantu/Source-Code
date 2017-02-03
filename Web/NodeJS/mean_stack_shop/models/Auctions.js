var mongoose = require('mongoose');
var User = require('./Users');
var Product = require('./Products');

var AuctionSchema = new mongoose.Schema({
	user : Number,
	product: Number,
	price: Number,
	time: Date
});

var Auctions = mongoose.model('Auction', AuctionSchema);
module.exports = Auctions;