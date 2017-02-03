//Temp API
var connection = require('../connection');

function Seat() {
    this.getSeat = function(payload, res) {
        connection.acquire(function(err, con) {
            if (err) throw err;
            con.query('select SoGhe from Ghe where MaGhe = ?', [payload.maghe], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getSeatByBookingCode = function(payload, res) {
        connection.acquire(function(err, con) {
            if (err) throw err;
            con.query('select SoGhe from Ghe where MaGhe = ? and MaDatCho = ?', [payload.seatCode, payload.bookingCode], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.insertSeat = function(payload, res) {
        connection.acquire(function(err, con) {
            if (err) throw err;
            con.query('INSERT INTO ghe VALUES (?, ?, ?)', [payload.MaGhe, payload.MaDatCho, payload.SoGhe], function(err, result) {
                 con.query('UPDATE Chi_Tiet_Chuyen_Bay SET MaGhe = ? WHERE MaDatCho = ? and MaChuyenBay = ? and Ngay = ?', [payload.MaGhe, payload.MaDatCho, payload.MaChuyenBay, payload.Ngay], function(err, result) {
                    con.release();
                    res.send(result);
                 });
            });
        });
    };
    // this.insertSeat = function(payload, res) {
    //     connection.acquire(function(err, con) {
    //         payload.ThongTinVe = null;
    //         con.query('INSERT INTO ghe VALUES (?, ?, ?)', [payload.MaGhe, payload.SoGhe, payload.MaDatCho], function(err, result) {
    //             if (err) {
    //                 con.release();
    //                 console.log(err);
    //                 res.send({ status: 1, message: 'Record creation failed' });
    //             } else {
    //                 console.log("successfully");
    //                 res.send({ status: 0, message: 'Record creation successfully' });
    //             }
    //         });
    //     });
    // };
     this.getSeatInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            if (err) throw err;
            con.query('select * from ghe where MaGhe = ?', [payload.flight], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
}

module.exports = new Seat();