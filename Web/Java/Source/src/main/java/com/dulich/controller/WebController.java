package com.dulich.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DanhSachNhaHangKhachSan;
import com.dulich.model.pojo.DiaDiemDuLich;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.KhachHang;
import com.dulich.model.pojo.PhieuDangKi;
import com.dulich.model.pojo.Register;
import com.dulich.model.pojo.Restaurant;
import com.dulich.model.pojo.Tour;
import com.dulich.validator.RegisterValidator;
import com.dulich.model.dao.DanhSachDiaDiemDAO;
import com.dulich.model.dao.DanhSachNhaHangKhachSanDAO;
import com.dulich.model.dao.DiaDiemDuLichDAO;
import com.dulich.model.dao.HotelDAO;
import com.dulich.model.dao.KhachHangDAO;
import com.dulich.model.dao.PhieuDangKiDAO;
import com.dulich.model.dao.RestaurantDAO;
import com.dulich.model.dao.TourDAO;


@Controller
public class WebController {
	 @Autowired
	 private TourDAO tourDAO;
	 
	 @Autowired
	 private DanhSachDiaDiemDAO danhSachDiaDiemDAO;
	 
	 @Autowired
	 private DiaDiemDuLichDAO diaDiemDuLichDAO;

	 @Autowired
	 private PhieuDangKiDAO phieuDangKiDAO;
	 
	 @Autowired
	 private KhachHangDAO khachHangDAO;
	 
	 @Autowired
     private DanhSachNhaHangKhachSanDAO dsDanhSachNhaHangKhachSanDAO;
	 
	 @Autowired
	 private RestaurantDAO restaurantDAO;
	 
	 @Autowired
	 private HotelDAO hotelDAO;
	 
	 @Autowired
	 private RegisterValidator registerValidator;
	 
	 
	 @RequestMapping(value = {"/web/index" }, method = RequestMethod.GET)	
	 public String Index(Model model) throws ParseException {
		 List<Tour> tours = tourDAO.getListTour_Web();
		 
		 
		 List<Tour> toursRD = new ArrayList();
		 for (int i = 0; i < Math.min(5, tours.size()); i++) {
			 int idx = new Random().nextInt(tours.size());
			 tours.get(idx).setCo(daysBetween(tours.get(idx).getNgay_di(), tours.get(idx).getNgay_ve()));
			 toursRD.add(tours.get(idx));
		 }
		 
		 model.addAttribute("toursRD", toursRD);
		 
		 List<Tour> toursShow = new ArrayList();
		 for (int i = tours.size() - 1; i >= 0; i--) {
			 tours.get(i).setCo(daysBetween(tours.get(i).getNgay_di(), tours.get(i).getNgay_ve()));
			 toursShow.add(tours.get(i));
			 if (toursShow.size() == 3) 
				 break;
		 }
		 model.addAttribute("toursShow", toursShow);
		 
	     return "user/index";
	 }
	 
	 private int daysBetween(String d1, String d2) throws ParseException{
		 Calendar cal1 = new GregorianCalendar();
		 Calendar cal2 = new GregorianCalendar();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		 Date date = sdf.parse(d2);
		 cal1.setTime(date);
		 date = sdf.parse(d1);
		 cal2.setTime(date);
		 
         return (int)( (cal1.getTime().getTime() - cal2.getTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
	 }
	 
	 @RequestMapping(value = "/web/listtour", method = RequestMethod.GET)	
	 public String ListTour(Model model) throws ParseException {
		 List<Tour> tours = tourDAO.getListTour_Web();
		 
		 for (Tour tour : tours)
			 tour.setCo(daysBetween(tour.getNgay_di(), tour.getNgay_ve()));
		 
		 model.addAttribute("tours", tours);
		 
	     return "user/listtour";
	 }
	 
	 private String unicodeToAscii(String s) throws UnsupportedEncodingException {
	        String s1 = Normalizer.normalize(s, Normalizer.Form.NFKD);
	        String regex = Pattern.quote("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");
	        String s2 = new String(s1.replaceAll(regex, "").getBytes("ascii"), "ascii");

	        return s2. replaceAll("[?]", "");
	    }
	 
	 private List<Tour> searchName(String name, List<Tour> tours ) throws UnsupportedEncodingException {
		 	if (name.equals("")) 
		 		return tours;
		 		
		 	List<Tour> result = new ArrayList();

	    	name = unicodeToAscii(name);

	        for(Tour tour : tours) {
	            String _name = null;
	            _name = unicodeToAscii(tour.getTen());
	            if (compare(name, _name))
	                result.add(tour);
	        }
	        
	        return result;
	    }
	 
	 private List<Tour> searchDuration(String duration, List<Tour> tours) throws ParseException {
		 if (duration.equals("")) 
		 		return tours;
		 int _duration;
		 try{
			 _duration = Integer.valueOf(duration);
		 }
		 catch(Exception ex) {
			 return tours;
		 }
		 		
		 List<Tour> result = new ArrayList();
		 	
		
		
		for(Tour tour : tours) 
			if (_duration == daysBetween(tour.getNgay_di(), tour.getNgay_ve()))
				result.add(tour);
		
		return result;
	 }
	 
	 private List<Tour> searchCheckIn(String checkIn, List<Tour> tours) {
		 if (checkIn.equals("")) 
		 	return tours;
		 
		 try{
			 checkIn = checkIn.substring(3, 6) + checkIn.substring(0, 3) + checkIn.substring(6, checkIn.length());	
		 }
		 catch(Exception ex) {
			 return tours;
		 }
		 	
		 List<Tour> result = new ArrayList();

		 for(Tour tour : tours) 
            if (tour.getNgay_di().equals(checkIn))
                result.add(tour);

        return result;
	 }
	 
	 private List<Tour> searchCost(String cost, int filter, List<Tour> tours) {
		 if (cost.equals("")) 
			 return tours;
		 	
		 int _cost;
		 try{
			 _cost = Integer.valueOf(cost);
		 }
		 catch(Exception ex) {
			 return tours;
		 }
		 
		 List<Tour> result = new ArrayList();

		 for(Tour tour : tours) 
			switch (filter) {
			case 1 : 
				 if (tour.getGia() <= _cost)
		                result.add(tour);
				 break;
			case 0 : 
				 if (tour.getGia() == _cost)
		                result.add(tour);
				 break;
			case -1 : 
				 if (tour.getGia() >= _cost)
		                result.add(tour);
				 break;
			}
           

        return result;
	 }

	 private static boolean compare(String item, String _item) {
		 if (item.equals(""))
			 return true;

	        String itemWord[] = item.split("[., ]");
	        String _itemWord[] = _item.split("[., ]");

	        for (String word : itemWord) {
	            for (String _word : _itemWord) {
	                if (word.equalsIgnoreCase(_word))
	                    return true;
	            }
	        }

	        return false;
	 }
	 
	 @RequestMapping(value = "/web/listtour", method = RequestMethod.POST)	
	 public String ListTour(Model model, @RequestParam("name") String name, @RequestParam("duration") String duration,
			 @RequestParam("check-in") String check_in, @RequestParam("cost") String cost, @RequestParam("filter") String filter) throws UnsupportedEncodingException, ParseException {
		 List<Tour> tours = tourDAO.getListTour_Web();
		 
		 tours = searchName(name, tours);
		 tours = searchDuration(duration, tours);
		 tours = searchCheckIn(check_in, tours);
		 
		 int _filter = 1;
		 if (filter.equals("equal"))
			 _filter = 0;
		 else if (filter.equals("min"))
			 _filter = -1;
		 
		 tours = searchCost(cost, _filter, tours);
		 
		 model.addAttribute("tours", tours);

	     return "user/listtour";
	 }
	 
	 @RequestMapping(value = "/web/findtour", method = RequestMethod.GET)	
	 public String FindingTour(Model model) {
	     return "user/findtour";
	 }
	 
	 @RequestMapping(value = "/web/detailtour", method = RequestMethod.GET)	
	 public String DetailTour(Model model, @RequestParam("id") String id) {
		 Tour tour = tourDAO.getTourById_Web(Integer.valueOf(id));
		 String ct[] = tour.getChuong_trinh().split("[0-9].");
		 
		 List<DanhSachDiaDiem> list = danhSachDiaDiemDAO.getListByTourId_Web(id);
		 List<DiaDiemDuLich> places = new ArrayList();
		 List<List<Restaurant>> rests = new ArrayList();
		 List<List<Hotel>> hotels = new ArrayList();
		 
		 for (DanhSachDiaDiem ds : list) {
			 places.add(diaDiemDuLichDAO.getById_Web(ds.getKey().getMa_dia_diem().toString()));
			 List<DanhSachNhaHangKhachSan> dsnhks = dsDanhSachNhaHangKhachSanDAO.getListByPlaceid(ds.getKey().getMa_dia_diem().toString());
			 List<Restaurant> rest = new ArrayList();
			 List<Hotel> hotel = new ArrayList();
			 for (DanhSachNhaHangKhachSan nhks : dsnhks) {
				 rest.add(restaurantDAO.getRestaurantByMa(nhks.getKey().getMa_nha_hang().toString()));
				 hotel.add(hotelDAO.getHotelByMa(nhks.getKey().getMa_khach_san().toString()));
			 }
			 rests.add(rest);
			 hotels.add(hotel);
		 }
		
		 model.addAttribute("tour", tour);
		 model.addAttribute("places", places);
		 model.addAttribute("rests", rests);
		 model.addAttribute("hotels", hotels);
		 model.addAttribute("ct", ct);
	     return "user/detail";
	 }
	 
	 private String bookingForm(Model model, Register register) {
		 model.addAttribute("bookingForm", register);
		 return "user/register";
	 }
	 
	 @InitBinder
	 protected void initBinder(WebDataBinder dataBinder) {
		 Object target = dataBinder.getTarget();
		 if (target == null) 
	           return;
	 
		 if (target.getClass() == Register.class) 
			 dataBinder.setValidator(registerValidator);
	 }
	 
	 @RequestMapping(value = "/web/registerTour", method = RequestMethod.GET)	
	 public String RegisterTourHasId(Model model, @RequestParam("id") String id) {
		 model.addAttribute("TourId", id);		 
		 model.addAttribute("TourName", tourDAO.getTourById_Web(Integer.valueOf(id)).getTen());
	     return bookingForm(model, new Register());
	 }
	 
	 @RequestMapping(value = "/web/register", method = RequestMethod.GET)	
	 public String RegisterTour(Model model) {
		 model.addAttribute("TourId", null);
		 model.addAttribute("TourName", null);
	     return bookingForm(model, new Register());
	 }
	 
	 private void saveBooking(Register register) {
		 KhachHang user = new KhachHang(0, register.getTen_khach_hang(), register.getSdt(),
				 						register.getCmnd(), register.getDia_chi(), false);
		 Integer userId = khachHangDAO.create_Web(user);

		 PhieuDangKi booking = new PhieuDangKi(0, Integer.valueOf(register.getMa_tour()), userId, 0,
					0, Integer.valueOf(register.getSo_luong()), register.getGhi_chu(), 0, false, register.getChinh_sua() != null);
		 
		 phieuDangKiDAO.create_Web(booking);
		 
		 Tour tour = tourDAO.getTourById_Web(booking.getMaTour());
		 tour.setSo_ve_con_lai(tour.getSo_ve_con_lai() - booking.getSLNguoi());
		 tourDAO.updateTourBySoVeConLai_Web(tour);
	 }
	 
	 @RequestMapping(value = "/web/pushRegister", method = RequestMethod.POST)
	 public String saveApplicant(Model model, 
	           @ModelAttribute("bookingForm") @Validated Register register, 
	           BindingResult result, 
	           final RedirectAttributes redirectAttributes) {
		 	
		 	if (result.hasErrors()) {
		 		model.addAttribute("TourId", register.getMa_tour());		 
		 		model.addAttribute("TourName", tourDAO.getTourById_Web(Integer.valueOf(register.getMa_tour())).getTen());
		 		return this.bookingForm(model, register);
		 	}
	 
		 	saveBooking(register);
		 	redirectAttributes.addFlashAttribute("message", "Đăng ký thành công!");
	 
		 	return "redirect:/web/detailtour?id=" + register.getMa_tour();
	 
	 }
	 
	 @RequestMapping(value = {"/web/contact" }, method = RequestMethod.GET)	
	 public String Contact(Model model) throws ParseException {
		
		 
	     return "user/contact";
	 }
	 
	 @RequestMapping(value = { "/web/image/tour" }, method = RequestMethod.GET)
	 public void loadImageTour(HttpServletResponse response, Model model,
	           @RequestParam("id") String id) throws IOException {
	       Tour tour = tourDAO.getTourById_Web(Integer.valueOf(id));
	       if (tour != null && tour.getHinh_anh() != null) {
	           response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	           response.getOutputStream().write(tour.getHinh_anh());
	       }
	       response.getOutputStream().close();
	   }
	 
	 @RequestMapping(value = { "/web/image/place" }, method = RequestMethod.GET)
	 public void loadImagePlace(HttpServletResponse response, Model model,
	           @RequestParam("id") String id) throws IOException {
	       DiaDiemDuLich place = diaDiemDuLichDAO.getById_Web(id);
	       if (place != null && place.getHinh_anh() != null) {
	           response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	           response.getOutputStream().write(place.getHinh_anh());
	       }
	       response.getOutputStream().close();
	   }
}
