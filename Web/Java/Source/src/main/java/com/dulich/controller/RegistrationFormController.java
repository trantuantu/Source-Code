package com.dulich.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.dulich.form.AcceptRegistrationForm;
import com.dulich.form.CustomerForm;
import com.dulich.form.HotelForm;
import com.dulich.form.SearchRegistrationForm;
import com.dulich.form.TourForm;
import com.dulich.model.dao.CustomerDAO;
import com.dulich.model.dao.RegistrationFormDAO;
import com.dulich.model.dao.TourDAO;
import com.dulich.model.dao.TourRealityDAO;
import com.dulich.model.dao.UserDAO;
import com.dulich.model.pojo.Customer;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.RegistrationForm;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.TourThucTe;

@Controller
public class RegistrationFormController {
	@Autowired
	private RegistrationFormDAO registrationFormDao;
	@Autowired
	private TourDAO tourDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Autowired(required=true)
	private TourRealityDAO tourRealityDAO;
	
	ObjectMapper mapper = new ObjectMapper();
	private String errorMessages = "";
	private String errorMessagesTour = "";
	private String errorMessagesApprova = "";
	private String isEdited = "false";
	private String isEditedTour = "false";
	private String isApproval = "false";
	@Autowired
	private MessageSource messageSource;
	private CustomerForm _editCustomer;
	private TourForm _editTourReality;
	private static String TITLE = "PHIẾU ĐĂNG KÍ";
	private static String title = "Phiếu Đăng Kí";
	
	//private Boolean statusTour = true;
	
	@RequestMapping("/registrations")
	public ModelAndView setupForm(RegistrationForm registrationForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 2);
		 if (foward != null)
			 return foward;
		ModelAndView mav = new ModelAndView("redirect:" + "/registrations");
		mav.setViewName("admin/registrations");
		//RegistrationForm registrationForm = new RegistrationForm();
		//map.put("registrationForm", registrationForm);
		mav.addObject("registrationFormList", registrationFormDao.getAllRegistration());
		mav.addObject("searchRegistrationForm",new SearchRegistrationForm(2,1,1));
		//map.put("registrationFormList", registrationFormService.getAllRegistration());
		//Tour a = tourDAO.getTourByKey("1");
		mav.addObject("acceptRegistration", new AcceptRegistrationForm());
		mav.addObject("deleteRegistration", new AcceptRegistrationForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
		
		return mav;
		
	}
	
	 @RequestMapping(value = {"/searchRegistration"}, method = RequestMethod.GET )
	 public ModelAndView search(@ModelAttribute("searchRegistrationForm")SearchRegistrationForm searchRegistrationForm, BindingResult resultHotel,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 2);
		 if (foward != null)
			 return foward;
		 
		 ModelAndView mav = new ModelAndView();
		 List<RegistrationForm> listRegistration = new ArrayList<RegistrationForm>();
		 listRegistration = registrationFormDao.getListRegistrationConditional(searchRegistrationForm);
		 mav.setViewName("admin/registrations");
		 mav.addObject("registrationFormList", listRegistration);
		 mav.addObject("searchRegistrationForm",new SearchRegistrationForm(2,1,1));
		 //map.put("registrationFormList", registrationFormService.getAllRegistration());
		 //Tour a = tourDAO.getTourByKey("1");
		 mav.addObject("deleteRegistration", new AcceptRegistrationForm());
		 mav.addObject("acceptRegistration", new AcceptRegistrationForm());
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
	 
		 return mav;
		 
	 }
	
	
	private void copyCustomerFormTemp(CustomerForm src, CustomerForm desc){
		 if(src != null){
			 desc.setMa(src.getMa());
			 desc.setAddress(src.getAddress());
			 desc.setCMND(src.getCMND());
			 desc.setName(src.getName());
			 desc.setPhone(src.getPhone()); 
		 }
		 
	 }
	private void copyTourFormTemp(TourForm src,TourForm desc){
		if(src != null){
			desc.setNgay_ve(src.getNgay_ve());
			desc.setNgay_di(src.getNgay_ve());
			desc.setPhuong_tien(src.getPhuong_tien());
			desc.setGioi_thieu(src.getGioi_thieu());
			desc.setMa_phieu_dang_ki(src.getMa_phieu_dang_ki());
			desc.setMa_tour_goc(src.getMa_tour_goc());
			desc.setTen(src.getTen());
			desc.setChuong_trinh(src.getChuong_trinh());
			desc.setSo_ve_con_lai(src.getSo_ve_con_lai());
			desc.setSo_ve(src.getSo_ve());
			desc.setGia(src.getGia());
		}
	}
	@RequestMapping(value = "/registration.do",method = RequestMethod.POST)
	public String doActions(@ModelAttribute("registrationForm") RegistrationForm registrationForm,BindingResult result,@RequestParam String action,Map<String,Object>map){
		RegistrationForm registrationFormResult = new RegistrationForm();
		switch(action.toLowerCase()){
			case "edit":
				System.out.println(action.toLowerCase());
				registrationFormDao.edit(registrationForm);
				registrationFormResult = registrationForm;
				break;
		
		}
		map.put("registrationForm", registrationFormResult);
		map.put("registrationFormList", registrationFormDao.getAllRegistration());
		return "admin/registration";
	}
	
	 @RequestMapping(value = {"/informationRegistration"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getInformation(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String key_user = request.getParameter("key_user");
		 String key_tour = request.getParameter("key_tour");
		
		 String jsonInString = "";
		 String jsonInString1 = "";
		 List<String> list = new ArrayList<String>();
		 Tour tour = tourDAO.getTourByKey(key_tour);
		 if(tour != null){
			 jsonInString = mapper.writeValueAsString(tour);
		 }else{
			 jsonInString = mapper.writeValueAsString(new Hotel());
			 }
		 list.add(jsonInString);
		 Customer customer = customerDAO.getCustomerByKey(key_user);
		 if(customer != null){
			 jsonInString1 = mapper.writeValueAsString(customer);
		 }else{
			 jsonInString1 = mapper.writeValueAsString(new Hotel());
		 }
		 list.add(jsonInString1);
		 String result = mapper.writeValueAsString(list);
		 
		 return result;
	 }
	 @RequestMapping(value = {"/getInformationRegistration"}, method = RequestMethod.GET)
	 public @ResponseBody ModelAndView getFormInformation(HttpServletRequest request, @RequestParam(value = "action", required = false) String action) throws JsonGenerationException, IOException {

		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/information");
		 String key_user = request.getParameter("key_user");
		 String key_tour = request.getParameter("key_tour");
		 String key_registration = request.getParameter("key_registration");
		 RegistrationForm registrationForm = registrationFormDao.getRegistration(key_registration);
         int status = registrationForm.getIs_edit_tour();
		 Tour tour = tourDAO.getTourByKey(key_tour);
		 registrationForm.setTotal_money(registrationForm.getCount_people() * tour.getGia());
		 mav.addObject("acceptRegistration", new AcceptRegistrationForm());
		 mav.addObject("customer", customerDAO.getCustomerByKey(key_user));
         if(status==2){
             mav.addObject("tour",tourRealityDAO.getTourThucTeByKey(key_tour));
         }else{
             mav.addObject("tour", tourDAO.getTourByKey(key_tour));
         }
         mav.addObject("key_tour_goc",key_tour);
        // mav.addObject("tour", tourDAO.getTourByKey(key_tour));     
		 mav.addObject("registration",registrationForm);
		 mav.addObject("TITLE", TITLE);
		 mav.addObject("title", title);
		 CustomerForm temp = new CustomerForm();
		 this.copyCustomerFormTemp(_editCustomer, temp); 
		 mav.addObject("editCustomer", temp);
		 
		 TourForm tourTemp = new TourForm();
		 this.copyTourFormTemp(_editTourReality, tourTemp);
		 mav.addObject("editTourReality", tourTemp);
		 
		 //mav.addObject("statusTour", statusTour);
		 errorMessages =( action == null)? "" : errorMessages;
		 mav.addObject("errorMessages", errorMessages);
		 
		 errorMessagesTour =( action == null)? "" : errorMessagesTour;
		 mav.addObject("errorMessagesTour", errorMessagesTour);
		 
		 errorMessagesApprova =( action == null)? "" : errorMessagesApprova;
		 mav.addObject("errorMessagesApprova", errorMessagesApprova);
		 
		 isEdited = (action != null && action.equals("editCustomer"))? "true" : "false";
		 mav.addObject("isEdited", isEdited);
		 
		 isEditedTour = (action != null && action.equals("editTourReality"))? "true" : "false";
		 mav.addObject("isEditedTour", isEditedTour);
		 
		 isApproval = (action != null && action.equals("sumit"))? "true" : "false";
		 mav.addObject("isApproval", isApproval);
		
		 return mav;
	 }
	 public static String makeUrl(HttpServletRequest request)
	 {
	     return request.getRequestURL().toString() + "?" + request.getQueryString();
	 }
	 
	 @RequestMapping(value = {"/informationCustomer"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getHotelById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String key_user = request.getParameter("key_user");
		 Customer customer = customerDAO.getCustomerByKey(key_user);
		 String jsonInString = "";
		 if(customer != null){
			 jsonInString = mapper.writeValueAsString(customer);
		 }else{
			 jsonInString = mapper.writeValueAsString(new Customer());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/informationTour"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getInforTour(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String key_tour = request.getParameter("key_tour");
		 
		 String key_registration = request.getParameter("key_registration");
		 RegistrationForm registrationForm = registrationFormDao.getRegistration(key_registration);
		 
		 String jsonInString = "";
		 if(registrationForm.getIs_edit_tour()==2){
			 TourThucTe tourReality = tourRealityDAO.getTourThucTeByKey(key_tour);
			 if(tourReality != null){
				 jsonInString = mapper.writeValueAsString(tourReality);
			 }else{
				 jsonInString = mapper.writeValueAsString(new Tour());
			 }
		 }
		 else
		 {
			 Tour tour = tourDAO.getTourByKey(key_tour);
			 if(tour != null){
				 jsonInString = mapper.writeValueAsString(tour);
			 }else{
				 jsonInString = mapper.writeValueAsString(new Tour());
			 }
		 }
			 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/editCustomer"},method = RequestMethod.POST)
	 public  @ResponseBody void  editCustomer( CustomerForm customerForm,HttpServletRequest request, BindingResult bindingResult, Locale locale) {
	       String pName = customerForm.getName();
	       //String lName = customerForm.getCMND();
	       System.out.println(pName);
	       System.out.println("?" + request.getQueryString());
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
		    	 if(customerDAO.updateCustomer(customerForm))
		    		 errorMessages = "successfully";
		    	 //isEdited = "true";
		     }
			 _editCustomer = customerForm;
			 customerForm = null;
			 //System.out.println(errorMessages); 
	   }
	 
	 
	 @RequestMapping(value = {"/editTourReality"},method = RequestMethod.POST)
	 public  @ResponseBody ModelAndView  editTourReality( TourForm tourForm,HttpServletRequest request, BindingResult bindingResult, Locale locale) {
	     	String key_retristation = tourForm.getMa_phieu_dang_ki();
	       String referer = request.getHeader("Referer");
	       
	       try {
	    	   MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(referer)).build().getQueryParams();
	    	   List<String> action= parameters.get("action");
	    	   if(action != null && action.size()>0){
	    		   referer = referer.substring(0,referer.indexOf("&action"));
	    	   }
	       } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
	       
	       System.out.println(referer);
	       if(registrationFormDao.flagEnabledEditing(key_retristation) ){
	    	   tourRealityDAO.createTourReality(tourForm);
	    	   referer += "&action=createTourReality";
	       }
	       else
	       {
	    	   errorMessagesTour = new String();	 
	    	   if (bindingResult.hasErrors()) {
				 for (Object object : bindingResult.getAllErrors()) {
				    if(object instanceof ObjectError) {
				        ObjectError objectError = (ObjectError) object;
				        errorMessagesTour += messageSource.getMessage(objectError.getCodes()[0], null, locale) + "<br>";
				    }
				}
					 errorMessages = String.format(errorMessages);
			   }else{
			    	 if(tourRealityDAO.updateTourReality(tourForm))
			    		 errorMessagesTour = "successfully";
			   	}
	    	   _editTourReality = tourForm;
	    	   tourForm = null;
	    	   referer += "&action=editTourReality";
	       }
	       System.out.println(referer);
	       return new ModelAndView("redirect:" + referer);
	 }
	 
	 @RequestMapping(value = {"/approvalRegistration"},method = RequestMethod.POST)
	 public  @ResponseBody ModelAndView  approvalRegistration( AcceptRegistrationForm acceptRegistrationForm,HttpServletRequest request, BindingResult bindingResult, Locale locale) {
		 System.out.println(acceptRegistrationForm.getKey_tour());
		 System.out.println(acceptRegistrationForm.getKey_registration());
		 
		 String referer = request.getHeader("Referer");
		 try {
	    	   MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(referer)).build().getQueryParams();
	    	   List<String> action= parameters.get("action");
	    	   if(action != null && action.size()>0){
	    		   referer = referer.substring(0,referer.indexOf("&action"));
	    	   }
	       } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
		 ModelAndView modelViewResult =  new ModelAndView("redirect:" + referer);
		 
		 RegistrationForm registrationForm = registrationFormDao.getRegistration(acceptRegistrationForm.getKey_registration());
		 int editcase = registrationForm.getIs_edit_tour();
		 System.out.println("editcase: " +editcase);
		 switch (editcase) {
		 	case 0:
		 		 Tour tour1 = tourDAO.getTourByKey(acceptRegistrationForm.getKey_tour());
		 		 int total_money = tour1.getGia() * registrationForm.getCount_people();
				 int deposit = acceptRegistrationForm.getDeposit();
				 if(deposit != total_money){
					 referer += "&action=sumit";
					 errorMessagesApprova = "Khách Hàng phải đóng tiền đầy đủ";
					 
					 modelViewResult =  new ModelAndView("redirect:" + referer); 
				 }
				 else{
					if(registrationFormDao.updateRegistration(acceptRegistrationForm)){
						String url = request.getRequestURL().toString();
						url = 	url.substring(0,url.indexOf("approvalRegistration"));
						url += "registrations";
						modelViewResult =  new ModelAndView("redirect:" + url); 
					}else{
						 referer += "&action=sumit";
						 errorMessagesApprova = "Lỗi database";
						 modelViewResult =  new ModelAndView("redirect:" + referer); 
					}
				 }
				 break;
		 	case 1:
		 		 referer += "&action=sumit";
				 errorMessagesApprova = "Bạn phải chỉnh sữa lại tour theo yêu cầu của khách hàng";
				 modelViewResult =  new ModelAndView("redirect:" + referer); 
				 break;
				
		 	case 2:
		 		TourThucTe tourThucTe = tourRealityDAO.getTourThucTebyKeyThucte(acceptRegistrationForm.getKey_tour());
		 		int ma_tour_goc = tourThucTe.getMa_tour_goc();
		 		Tour tour = tourDAO.getTourByKey(String.valueOf(ma_tour_goc));
		 		int total_money1 = tour.getGia() * registrationForm.getCount_people();
				int deposit1 = acceptRegistrationForm.getDeposit();
				if(deposit1 > total_money1){
					referer += "&action=sumit";
					errorMessagesApprova = "Bạn nhập số tiền sai(lớn hơn tổng tiền)";
					modelViewResult =  new ModelAndView("redirect:" + referer); 
				}
				else{
					int status = acceptRegistrationForm.getStatus();
					if(deposit1 < total_money1 /2){
						referer += "&action=sumit";
						errorMessagesApprova = "Khách hàng phải đóng tiền cọc >= 50% tổng tiền";
						modelViewResult =  new ModelAndView("redirect:" + referer); 	
					}else{
						if(status == 1 && deposit1 != total_money1){
							referer += "&action=sumit";
							errorMessagesApprova = "Khách hàng lần này phải đóng đủ số tiền";
							modelViewResult =  new ModelAndView("redirect:" + referer); 
						}else{
							if(registrationFormDao.updateRegistration(acceptRegistrationForm)){
								String url = request.getRequestURL().toString();
								url = 	url.substring(0,url.indexOf("approvalRegistration"));
								url += "registrations";
								modelViewResult =  new ModelAndView("redirect:" + url); 
							}else{
								 referer += "&action=sumit";
								 errorMessagesApprova = "Lỗi database";
								 modelViewResult =  new ModelAndView("redirect:" + referer); 
							}
						}
					}
				}			
		}
		 return modelViewResult;
	   }
	 
	 @RequestMapping(value = {"/deleteRegistration"}, method = RequestMethod.POST )
	 public ModelAndView delete( @ModelAttribute("deleteRegistration") AcceptRegistrationForm deleteRegistration, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 2);
		 if (foward != null)
			 return foward;
		 String idRegistration = deleteRegistration.getKey_registration();
		 registrationFormDao.deleteRegistration(idRegistration);
//		 userForm = null;
		
		 String referer = request.getHeader("Referer");
		 try {
	    	   MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUri(new URI(referer)).build().getQueryParams();
	    	   List<String> action= parameters.get("action");
	    	   if(action != null && action.size()>0){
	    		   referer = referer.substring(0,referer.indexOf("&action"));
	    	   }
	       } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	       }
		 return new ModelAndView("redirect:" + referer);
	 }
}
