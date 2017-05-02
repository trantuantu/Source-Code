package com.dulich.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.dulich.form.HotelForm;
import com.dulich.form.KhachHangForm;
import com.dulich.form.TourForm;
import com.dulich.form.UserForm;
import com.dulich.model.dao.DanhSachNhaHangKhachSanDAO;
import com.dulich.model.dao.DiaDiemDuLichDAO;
import com.dulich.model.dao.HotelDAO;
import com.dulich.model.dao.RestaurantDAO;
import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DanhSachNhaHangKhachSan;
import com.dulich.model.pojo.DiaDiemDuLich;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.Restaurant;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.User;

@Controller
public class HotelController {
	
	 @Autowired
	 private HotelDAO hotelDAO;
     
     @Autowired
     private DiaDiemDuLichDAO diaDiemDuLichDAO;
     @Autowired
     private RestaurantDAO restaurantDAO;
     
     @Autowired
     private DanhSachNhaHangKhachSanDAO danhSachNhaHangKhachSanDAO;
     
	 @Autowired
	 private MessageSource messageSource;
	 
	 ObjectMapper mapper = new ObjectMapper();
	 private static String TITLE = "KHÁCH SẠN";
	 private static String title = "Khách sạn";
	 private String errorMessages = "";
	 private HotelForm _hotelForm_search;
	 private HotelForm _hotelForm_edit;
	 private String isEdited = "false";
	 private List<Hotel> _listHotel_search;
	 
	@RequestMapping(value = {"/hotel"}, method = RequestMethod.GET )
	 public ModelAndView user_home(HotelForm hotelForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/hotel");
		mav.addObject("countHotel", 0);
		mav.addObject("hotelForm", new HotelForm(6));
		mav.addObject("hotelFormEdit", new HotelForm());
		mav.addObject("hotelFormDelete", new HotelForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	}
	
	
	 @RequestMapping(value = {"/searchHotel"}, method = RequestMethod.GET )
	 public ModelAndView search(@ModelAttribute("hotelForm")HotelForm hotelForm, BindingResult resultHotel,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 int countHotel = 0;
		 ModelAndView mav = new ModelAndView();
		 List<Hotel> listHotel = new ArrayList<Hotel>();
		 if(action == null){
			 hotelForm.setLoai(6);
			 listHotel = _listHotel_search;
		 }else if(action.equals("search") || action.equals("edit")){
			 listHotel = hotelDAO.getListHotel(hotelForm);
			 _hotelForm_search = hotelForm;
			 _listHotel_search = listHotel;
			 countHotel = listHotel.size();
		 }else if(action.equals("reset")){
			return new ModelAndView("redirect:/hotel");
		 }
		 
		 mav.addObject("listHotel", listHotel);	
		 mav.addObject("countHotel", countHotel);
		 mav.setViewName("admin/hotel");
		 mav.addObject("hotelForm", hotelForm);
		 mav.addObject("hotelFormDelete", new HotelForm());
		 
		 HotelForm temp = new HotelForm();
		 this.copy2HotelForm(_hotelForm_edit, temp); 
		 mav.addObject("hotelFormEdit", temp);
		 
//		 for show modal edit
		 errorMessages =( action != null && action.equals("search"))? "" : errorMessages;
		 mav.addObject("errorMessages", errorMessages);
		 
		 isEdited = (action != null && action.equals("edit"))? "true" : "false";
		 isEdited = (_hotelForm_edit== null)? "false" : isEdited; // when f5
		 mav.addObject("isEdited", isEdited);
		 
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);

	     return mav;
	 }
	 
	 @RequestMapping(value = {"/deleteHotel"}, method = RequestMethod.POST )
	 public String delete( @ModelAttribute("hotelFormDelete") HotelForm hotelForm) {
		 String idHotel = hotelForm.getMa();
		 hotelDAO.deleteHotel(idHotel);
//		 userForm = null;
		 String param = "";
		 param = (_hotelForm_search != null)? this.makeURLFromForm(_hotelForm_search, "search"): "";
		 return "redirect:/searchHotel" + param;
	 }
	
	 private String makeURLFromForm(HotelForm hotelForm,String action){
		 //Loai=6&Ten=a&DiaChi=&action=search
		 String rs = "?";
		 rs += "Loai=" + hotelForm.getLoai() + "&";
		 rs += "Ten=" + hotelForm.getTen() + "&";
		 rs += "Ma=" + hotelForm.getMa() + "&";
		 rs += "DiaChi=" + hotelForm.getDiaChi() + "&";
		 rs += "action=" + action;
		 return rs;
	 }
	 
	 @RequestMapping(value = {"/getHotelById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getHotelById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String hotelId = request.getParameter("hotelId");
		 Hotel rs = hotelDAO.getHotelByMa(hotelId);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new Hotel());
		 }
		 
		 return jsonInString;
	 }
	
	 @RequestMapping(value = {"/editHotel"}, method = RequestMethod.POST)
	 public String edit(@Valid @ModelAttribute("hotelFormEdit") HotelForm hotelForm, BindingResult bindingResult, Locale locale) {
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
	    	 hotelDAO.updateHotel(hotelForm);
	    	 errorMessages = "successfully";
	     }
		 
		 _hotelForm_edit = hotelForm;
		 hotelForm = null;
		 String param = (_hotelForm_search != null)? this.makeURLFromForm(_hotelForm_search, "edit"): "";
		 return "redirect:/searchHotel" + param;
	 }
	 
	 private void copy2HotelForm(HotelForm src, HotelForm desc){
		 if(src != null){
			 desc.setDiaChi(src.getDiaChi());
			 desc.setLoai(src.getLoai());
			 desc.setMa(src.getMa());
			 desc.setTen(src.getTen());
		 }
		 
	 }
	
	 @RequestMapping(value = {"/addHotel"} )
	 public ModelAndView add(@Valid @ModelAttribute("hotelFormAdd")HotelForm hotelForm, BindingResult bindingResult, Locale locale,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
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
		    	 String rs = hotelDAO.addHotel(hotelForm);
		    	 if(rs.equals("1")){
		    		 errorMessages = "successfully";
		    	 }else{
		    		 errorMessages = rs;
		    	 }
		    	 
		     }
			 System.out.println(errorMessages); 
		 }
		 if(action != null && action.equals("reset")){
			 return new ModelAndView("redirect:/addHotel");
		 }
		
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/hotel_add");
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("hotelForm", hotelForm);
	     return mav;
	 }
    
     @RequestMapping(value = {"/getlistHotelRetaurant"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
     public @ResponseBody String getTourByIdPDK(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
         mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
         String id_place = request.getParameter("id_place");
         DiaDiemDuLich diaDiemDuLich = diaDiemDuLichDAO.getById_Web(id_place);
         List<Hotel> listHotel = hotelDAO.getAllListHotel();
         List<Restaurant> listRestaurant = restaurantDAO.getAllListRestaurant();
         
         String jsonInString = "";
         String jsonInString1 = "";
        
         List<String> list = new ArrayList<String>();
         jsonInString = mapper.writeValueAsString(listHotel);
         list.add(jsonInString);
         jsonInString1 = mapper.writeValueAsString(listRestaurant);
         list.add(jsonInString1);
         String result = mapper.writeValueAsString(list);
         
         return result;
     }
     
     @RequestMapping(value = {"/informationHotel"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
     public @ResponseBody String getInformationTour(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
         mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
         
         String id_place = request.getParameter("id_place");
        
         List<DanhSachNhaHangKhachSan> listDsNHKS=  danhSachNhaHangKhachSanDAO.getListByPlaceid(id_place);
         List<Hotel> listHotel = new ArrayList<Hotel>();
         for(int i = 0; i< listDsNHKS.size(); i++){
             if(listDsNHKS.get(i).getKey().getMa_khach_san()!=null)
             {
                 Hotel hotel = new Hotel();
                 hotel = hotelDAO.getHotelByMa(listDsNHKS.get(i).getKey().getMa_khach_san().toString());
                 listHotel.add(hotel); 
             }
             
         }
         String jsonInString = "";
         jsonInString = mapper.writeValueAsString(listHotel);
         System.out.println(jsonInString);
         return jsonInString;
     }
}
