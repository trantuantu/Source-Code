var connection = require('../connection');

function Prices() {
    this.getPrices = function(req, res) {
        console.log(req)
        connection.acquire(function(err, con) {
            if (err) throw err;
            con.query('select distinct chuyen_bay.Ngay, thong_tin_ve.GiaBan  AS SmallestTicketsPrice from chuyen_bay inner join thong_tin_chuyen_bay on chuyen_bay.Ma = thong_tin_chuyen_bay.Ma inner join loai_ve on chuyen_bay.ThongTinVe = loai_ve.MaLoaiVe inner join thong_tin_ve on loai_ve.MaThongTin = thong_tin_ve.MaVe where thong_tin_chuyen_bay.NoiDi = ? and thong_tin_chuyen_bay.NoiDen = ? and STR_TO_DATE(chuyen_bay.Ngay, \'%d-%m-%Y\') >= STR_TO_DATE(?, \'%d-%m-%Y\') and STR_TO_DATE(chuyen_bay.Ngay, \'%d-%m-%Y\') <= STR_TO_DATE(?, \'%d-%m-%Y\') and thong_tin_ve.SoLuong >= ?', [req.from, req.to, req.datefrom, req.dateto, req.amount], function(err, result) {
                con.release();
                res.send(result);
            });
        });
    };
}

module.exports = new Prices();
