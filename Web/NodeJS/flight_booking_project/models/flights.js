var connection = require('../connection');

function Flights() {
    this.getFlights = function(res) {
        connection.acquire(function(err, con) {
            con.query('select * from chuyen_bay', function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
    this.addFlight = function(payload, res) {
        connection.acquire(function(err, con) {
            payload.ThongTinVe = null;
            con.query('insert into chuyen_bay set ?', payload, function(err, result) {
                if (err) {
                    con.release();
                    console.log(err);
                    res.send({ status: 1, message: 'Record creation failed' });
                } else {
                    console.log("successfully");
                    res.send({ status: 0, message: 'Record creation successfully' });
                }
            });
        });
    };
    this.searchFlights = function(query, res) {
        connection.acquire(function(err, con) {
            con.query('select distinct chuyen_bay.Ma, chuyen_bay.Ngay, chuyen_bay.Gio, chuyen_bay.ThoiGianBay, loai_ve.Hang, thong_tin_ve.MucGia, thong_tin_ve.GiaBan, thong_tin_ve.SoLuong, thong_tin_ve.MaVe from chuyen_bay inner join thong_tin_chuyen_bay on chuyen_bay.Ma = thong_tin_chuyen_bay.Ma inner join loai_ve on chuyen_bay.ThongTinVe = loai_ve.MaLoaiVe inner join thong_tin_ve on loai_ve.MaThongTin = thong_tin_ve.MaVe where thong_tin_chuyen_bay.NoiDi = ? and thong_tin_chuyen_bay.NoiDen = ? and STR_TO_DATE(chuyen_bay.Ngay, \'%d-%m-%Y\') = STR_TO_DATE(?, \'%d-%m-%Y\') and thong_tin_ve.SoLuong >= ?', [query.from, query.to, query.date, query.amount], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
    this.updateFlight = function(payload, res) {
        connection.acquire(function(err, con) {
            console.log(payload);
            con.query('UPDATE chuyen_bay SET Ma = ?, Ngay = ?, Gio = ?, ThoiGianBay = ?, ThongTinVe = ? WHERE Ma = ? and Ngay = ?', [payload.newFlightCode, payload.date, payload.time, payload.flightTime, null, payload.flightCode, payload.oldDate], function(err, result) {
                //con.release();
                if (err) {
                    console.log(err);
                    res.send({ status: 1, message: 'Record update failed' });
                } else {
                    con.query('UPDATE loai_ve SET MaLoaiVe = ? WHERE MaLoaiVe = ?', [payload.newFlightCode + payload.date, payload.flightCode + payload.oldDate], function(err, result) {
                        //con.release();
                        console.log(payload);
                        if (err) {
                            console.log(err);
                            res.send({ status: 1, message: 'Record update failed' });
                        } else {
                            con.query('UPDATE chuyen_bay SET ThongTinVe = ? WHERE Ma = ? and Ngay = ?', [payload.newFlightCode + payload.date, payload.newFlightCode, payload.date], function(err, result) {
                                con.release();
                                console.log(payload);
                                if (err) {
                                    console.log(err);
                                    res.send({ status: 1, message: 'Record update failed' });
                                } else {
                                    res.send({ status: 0, message: 'Record update successfully' });
                                }
                            })
                        }
                    });
                }
            });
        });
    };
    this.deleteFlight = function(payload, res) {
        connection.acquire(function(err, con) {
            //Delete all remaining tickets of the flight
            con.query('delete t, u, w from chuyen_bay t left join loai_ve w on t.ThongTinVe = w.MaLoaiVe left join thong_tin_ve u on w.MaThongTin = u.MaVe where t.Ma = ? and t.Ngay = ?', [payload.flight, payload.date], function(err, result) {
                con.release();
                if (err) {
                    console.log(err);
                    res.send({ status: 1, message: 'Record deletion failed' });
                } else {
                    /* con.query('delete t, w from hanh_khach t inner join chi_tiet_chuyen_bay w on t.MaDatCho = w.MaDatCho where w.MaChuyenBay = ? and w.Ngay = ?', [payload.flight, payload.date] , function(err, result) {     
                          con.release();
                          if (err) {
                           res.send({status: 1, message: 'Record deletion failed'});
                         } else {
                          res.send({status: 0, message: 'Record deletion successfully'});
                         }
                       });*/
                    res.send({ status: 0, message: 'Record deletion successfully' });

                }
            });
        });
    };

    this.getFlightsInfo = function(res) {
        connection.acquire(function(err, con) {
            con.query('select * from thong_tin_chuyen_bay', function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
    this.addFlightInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('insert into thong_tin_chuyen_bay set ?', payload, function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record update failed' });
                } else {
                    res.send({ status: 0, message: 'Record update successfully' });
                }
            });
        });
    };
    this.updateFlightInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('update thong_tin_chuyen_bay set Ma = ?, NoiDi = ?, NoiDen = ? where Ma = ?', [payload.newFlightCode, payload.start, payload.end, payload.flightCode], function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record update failed' });
                } else {
                    res.send({ status: 0, message: 'Record update successfully' });
                }
            });
        });
    };
    this.deleteFlightInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            console.log(payload.flight);
            con.query('delete from thong_tin_chuyen_bay where Ma = ?', payload.flight, function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record deletion failed' });
                } else {
                    res.send({ status: 0, message: 'Record deletion successfully' });
                }
            });
        });
    };

    this.addFlightDetail = function(payload, res) {
        console.log(payload);
        connection.acquire(function(err, con) {
            con.query('insert into chi_tiet_chuyen_bay set ?', payload, function(err, result) {
                con.release();
                console.log(err);
                if (err) {
                    res.send({ status: 1, message: 'Record update failed' });
                } else {
                    res.send({ status: 0, message: 'Record update successfully' });
                }
            });
        });
    };

    this.updateFlightDetail = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('UPDATE Chi_Tiet_Chuyen_Bay SET MaGhe = ? WHERE MaDatCho = ? and MaChuyenBay = ? and Ngay = ?', [payload.MaGhe, payload.MaDatCho, payload.MaChuyenBay, payload.Ngay], function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record updated failed' });
                } else {
                    res.send({ status: 0, message: 'Record updated successfully' });
                }
            });
        });
    };

    this.getFlightDetail = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('select * from chi_tiet_chuyen_bay where Ma = ?', payload, function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getLuggagePrice = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('select GiaHanhLy from chuyen_bay where Ma = ? and Ngay = ?', [payload.code, payload.date], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
}

module.exports = new Flights();
