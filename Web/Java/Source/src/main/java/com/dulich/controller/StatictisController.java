package com.dulich.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dulich.form.StatictisForm;
import com.dulich.form.UserForm;
import com.dulich.model.dao.TourDAO;
import com.dulich.model.pojo.User;



@Controller
public class StatictisController {
	
	private static String TITLE = "THỐNG KÊ";
	private static String title = "thống kê";
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	 private TourDAO tourDAO;
	
	@RequestMapping(value = {"/thongketour"}, method = RequestMethod.GET )
	 public ModelAndView user_home(UserForm userForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		 

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/thongke_sltour");
//		tourDAO.thongKeSLTourTheoNam("2016");
//		mav.addObject("countUser", 0);

		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	 }
	
	 @RequestMapping(value = {"/thongke_sltour_Nam_Thang"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getUserById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String type_1 = request.getParameter("type_1");
		 String type_2 = request.getParameter("type_2");
		 String type_3 = request.getParameter("type_3");
		 String year = request.getParameter("year");
		 String startYear = request.getParameter("start_year");
		 String endYear = request.getParameter("end_year");
		 year = (year != null)? year.replaceAll(" ", "") : year;
		 startYear = (startYear != null)? startYear.replaceAll(" ", "") : startYear;
		 endYear = (endYear != null)? endYear.replaceAll(" ", "") : endYear;
		 StatictisForm statictisForm = new StatictisForm();
		 
		 if(type_1.equals("0") && type_2.equals("0") && type_3.equals("0")){
			 statictisForm = tourDAO.thongKeSLTourTheoNam_Thang(year);
		 }
		 if(type_1.equals("0") && type_2.equals("0") && type_3.equals("1")){
			 statictisForm = tourDAO.thongKeSLTourTheoNam_Qui(year);
		 }
		 if(type_1.equals("0") && type_2.equals("1")){
			 statictisForm = tourDAO.thongKeSLTourTheoNhieuNam(startYear, endYear);
		 }
		 
		 if(type_1.equals("2") && type_2.equals("0") && type_3.equals("0")){
			 statictisForm = tourDAO.thongKeDoanhThuTheoNam_Thang(year);
		 }
		 if(type_1.equals("2") && type_2.equals("0") && type_3.equals("1")){
			 statictisForm = tourDAO.thongKeDoanhThuTheoNam_Qui(year);
		 }
		 if(type_1.equals("2") && type_2.equals("1")){
			 statictisForm = tourDAO.thongKeDoanhThuTheoNhieuNam(startYear, endYear);
		 }
		 
		 if(type_1.equals("1") && type_2.equals("0") && type_3.equals("0")){
			 statictisForm = tourDAO.thongKeKhachTheoNam_Thang(year);
		 }
		 if(type_1.equals("1") && type_2.equals("0") && type_3.equals("1")){
			 statictisForm = tourDAO.thongKeKhachTheoNam_Qui(year);
		 }
		 if(type_1.equals("1") && type_2.equals("1")){
			 statictisForm = tourDAO.thongKeKhachTheoNhieuNam(startYear, endYear);
		 }
		 
		 if(type_1.equals("3") && type_2.equals("0") && type_3.equals("0")){
			 statictisForm = tourDAO.thongKeTienGocTheoNam_Thang(year);
		 }
		 if(type_1.equals("3") && type_2.equals("0") && type_3.equals("1")){
			 statictisForm = tourDAO.thongKeTienGocTheoNam_Qui(year);
		 }
		 if(type_1.equals("3") && type_2.equals("1")){
			 statictisForm = tourDAO.thongKeTienGocTheoNhieuNam(startYear, endYear);
		 }
		 
		 String jsonInString = "";
		 if(statictisForm != null){
			 jsonInString = mapper.writeValueAsString(statictisForm);
		 }else{
			 jsonInString = mapper.writeValueAsString(new StatictisForm());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/tour_In_Thang"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getTourInMonth(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String year = request.getParameter("year");
		 String month = request.getParameter("month");

		 year = (year != null)? year.replaceAll(" ", "") : year;
		 month = (month != null)? month.replaceAll(" ", "") : month;
		 List<Object> rs = null;
		 rs = tourDAO.thongKeTourTheoNam_Thang(month, year);
		 
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(null);
		 }
		 
		 return jsonInString;
	 }
	
}
