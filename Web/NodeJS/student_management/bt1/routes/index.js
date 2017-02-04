var express = require('express');
var router = express.Router();
var students = require('../controllers/api/students');
router.get('/api/students', students.getAll);
router.route('/api/students/:id')
	  .delete(students.delete);
router.put('/api/students', students.update);
router.post('/api/students', students.insert);
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;