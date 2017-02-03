var connection = require('../connection');

function Tickets() {
    this.getTickets = function(req, res) {
    connection.acquire(function(err, con) {
      console.log(req);
      //  console.log(req.date);
      con.query('select loai_ve.MaLoaiVe, Hang, MucGia, SoLuong, GiaBan, MaThongTin from chuyen_bay inner join loai_ve inner join thong_tin_ve on chuyen_bay.ThongTinVe = loai_ve.MaLoaiVe and loai_ve.MaThongTin = thong_tin_ve.MaVe where chuyen_bay.Ma = ? and chuyen_bay.Ngay = ?', [req.flight, req.date], function(err, result) {
        con.release();
        if (err) {console.log(err)};
        console.log("Get Tickets");
        //console.log(result);
        console.log(result);
        res.send(result);
      });
    });
  };

  this.addTicket = function(payload, res) {
    connection.acquire(function(err, con) {
      con.query('insert into thong_tin_ve values (?, ?, ?, ?)', [payload.infoCode, payload.priceLevel, payload.number, payload.price], function(err, result) {
        //con.release();
        if (err) {
          con.release();
          console.log(err);
          res.send({status: 1, message: 'Record created failed'});
        } else {
           con.query('insert into loai_ve values (?, ?, ?)', [payload.code, payload.ticketType, payload.infoCode], function(err, result) {
        //con.release();
        //if (err) console.log(err);
        /*if (err) {
          con.release();
          console.log(err);
          res.send({status: 1, message: 'Record c
          sreated failed'});
        } else {*/
         
            
            con.query('update chuyen_bay set ThongTinVe = ? where Ma = ? and Ngay = ?', [payload.code, payload.flightCode, payload.date], function(err, result) {
            con.release();
        if (err) {
           console.log(err);
          res.send({status: 1, message: 'Record created failed'});
        } else {
            res.send({status: 0, message: 'Record created successfully'});
          }
        });
          //}
        });
          //res.send({status: 0, message: 'Record created successfully'});
        }
      });
    });
  };
  this.updateTicket = function(payload, res) {
    connection.acquire(function(err, con) {
      con.query('update thong_tin_ve set MucGia = ?, SoLuong = ?, GiaBan = ? where MaVe = ? and MucGia = ?', [payload.priceLevel, payload.number, payload.price, payload.code, payload.oldPriceLevel], function(err, result) {
        con.release();
        if (err) {
          res.send({status: 1, message: 'Record created failed'});
          console.log(err);
        } else {
          res.send({status: 0, id: payload.code, message: 'Record created successfully'});
        }
      });
    });
  };
  this.updateTicketForeignKey = function(payload, res)
  { 
        connection.acquire(function(err, con) {
        con.query('update loai_ve set MaThongTin = null where MaLoaiVe = ?', [payload.newFlightCode + payload.date], function(err, result) {
        con.query('update thong_tin_ve set MaVe = ? where MaVe = ?', [payload.newFlightCode + payload.date + 'Y', payload.flight + payload.oldDate + 'Y'], function(err, result) {
       /* if (err) {
          res.send({status: 1, message: 'Record created failed 2'});
          console.log(err);
        } else {*/
          if (err) console.log(err + '1');
          con.query('update thong_tin_ve set MaVe = ? where MaVe = ?', [payload.newFlightCode + payload.date + 'C', payload.flight + payload.oldDate + 'C'], function(err, result) {
         /* if (err) {
            res.send({status: 1, message: 'Record created failed 3'});
            console.log(err);
          }else
          {*/
             if (err) console.log(err + '2');
            con.query('update loai_ve set MaThongTin = ? where MaLoaiVe = ? and Hang = ?', [payload.newFlightCode + payload.date + 'Y', payload.newFlightCode + payload.date, 'Y'], function(err, result) {
           /* if (err) {
            res.send({status: 1, message: 'Record created failed 4'});
            console.log(err);
         } else {*/
           if (err) console.log(err + '3');
          con.query('update loai_ve set MaThongTin = ? where MaLoaiVe = ? and Hang = ?', [payload.newFlightCode + payload.date + 'C', payload.newFlightCode + payload.date, 'C'], function(err, result) {
          /*if (err) {
            res.send({status: 1, message: 'Record created failed 5'});
            console.log(err);
         } else
          {*/
             con.release();
             if (err) console.log(err + '4');
             else res.send({status: 0, id: payload.code, message: 'Record created successfully'});
        });
        });
        });
        });
        });
        });
  };

  this.deleteTicket = function(payload, res) {
    console.log(payload);
    connection.acquire(function(err, con) {
      //Kiểm tra xem co bao nhieu phan tu con, neu nhieu hon 1 thi khong gan null nguoc lai xoa luon node cha
      con.query('select count(MaVe) from thong_tin_ve where MaVe = ?', payload.typeCode + payload.type, function(err, result) {
        if (result[0]['count(MaVe)'] > 1)
        {
          //Xóa bình thường
          con.query('update loai_ve set MaThongTin = null where MaThongTin = ?', [payload.typeCode + payload.type], function(err, result) {
           con.query('delete from thong_tin_ve where MaVe = ? and MucGia = ?', [payload.typeCode + payload.type, payload.priceLevel], function(err, result) {
             if (err) {
              console.log(err + 1);
                  res.send({status: 1, message: 'Record delete failed'});
                } else {
                   con.query('update loai_ve set MaThongTin = ? where MaLoaiVe = ? and Hang = ?', [payload.typeCode + payload.type, payload.typeCode, payload.type], function(err, result) {
                    con.release();
                   if (err) {
                     console.log(err + 2);
                  res.send({status: 1, message: 'Record delete failed'});
                } else {
                  res.send({status: 0, message: 'Record delete successfully'});
                }
           });
          }
        });
      });
        }else
        {
           //Xóa luôn loại vé
            con.query('select count(MaLoaiVe) from loai_ve where MaLoaiVe = ?', payload.typeCode, function(err, result) {
              if (result[0]['count(MaLoaiVe)'] == 1)
              {
                console.log(11111);
                  con.query('update loai_ve set MaThongTin = null where MaThongTin = ?', [payload.typeCode + payload.type], function(err, result) {
                       con.query('delete from thong_tin_ve where MaVe = ? and MucGia = ?', [payload.typeCode + payload.type, payload.priceLevel], function(err, result) {
                         if (err) {
                          console.log(err + 1);
                              res.send({status: 1, message: 'Record delete failed'});
                            } else {
                               con.query('update chuyen_bay set ThongTinVe = null where ThongTinVe = ?', [payload.typeCode], function(err, result) {
                               con.query('delete from loai_ve where MaLoaiVe = ? and Hang = ?', [payload.typeCode, payload.type], function(err, result) {
                                //con.release();
                               if (err) {
                                       console.log(err + 2);
                                        res.send({status: 1, message: 'Record delete failed'});
                                      } else {
                                        res.send({status: 0, message: 'Record delete successfully'});
                                      }
                                   });
                               });
                             }
                           });
                     });
    
         }else{
          console.log(2222);
          //Nếu 2 loại vé, không cần xóa thông tin vé, vẫn xóa loại vé
           con.query('update loai_ve set MaThongTin = null where MaThongTin = ?', [payload.typeCode + payload.type], function(err, result) {
           con.query('delete from thong_tin_ve where MaVe = ? and MucGia = ?', [payload.typeCode + payload.type, payload.priceLevel], function(err, result) {
             if (err) {
              console.log(err + 1);
                  res.send({status: 1, message: 'Record delete failed'});
                } else {
                   con.query('update chuyen_bay set ThongTinVe = null where ThongTinVe = ?', [payload.typeCode], function(err, result) {
                   con.query('delete from loai_ve where MaLoaiVe = ? and Hang = ?', [payload.typeCode, payload.type], function(err, result) {
                   con.query('update chuyen_bay set ThongTinVe = ? where Ma = ? and Ngay = ?', [payload.typeCode, payload.flight, payload.date], function(err, result) {
                    //con.release();
                   if (err) {
                           console.log(err + 2);
                            res.send({status: 1, message: 'Record delete failed'});
                          } else {
                            res.send({status: 0, message: 'Record delete successfully'});
                          }
                       });
                   });
                });
          }
          });
          });
          }
          });
          }
          });
          });
          };
          
    this.getTicketsInfo = function(id, res) {
      connection.acquire(function(err, con) {
          con.query('select * from thong_tin_ve where MaLoaiVe = ?', id, function(err, result) {
              con.release();
              res.send(result);
          });
      });
  };
    this.addTicketInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('insert into thong_tin_ve set ?', payload, function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record created failed' });
                } else {
                    res.send({ status: 0, message: 'Record created successfully' });
                }
            });
        });
    };
    this.updateTicketInfo = function(payload, res) {
        connection.acquire(function(err, con) {
          console.log(payload);
            con.query('update thong_tin_ve set ? where MaVe = ? and MucGia = ?', [payload, payload.MaVe, payload.MucGia], function(err, result) {
                con.release();
                console.log(err);
                if (err) {
                    res.send({ status: 1, message: 'Record created failed' });
                } else {
                    res.send({ status: 0, message: 'Record created successfully' });
                }
            });
        });
    };

    this.getSoLuongDatVe = function(res) {
        connection.acquire(function(err, con) {
            con.query('select count(Ma) as counter from dat_ve', function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.deteleTicketInfo = function(payload, res) {
        connection.acquire(function(err, con) {
            con.query('delete from dat_ve where Ma = ?', payload.Ma, function(err, result) {
                con.release();
                if (err) {
                    res.send({ status: 1, message: 'Record updated failed' });
                } else {
                    res.send({ status: 0, message: 'Record updated successfully' });
                }
            });
        });
    };

    this.getTicketsInFlight = function(flight, date, res) {
        connection.acquire(function(err, con) {
            con.query('select dat_ve.Ma, dat_ve.ThoiGian, dat_ve.TongTien, dat_ve.TrangThai from chi_tiet_chuyen_bay inner join dat_ve on chi_tiet_chuyen_bay.MaDatCho = dat_ve.Ma where chi_tiet_chuyen_bay.MaChuyenBay = ? and chi_tiet_chuyen_bay.Ngay = ?', [flight, date], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
}

module.exports = new Tickets();
