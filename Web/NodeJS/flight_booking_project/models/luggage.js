var connection = require('../connection');

function Luggage() {
  this.getLuggage = function(payload, res) {
    connection.acquire(function(err, con) {
      if (err) throw err;
      con.query('select * from Hanh_Ly where MaHanhLy = ?', [payload.luggageCode], function(err, result) {
        con.release();
        res.send(result);
      });
    });
  };

  this.addLuggage = function(payload, res) {
    connection.acquire(function(err, con) {
      if (err) throw err;
      con.query('INSERT INTO Hanh_Ly VALUES (?, ?, ?)', [payload.MaDatCho + payload.MaCB + payload.Ngay, payload.SoLuong, payload.DonGia], function(err, result) {
        con.query('UPDATE Chi_Tiet_Chuyen_Bay SET MaHanhLy = ? WHERE MaDatCho = ? and MaChuyenBay = ? and Ngay = ?', [payload.MaDatCho + payload.MaCB + payload.Ngay, payload.MaDatCho, payload.MaCB, payload.Ngay], function(err, result) {
        	con.release();
        res.send(result);
        });
      });
    });
  };
}

module.exports = new Luggage();