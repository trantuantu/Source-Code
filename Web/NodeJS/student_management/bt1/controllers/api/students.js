var database = require('../../public/javascripts/database');
var conn = database.getConnection();
//--------------------------------------------------------------------------------------------------------
var students = {
  getAll: function (req, res, next){
    conn.query('SELECT * FROM students', function(err, rows){
      if(err)  res.status(500).send(err.message);
      else res.json(rows);
    });
  },
  insert: function(req, res, next){
    var request = req.body;
    var student = {id: request.id, name: request.name, birthday: request.birthday, gender: request.gender, address: request.address};
    conn.query('INSERT INTO students SET ?', student, function(err, rows){
    if(err)  res.status(500).send(err.message);
    else res.json(rows);
  });
  },
  update: function(req, res, next){
    var request = req.body;
    conn.query(
    'UPDATE students SET id = ?, name = ?, birthday = ?, gender = ?, address = ? WHERE id = ?',
     [request.uid,  request.name,  request.birthday, request.gender, request.address,  request.id],
    function (err, rows) {
      if(err)  res.status(500).send(err.message);
      else res.json(rows);
    });
  },
  delete: function(req, res, next){
    conn.query(
    'DELETE FROM students WHERE id = ?',
    [req.params.id],
    function (err, rows) {
      if(err)  res.status(500).send(err.message);
      else res.json(rows);
    });
  }
}
module.exports = students;