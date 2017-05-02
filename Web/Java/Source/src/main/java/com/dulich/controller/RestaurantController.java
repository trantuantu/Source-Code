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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dulich.form.RestaurantForm;
import com.dulich.model.dao.DanhSachNhaHangKhachSanDAO;
import com.dulich.model.dao.RestaurantDAO;
import com.dulich.model.pojo.DanhSachNhaHangKhachSan;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.Restaurant;


@Controller
public class RestaurantController {
	
	 @Autowired
	 private RestaurantDAO restaurantDAO;
	 @Autowired
	 private MessageSource messageSource;
     @Autowired
     private DanhSachNhaHangKhachSanDAO danhSachNhaHangKhachSanDAO;
     
	 ObjectMapper mapper = new ObjectMapper();
	 private static String TITLE = "NHÀ HÀNG";
	 private static String title = "Nhà hàng";
	 private String errorMessages = "";
	 private RestaurantForm _restaurantForm_search;
	 private RestaurantForm _restaurantForm_edit;
	 private String isEdited = "false";
	 private List<Restaurant> _listRestaurant_search;
	 
	@RequestMapping(value = {"/restaurant"}, method = RequestMethod.GET )
	 public ModelAndView restaurant_home(RestaurantForm restaurantForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/restaurant");
		mav.addObject("countRestaurant", 0);
		mav.addObject("restaurantForm", new RestaurantForm(4));
		mav.addObject("restaurantFormEdit", new RestaurantForm());
		mav.addObject("restaurantFormDelete", new RestaurantForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	}
	
	
	 @RequestMapping(value = {"/searchRestaurant"}, method = RequestMethod.GET )
	 public ModelAndView search(@ModelAttribute("restaurantForm")RestaurantForm restaurantForm, BindingResult resultRestaurant,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 int countRestaurant= 0;
		 ModelAndView mav = new ModelAndView();
		 List<Restaurant> listRestaurant = new ArrayList<Restaurant>();
		 if(action == null){
			 restaurantForm.setLoai(4);
			 listRestaurant = _listRestaurant_search;
		 }else if(action.equals("search") || action.equals("edit")){
			 listRestaurant = restaurantDAO.getListRestaurant(restaurantForm);
			 _restaurantForm_search = restaurantForm;
			 _listRestaurant_search = listRestaurant;
			 countRestaurant = listRestaurant.size();
		 }else if(action.equals("reset")){
			return new ModelAndView("redirect:/restaurant");
		 }
		 mav.addObject("listRestaurant", listRestaurant);	
		 mav.addObject("countRestaurant", countRestaurant);
		 mav.setViewName("admin/restaurant");
		 mav.addObject("restaurantForm", restaurantForm);
		 mav.addObject("restaurantFormDelete", new RestaurantForm());
		 
		 RestaurantForm temp = new RestaurantForm();
		 this.copy2RestaurantForm(_restaurantForm_edit, temp); 
		 mav.addObject("restaurantFormEdit", temp);
		 
//		 for show modal edit
		 errorMessages =( action != null && action.equals("search"))? "" : errorMessages;
		 mav.addObject("errorMessages", errorMessages);
		 
		 isEdited = (action != null && action.equals("edit"))? "true" : "false";
		 isEdited = (_restaurantForm_edit== null)? "false" : isEdited; // when f5
		 mav.addObject("isEdited", isEdited);
		 
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);

	     return mav;
	 }
	 
	 @RequestMapping(value = {"/deleteRestaurant"}, method = RequestMethod.POST )
	 public String delete( @ModelAttribute("restaurantFormDelete") RestaurantForm restaurantForm) {
		 String idRestaurant = restaurantForm.getMa();
		 restaurantDAO.deleteRestaurant(idRestaurant);
//		 userForm = null;
		 String param = "";
		 param = (_restaurantForm_search != null)? this.makeURLFromForm(_restaurantForm_search, "search"): "";
		 return "redirect:/searchRestaurant" + param;
	 }
	
	 private String makeURLFromForm(RestaurantForm restaurantForm,String action){
		 //Loai=6&Ten=a&DiaChi=&action=search
		 String rs = "?";
		 rs += "Loai=" + restaurantForm.getLoai() + "&";
		 rs += "Ten=" + restaurantForm.getTen() + "&";
		 rs += "Ma=" + restaurantForm.getMa() + "&";
		 rs += "DiaChi=" + restaurantForm.getDiaChi() + "&";
		 rs += "action=" + action;
		 return rs;
	 }
	 
	 @RequestMapping(value = {"/getRestaurantById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getRestaurantById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String restaurantId = request.getParameter("restaurantId");
		 Restaurant rs = restaurantDAO.getRestaurantByMa(restaurantId);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new Restaurant());
		 }
		 
		 return jsonInString;
	 }
	
	 @RequestMapping(value = {"/editRestaurant"}, method = RequestMethod.POST)
	 public String edit(@Valid @ModelAttribute("restaurantFormEdit") RestaurantForm restaurantForm, BindingResult bindingResult, Locale locale) {
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
	    	 restaurantDAO.updateRestaurant(restaurantForm);
	    	 errorMessages = "successfully";
	     }
		 
		 _restaurantForm_edit = restaurantForm;
		 restaurantForm = null;
		 String param = (_restaurantForm_search != null)? this.makeURLFromForm(_restaurantForm_search, "edit"): "";
		 return "redirect:/searchRestaurant" + param;
	 }
	 
	 private void copy2RestaurantForm(RestaurantForm src, RestaurantForm desc){
		 if(src != null){
			 desc.setDiaChi(src.getDiaChi());
			 desc.setLoai(src.getLoai());
			 desc.setMa(src.getMa());
			 desc.setTen(src.getTen());
		 }
		 
	 }
	
	 @RequestMapping(value = {"/addRestaurant"} )
	 public ModelAndView add(@Valid @ModelAttribute("restaurantFormAdd")RestaurantForm restaurantForm, BindingResult bindingResult, Locale locale,
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
		    	 String rs = restaurantDAO.addRestaurant(restaurantForm);
		    	 if(rs.equals("1")){
		    		 errorMessages = "successfully";
		    	 }else{
		    		 errorMessages = rs;
		    	 }
		    	 
		     }
			 System.out.println(errorMessages); 
		 }
		 if(action != null && action.equals("reset")){
			 return new ModelAndView("redirect:/addRestaurant");
		 }
		
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/restaurant_add");
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("restaurantForm", restaurantForm);
	     return mav;
	 }
     
     @RequestMapping(value = {"/informationRestaurant"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
     public @ResponseBody String getInformationTour(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
         mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
         
         String id_place = request.getParameter("id_place");
        
         List<DanhSachNhaHangKhachSan> listDsNHKS=  danhSachNhaHangKhachSanDAO.getListByPlaceid(id_place);
         List<Restaurant> listRestaurant = new ArrayList<Restaurant>();
         for(int i = 0; i< listDsNHKS.size(); i++){
             if(listDsNHKS.get(i).getKey().getMa_nha_hang()!= null){
                 Restaurant restaurant = new Restaurant();
                 restaurant = restaurantDAO.getRestaurantByMa(listDsNHKS.get(i).getKey().getMa_nha_hang().toString());
                 listRestaurant.add(restaurant); 
             }
         }
         String jsonInString = "";
         jsonInString = mapper.writeValueAsString(listRestaurant);
         System.out.println(jsonInString);
         return jsonInString;
     }

}