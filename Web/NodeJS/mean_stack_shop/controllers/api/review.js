var Review = require('../../models/Reviews');

var review = {
	read: function(req, res, next){
		res.json({type: "Read", id: req.params.id});
	},
	create: function(req, res, next){
		var body = req.body;
		console.log(body);
	    // Review.save(body, function(err, data){
	    //     res.json(data);
	    // });

	    item = new Review(body);
		item.save(function(err){
		if(err)
			console.log(err);
		})
	},
	update: function(req, res, next){
		res.json({type: "Update", id: req.params.id, body: req.body});	
	},
	delete: function(req, res, next){
		res.json({type: "Delete", id: req.params.id});
	},
	getAll: function(req, res, next){
		Review.find(function(err,data){
			if(err) console.error;
			res.json(data);
		})
	},
	readByProduct: function(req, res, next){
		Review.find({'productid' : req.params.id},function(err,data){
			if(err) console.error;
			res.json(data);
		})
	}
}

module.exports = review;