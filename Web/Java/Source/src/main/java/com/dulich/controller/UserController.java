package com.dulich.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dulich.form.NghiPhepForm;
import com.dulich.form.UserForm;
import com.dulich.model.dao.UserDAO;
import com.dulich.model.pojo.NghiPhep;
import com.dulich.model.pojo.User;

@Controller
public class UserController {
	
	 @Autowired
	 private UserDAO userDAO;
	 @Autowired
	 private MessageSource messageSource;
	 
	 ObjectMapper mapper = new ObjectMapper();
	 private List<User> _listUser_search;
	 private UserForm _userForm_search;
	 private UserForm _userForm_edit;
	 private static String TITLE = "NHÂN VIÊN";
	 private static String title = "nhân viên";
	 private String errorMessages = "";
	 private String isEdited = "false";
	 
	 @RequestMapping(value = {"/", "/login", "/logout", "/logoutSuccessful"}, method = RequestMethod.GET )
	 public ModelAndView login() {
		 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/login");
	    return mav;
	 }
	 
	 @RequestMapping(value = {"/home"}, method = RequestMethod.POST )
	 public ModelAndView logintohome(@RequestParam(value = "username") String username,
			 @RequestParam(value = "password") String password) {
		 User user = userDAO.getUserByTaiKhoan(username);
		 if (user != null && user.getMatKhau().equals(password)) {
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("admin/home");
			 mav.addObject("authen", user);
			 return mav;
		 }	  	

		 ModelAndView mav = new ModelAndView();
		 mav.addObject("error", "error");
		 mav.setViewName("admin/login");
		 return mav;
	 }
	 
	 @RequestMapping(value = {"/home"}, method = RequestMethod.GET )
	 public ModelAndView home(HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 1, 2);
		 if (foward != null)
			 return foward;
		 
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/home");
		 return mav;
	 }

	 @RequestMapping(value = {"/user"}, method = RequestMethod.GET )
	 public ModelAndView user_home(UserForm userForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		 
		_listUser_search = null;
		_userForm_search = new UserForm();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/user");
		mav.addObject("countUser", 0);
		mav.addObject("userForm", new UserForm(3));
		mav.addObject("userFormEdit", new UserForm());
		mav.addObject("userFormDelete", new UserForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	 }
	 
	 @RequestMapping(value = {"/searchUser"}, method = RequestMethod.GET )
	 public ModelAndView search(@ModelAttribute("userForm")UserForm userForm, BindingResult resultUser,
			 @RequestParam(value = "action", required = false) String action) {
		 
		 int countUser = 0;
		 ModelAndView mav = new ModelAndView();
		 List<User> listUser = new ArrayList<User>();
		 mav.addObject("isDeleted", false);
		 if(action == null){
			 userForm.setLoai(3);
			 listUser = _listUser_search;
		 }else if(action.equals("search") || action.equals("edit")){
			 listUser = userDAO.getListUser(userForm);
			 _listUser_search = listUser;
			 _userForm_search = userForm;
			 countUser =  listUser.size();
			 
		 }else if(action.equals("reset")){
			 return new ModelAndView("redirect:/user");
		 }else if(action.equals("delete")){
			 listUser = userDAO.getListUser(userForm);
			 _listUser_search = listUser;
			 _userForm_search = userForm;
			 countUser =  listUser.size();
			 mav.addObject("isDeleted", true);
		 }
		 
		 
		 mav.addObject("listUser", listUser);
		 mav.setViewName("admin/user");
		 mav.addObject("countUser", countUser);
		 mav.addObject("userForm", _userForm_search);
		 UserForm temp = new UserForm();
		 this.copy2UserForm(_userForm_edit, temp);
		 
		 mav.addObject("userFormEdit", temp);
		 mav.addObject("userFormDelete", new UserForm());
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
//		 for show modal edit
		 errorMessages =( action != null && action.equals("search"))? "" : errorMessages;
		 mav.addObject("errorMessages", errorMessages);
		 
		 isEdited = (action != null && action.equals("edit"))? "true" : "false";
		 isEdited = (_userForm_edit== null)? "false" : isEdited; // when f5
		 mav.addObject("isEdited", isEdited);
	     return mav;
	 }
	 
	 @RequestMapping(value = {"/deleteUser"}, method = RequestMethod.POST )
	 public String delete( @ModelAttribute("userFormDelete") UserForm userForm) {
		 String idUser = userForm.getMa();
		 userDAO.deleteUser(idUser);
		 userForm = null;
		 String param = "";
		 param = (_userForm_search != null)? this.makeURLFromForm(_userForm_search, "delete"): "";
		 return "redirect:/searchUser" + param;
	 }
	 
	 @RequestMapping(value = {"/editUser"}, method = RequestMethod.POST)
	 public String edit(@Valid @ModelAttribute("userFormEdit") UserForm userForm, BindingResult bindingResult, Locale locale) {
		 errorMessages = new String();	 
		 if (bindingResult.hasErrors()) {
			 for (Object object : bindingResult.getAllErrors()) {
			    if(object instanceof ObjectError) {
			        ObjectError objectError = (ObjectError) object;
			        errorMessages += messageSource.getMessage(objectError.getCodes()[0], null, locale) + "<br>";
			    }
			}
			 errorMessages = String.format(errorMessages);
	     }else{
	    	 userDAO.updateUser(userForm);
	    	 errorMessages = "successfully";
	     }
		 
		 _userForm_edit = userForm;
		 userForm = null;
		 String param = (_userForm_search != null)? this.makeURLFromForm(_userForm_search, "edit"): "";
		 return "redirect:/searchUser" + param;
	 }

	 @RequestMapping(value = {"/getUserById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getUserById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String userId = request.getParameter("userId");
		 User rs = userDAO.getUserByMa(userId);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/getListUser"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getListUser(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String gt = request.getParameter("GioiTinh");
		 String ma = request.getParameter("Ma");
		 String tk = request.getParameter("TaiKhoan");
		 String hoten = request.getParameter("HoTen");
		 String diachi = request.getParameter("DiaChi");
		 String namsinh = request.getParameter("NamSinh");
		 UserForm userForm = new UserForm(ma, tk, hoten, diachi, namsinh, gt);
		 userForm.setLoai(1);
		 List<User> listUser = new ArrayList<User>();
		 listUser = userDAO.getListUser(userForm);
		 String jsonInString = "";
		 jsonInString = mapper.writeValueAsString(listUser);
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/addUser"} )
	 public ModelAndView add(@Valid @ModelAttribute("userFormAdd")UserForm userForm, BindingResult bindingResult, Locale locale,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		 
		 errorMessages = new String();	
		 if(action != null && action.equals("add")){
			 if (bindingResult.hasErrors()) {
				 for (Object object : bindingResult.getAllErrors()) {
				    if(object instanceof ObjectError) {
				        ObjectError objectError = (ObjectError) object;
				        errorMessages += messageSource.getMessage(objectError.getCodes()[0], null, locale) + "<br>";
				    }
				}
				 errorMessages = String.format(errorMessages);
		     }else{
		    	 String rs = userDAO.addUser(userForm);
		    	 if(rs.equals("1")){
		    		 errorMessages = "successfully";
		    	 }else{
		    		 errorMessages = rs;
		    	 }
		    	 
		     }
		 }
		 if(action != null && action.equals("reset")){
			 return new ModelAndView("redirect:/addUser");
		 }
		
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/user_add");
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("userForm", userForm);
	     return mav;
	 }
	 
	 @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String resetPassword(@RequestParam(value="id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 Boolean rs = userDAO.resetPassword(id);
		 String jsonInString = rs.toString();
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString("false");
		 }		 
		 return jsonInString;
	 }
	 ////////////////////////////////////////

	 @RequestMapping(value = {"/duyet_nghiphep"}, method = RequestMethod.GET )
	 public ModelAndView duyetNP_home(@ModelAttribute("userForm")UserForm userForm,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		 
		 int countUser = 0;
		 ModelAndView mav = new ModelAndView();
		 List<User> listUser = new ArrayList<User>();
		if(action != null && action.equals("search")){
			listUser = userDAO.getListUserDuyetNghiPhep(userForm);
			 countUser =  listUser.size();
		}else if(action != null && action.equals("reset")){
			return new ModelAndView("redirect:/duyet_nghiphep");
		}else if(action == null){
			mav.addObject("userForm", new UserForm(3, 1));
		}
		
		mav.addObject("listUser", listUser);
		mav.setViewName("admin/user_nghiphep");
		mav.addObject("countUser", countUser);
		mav.addObject("TITLE", "DUYỆT NGHỈ PHÉP");
		mav.addObject("title", "duyệt nghỉ phép");
	    return mav;
		
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
	 
	 @RequestMapping(value = {"/dangky_nghiphep"}, method = RequestMethod.GET )
	 public ModelAndView dangkyNP_home(HttpServletRequest request) throws ParseException {
		 ModelAndView foward = UserController.Authen(request, 1, 2);
		 if (foward != null)
			 return foward;
		 
		 int countNghiPhep = 0;
		 ModelAndView mav = new ModelAndView();
		 List<NghiPhep> listNghiPhep = userDAO.getListNghiPhepByIdUser(request.getSession().getAttribute("userId").toString());
		 for (NghiPhep np : listNghiPhep)
			 np.setMaNV(String.valueOf(daysBetween(np.getNgayNghi(), np.getNgayDiLam())));
		 
		 countNghiPhep =  listNghiPhep.size();
		
		 mav.addObject("listNghiPhep", listNghiPhep);
		 mav.setViewName("admin/user_dknghiphep");
		 mav.addObject("countNghiPhep", countNghiPhep);
		 mav.addObject("TITLE", "ĐĂNG KÝ NGHỈ PHÉP");
		 mav.addObject("title", "đăng ký nghỉ phép");
		 mav.addObject("errorMessages", "");
		 mav.addObject("userNghiPhepForm", new NghiPhepForm());
		 mav.addObject("userNghiPhepFormDelete", new UserForm());
		 return mav;
		
	 }
	 
	 @RequestMapping(value = {"/tao_nghiphep"}, method = RequestMethod.GET )
	 public ModelAndView tao_nghiphep(@ModelAttribute("userNghiPhepForm")NghiPhepForm nghiPhepForm,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) throws ParseException {
		 ModelAndView foward = UserController.Authen(request, 1, 2);
		 if (foward != null)
			 return foward;
					 
		 String errorMessages = "";
		 if (nghiPhepForm.getNgayDiLam().equals("") || nghiPhepForm.getNgayNghi().equals(""))
			 errorMessages = "Không được bỏ trống";
		 else {
			 if (daysBetween(nghiPhepForm.getNgayNghi(), nghiPhepForm.getNgayDiLam()) > 0) {
				 userDAO.themNNP(action, nghiPhepForm.getNgayNghi(), nghiPhepForm.getNgayDiLam());
				 errorMessages = "successfully";
			 }
			 else
				 errorMessages = "Không thể đăng ký";		 
		 }
		 
		 int countNghiPhep = 0;
		 ModelAndView mav = new ModelAndView();
		 List<NghiPhep> listNghiPhep = userDAO.getListNghiPhepByIdUser(action);
		 for (NghiPhep np : listNghiPhep)
			 np.setMaNV(String.valueOf(daysBetween(np.getNgayNghi(), np.getNgayDiLam())));
		 
		 countNghiPhep =  listNghiPhep.size();
		
		 mav.addObject("idUser", action);
		 mav.addObject("listNghiPhep", listNghiPhep);
		 mav.setViewName("admin/user_dknghiphep");
		 mav.addObject("countNghiPhep", countNghiPhep);
		 mav.addObject("TITLE", "ĐĂNG KÝ NGHỈ PHÉP");
		 mav.addObject("title", "đăng ký nghỉ phép");
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("userNghiPhepForm", nghiPhepForm);
		 mav.addObject("userNghiPhepFormDelete", new UserForm());	
	     return mav;
		
	 }
	 
	 @RequestMapping(value = {"/deleteNghiPhep"}, method = RequestMethod.POST )
	 public String deleteNghiPhep( @ModelAttribute("userNghiPhepFormDelete") UserForm userForm,
			 @RequestParam(value = "action", required = false) String action) {
		 String idNghiPhep = userForm.getMa();
		 userDAO.deleteNghiPhepById(idNghiPhep);
		 userForm = null;
		 String param = "";
		 param = (_userForm_search != null)? this.makeURLFromForm(_userForm_search, "delete"): "";
		 return "redirect:/dangky_nghiphep";
	 }
	 
	 @RequestMapping(value = {"/getNgayNghiByIdNV"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getNgayNghiByIdNV(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String userId = request.getParameter("userId");
		 List<NghiPhep> rs = userDAO.getNNPByIdNV(userId, 0);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/getNgayNghiDaDuyetByIdNV"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getNgayNghiDaDuyetByIdNV(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String userId = request.getParameter("userId");
		 List<NghiPhep> rs = userDAO.getNNPByIdNV(userId, 1);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/duyetNNP"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String duyetNNP(@RequestParam(value="value") String[] listNNP) throws JsonGenerationException, JsonMappingException, IOException {
		
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 Boolean rs = userDAO.duyetNNPByListID(listNNP);
		 String jsonInString = rs.toString();
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/xoaNNP"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String xoaNNP(@RequestParam(value="value") String[] listNNP) throws JsonGenerationException, JsonMappingException, IOException {
		
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 Boolean rs = userDAO.xoaNNPByListID(listNNP);
		 String jsonInString = rs.toString();
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/suaNNP"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String suaNNP(@RequestParam(value="id") String id,
			 @RequestParam(value="ngNghi") String ngNghi, @RequestParam(value="ngLam") String ngLam) throws JsonGenerationException, JsonMappingException, IOException {
		
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 Boolean rs = userDAO.suaNNP(id, ngNghi, ngLam);
		 String jsonInString = rs.toString();
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new User());
		 }		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/themNNP"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String themNNP(@RequestParam(value="userId") String userId,
			 @RequestParam(value="ngNghi") String ngNghi, @RequestParam(value="ngLam") String ngLam) throws JsonGenerationException, JsonMappingException, IOException {
		
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 Boolean rs = userDAO.themNNP(userId, ngNghi, ngLam);
		 String jsonInString = rs.toString();
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(false);
		 }		 
		 return jsonInString;
	 }
	 
	 private String makeURLFromForm(UserForm userForm,String action){
		 
		 String rs = "?";
		 rs += "Loai=" + userForm.getLoai() + "&";
		 rs += "Ma=" + userForm.getMa() + "&";
		 rs += "TaiKhoan=" + userForm.getTaiKhoan() + "&";
		 rs += "HoTen=" + userForm.getHoTen() + "&";
		 rs += "DiaChi=" + userForm.getDiaChi() + "&";
		 rs += "NamSinh=" + userForm.getNamSinh() + "&";
		 rs += "GioiTinh=" + userForm.getGioiTinh() + "&";
		 rs += "action=" + action;
		 return rs;
	 }
	 private void copy2UserForm(UserForm src, UserForm desc){
		 if(src != null){
			 desc.setCMND(src.getCMND());
			 desc.setDiaChi(src.getDiaChi());
			 desc.setGioiTinh(src.getGioiTinh());
			 desc.setHoTen(src.getHoTen());
			 desc.setLoai(src.getLoai());
			 desc.setMa(src.getMa());
			 desc.setNamSinh(src.getNamSinh());
			 desc.setSoDT(src.getSoDT());
			 desc.setTaiKhoan(src.getTaiKhoan());
		 }
		 
	 }
	 
	 /*@RequestMapping(value = {"/**"}, method = RequestMethod.GET )
	 public ModelAndView other(HttpServletRequest request) {
		 Object userType = request.getSession().getAttribute("userType");

		 if (userType == null || Integer.valueOf(userType.toString()) < 0) {
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("admin/login");
			 return mav;
		 }
		 
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/home");
		 return mav;
	 }*/
	 
	 public static ModelAndView Authen(HttpServletRequest request, int ... authens) {
		 Object userType = request.getSession().getAttribute("userType");

		 if (userType == null || Integer.valueOf(userType.toString()) < 0) {
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("admin/login");
			 return mav;
		 }
		 
		 for (int i = 0; i < authens.length; i++) 
			 if (authens[i] == Integer.valueOf(userType.toString()))
				 return null;

		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/home");
		 return mav;
	 }
}
