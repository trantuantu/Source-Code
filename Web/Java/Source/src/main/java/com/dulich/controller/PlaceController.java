package com.dulich.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dulich.form.AcceptRegistrationForm;
import com.dulich.form.HotelForm;
import com.dulich.form.PlaceForm;
import com.dulich.form.SearchRegistrationForm;
import com.dulich.model.dao.DiaDiemDuLichDAO;
import com.dulich.model.dao.HotelDAO;
import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DiaDiemDuLich;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.RegistrationForm;
import com.dulich.model.pojo.Tour;

@Controller
public class PlaceController {
	
	 @Autowired
	 private DiaDiemDuLichDAO diaDiemDuLichDAO;
	 @Autowired
	 private MessageSource messageSource;
	 
	 ObjectMapper mapper = new ObjectMapper();
	 private String errorMessages = "";
	 private HotelForm _placeForm_search;
	 private HotelForm _placeForm_edit;
	 private String isEdited = "false";
	 private List<Hotel> _listPlace_search;
	 
	 
	@RequestMapping(value = {"/place"}, method = RequestMethod.GET )
	 public ModelAndView user_home(HotelForm hotelForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/place");
		
		List<DiaDiemDuLich> listPlace = diaDiemDuLichDAO.getAllDiadiemdulich();
		mav.addObject("countPlace",listPlace.size());
		mav.addObject("listPlace", listPlace);
		mav.addObject("isEdited", false);
		mav.addObject("placeFormEdit", new PlaceForm());
		mav.addObject("placeFormDelete", new PlaceForm());
		mav.addObject("searchForm", new PlaceForm());
		
	    return mav;
	}
	
	
	 @RequestMapping(value = {"/addPlace"},method = RequestMethod.POST)
	 public ModelAndView add(@Valid @ModelAttribute("placeFormAdd")PlaceForm placeForm, BindingResult bindingResult, Locale locale,
		@RequestParam(value = "action", required = false) String action,HttpServletRequest request)  throws JsonParseException, JsonMappingException, IOException{
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
		    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				 List<MultipartFile> files = multipartRequest.getFiles("fileUpload");
				 MultipartFile file = files.get(0);
				 if(file.getSize() <= 0){
					 errorMessages = "BaÌ£n chÆ°a choÌ£n aÌ‰nh";
				 }else{
					 //DiaDiemDuLich diaDiemDuLich = diaDiemDuLichDAO.getDiadiemdulichbyTen(placeForm.getTen());
					 if(diaDiemDuLichDAO.getDiadiemdulichbyTen(placeForm.getTen())!= null)
						 errorMessages = "TÃªn Ä‘aÌƒ coÌ� trong cÆ¡ sÆ¡Ì‰ dÆ°Ìƒ liÃªÌ£u";
					 else{
				    	 if(diaDiemDuLichDAO.addPlace(placeForm,file))
				    		 errorMessages = "successfully";
				    	 else{
				    		 errorMessages = "loi database";
				    	 }
					 }
		    	 }
		     }
			 System.out.println(errorMessages); 
		 }
//		 if(action != null && action.equals("reset")){
//			 return new ModelAndView("redirect:/addHotel");
//		 }
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/place_add");
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("placeFormAdd", placeForm);
	     return mav;
	 }
	 
	 @RequestMapping(value = {"/addPlace"},method = RequestMethod.GET)
	 public ModelAndView get(@Valid @ModelAttribute("placeFormAdd")PlaceForm placeForm, BindingResult bindingResult, Locale locale,
		@RequestParam(value = "action", required = false) String action,HttpServletRequest request)  throws JsonParseException, JsonMappingException, IOException{
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/place_add");
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("placeFormAdd", placeForm);
	     return mav;
	 }
	 
	 @RequestMapping(value = {"/getPlaceById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getHotelById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String placeId = request.getParameter("placeId");
		 DiaDiemDuLich rs = diaDiemDuLichDAO.getById_Web(placeId);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new DiaDiemDuLich());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/editPlace"}, method = RequestMethod.POST)
	 public ModelAndView edit(@Valid @ModelAttribute("placeFormEdit") PlaceForm placeForm, BindingResult bindingResult, Locale locale,HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 ModelAndView mav = new ModelAndView();
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
	    	 System.out.println(placeForm.getMa());
	    	 DiaDiemDuLich diaDiemDuLich = diaDiemDuLichDAO.getById_Web(placeForm.getMa());
	    	 if(!diaDiemDuLich.getTen().equals(placeForm.getTen())){
		    	 if(diaDiemDuLichDAO.getDiadiemdulichbyTen(placeForm.getTen())==null){
		    		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					 List<MultipartFile> files = multipartRequest.getFiles("fileUpload");
					 MultipartFile file = files.get(0);
		    		 if(diaDiemDuLichDAO.updatePlace(placeForm, file)){
		    			 errorMessages = "successfully";
		    		 }
		    		 else
		    			 errorMessages = "lÃ´Ìƒi database";
		    		 
		    	 }else{
		    		 errorMessages = "Ä�iÌ£a Ä�iÃªÌ‰m Ä‘aÌƒ coÌ� trong dÆ°Ìƒ liÃªÌ£u";
		    	 }
	    	 }else{
	    		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				 List<MultipartFile> files = multipartRequest.getFiles("fileUpload");
				 MultipartFile file = files.get(0);
	    		 if(diaDiemDuLichDAO.updatePlace(placeForm, file)){
	    			 errorMessages = "successfully";
	    		 }
	    		 else
	    			 errorMessages = "lÃ´Ìƒi database";
	    	 }
	    	
	    	
	     }
			mav.setViewName("admin/place");
			mav.addObject("isEdited", true);
			List<DiaDiemDuLich> listPlace = diaDiemDuLichDAO.getAllDiadiemdulich();
			mav.addObject("countPlace",listPlace.size());
			mav.addObject("listPlace", listPlace);
			mav.addObject("placeFormEdit", placeForm);
			mav.addObject("placeFormDelete", new PlaceForm());
			mav.addObject("searchForm", new PlaceForm());
			 mav.addObject("errorMessages", errorMessages);
		    return mav;
	 }
	 
	 
	 @RequestMapping(value = {"/searchPlace"}, method = RequestMethod.GET )
	 public ModelAndView searchPlace(@ModelAttribute("searchForm")PlaceForm placeForm,BindingResult resultHotel,HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward; 
		
		List<DiaDiemDuLich> listDanhsachdiadiem = diaDiemDuLichDAO.getListDiaDiemDuLich(placeForm);
		 ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/place");
		
		mav.addObject("countPlace",listDanhsachdiadiem.size());
		mav.addObject("listPlace", listDanhsachdiadiem);
		mav.addObject("isEdited", false);
		mav.addObject("placeFormEdit", new PlaceForm());
		mav.addObject("placeFormDelete", new PlaceForm());
		mav.addObject("searchForm", new PlaceForm());
	 
		 return mav;
		 
	 }
	 
	 
	 @RequestMapping(value = {"/deletePlace"}, method = RequestMethod.POST )
	 public ModelAndView delete( @ModelAttribute("placeFormDelete") PlaceForm placeForm,HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 String idPlace= placeForm.getMa();
		 diaDiemDuLichDAO.deletePlace(idPlace);
		 String referer = request.getHeader("Referer");
		 int index = referer.indexOf("?");
		 if(index != -1)
			 referer = referer.substring(0,referer.indexOf("?"));
		 
		 //referer += "?action=deleteTour&idtour="+deleteTour.getMa();
		 return new ModelAndView("redirect:" + referer);
	 }
	 
	 @RequestMapping(value = {"/editPlace"},method = RequestMethod.GET)
	 public ModelAndView geteditplace(@Valid @ModelAttribute("placeFormAdd")PlaceForm placeForm, BindingResult bindingResult, Locale locale,
		@RequestParam(value = "action", required = false) String action,HttpServletRequest request)  throws JsonParseException, JsonMappingException, IOException{
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 ModelAndView mav = new ModelAndView();
			mav.setViewName("admin/place");
			
			List<DiaDiemDuLich> listPlace = diaDiemDuLichDAO.getAllDiadiemdulich();
			mav.addObject("countPlace",listPlace.size());
			mav.addObject("listPlace", listPlace);
			mav.addObject("isEdited", false);
			mav.addObject("placeFormEdit", new PlaceForm());
			mav.addObject("placeFormDelete", new PlaceForm());
			mav.addObject("searchForm", new PlaceForm());
			
		    return mav;
	 }
	 
	 

}
