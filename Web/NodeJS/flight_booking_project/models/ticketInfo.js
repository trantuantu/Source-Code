var connection = require('../connection');
var crypto = require('crypto'); 

function TicketInfo() {
    this.insertPassword = function(req, res) {
        connection.acquire(function(err, con) {
        	var salt = crypto.randomBytes(16).toString('hex');
    		var hash = crypto.pbkdf2Sync(req.pass, salt, 1000, 64).toString('hex');
            
            con.query('insert into ticket_review values(?, ?, ?)', [req.code, hash, salt], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    },
    this.verify = function(req, res) {
        connection.acquire(function(err, con) {
            con.query('select salt from ticket_review where code = ?', req.code, function(err, result) {
            if (err)
               console.log("Wrong booking code or password");
               if (result.length > 0)
               {
                    var hash = crypto.pbkdf2Sync(req.pass, result[0]['salt'], 1000, 64).toString('hex');
                    var res1, res2, res3, res4, res5;
                    con.query('select hash from ticket_review where code = ?', req.code, function(err, result) {
                    
                        if (err)
                        console.log(err);
                        if (hash == result[0]['hash'])
                        {
                            con.query('select ctcb.MaDatCho, ctcb.MaChuyenBay, ctcb.Ngay, ctcb.Hang, ctcb.MucGia, ctcb.GiaBan, hl.SoLuong, hl.DonGia, cb.Gio, cb.ThoiGianBay, dv.TongTien from chi_tiet_chuyen_bay as ctcb left join hanh_ly as hl on hl.MaHanhLy = ctcb.MaHanhLy join chuyen_bay as cb on ctcb.MaChuyenBay = cb.Ma and ctcb.Ngay = cb.Ngay join dat_ve dv on ctcb.MaDatCho = dv.Ma where ctcb.MaDatCho = ?', req.code, function(err, result) {
                                res1 = result;
                                con.query('select DanhXung, Ho, Ten from hanh_khach where MaDatCho = ?', req.code, function(err, result) {
                                   res2 = result; 
                                    con.query('select MaGhe, SoGhe from ghe where MaDatCho = ?', req.code, function(err, result) {
                                         res3 = result;
                                         con.query('SELECT TenDiaDanh FROM san_bay as sb where sb.Ma in (SELECT tt.NoiDi FROM thong_tin_chuyen_bay as tt WHERE tt.Ma = ?)', res1[0].MaChuyenBay, function(err, result) {
                                            res4 = result;
                                                con.query('SELECT TenDiaDanh FROM san_bay as sb where sb.Ma in (SELECT tt.NoiDen FROM thong_tin_chuyen_bay as tt WHERE tt.Ma = ?)', res1[0].MaChuyenBay, function(err, result) {
                                                    res5 = result;
                                                    res1.push(res2);
                                                    res1.push(res3);
                                                    res1.push(res4);
                                                    res1.push(res5);
                                                    con.release();
                                                    res.json(res1);
                                                });
                                         });
                                    });
                                });
                            });
                        } else 
                        {
                            var item = {};
                            item.message = 'Mã đặt vé hoặc mật khẩu không đúng';
                            res.json(item);
                        }
                    });
                }else
                {
                            var item = {};
                            item.message = 'Mã đặt vé hoặc mật khẩu không đúng';
                            res.json(item);
                }
            });
        });
    };
}
module.exports = new TicketInfo();