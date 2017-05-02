package com.dulich.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;
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

import com.dulich.form.ChiPhiThucTeForm;
import com.dulich.form.KhachHangForm;
import com.dulich.form.NghiPhepForm;
import com.dulich.form.TourForm;
import com.dulich.form.UserForm;
import com.dulich.model.dao.DanhSachDiaDiemDAO;
import com.dulich.model.dao.DanhSachNhaHangKhachSanDAO;
import com.dulich.model.dao.DanhSachDiaDiemDAOImpl;
import com.dulich.model.dao.DiaDiemDuLichDAO;
import com.dulich.model.dao.KhachHangDAO;
import com.dulich.model.dao.PhieuDangKiDAO;
import com.dulich.model.dao.TourDAO;
import com.dulich.model.dao.UserDAO;

import com.dulich.model.pojo.ChiPhiThucTe;
import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DiaDiemDuLich;
import com.dulich.model.pojo.KhachHang;
import com.dulich.model.pojo.PhieuDangKi;
import com.dulich.model.pojo.RegistrationForm;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.TourThucTe;
import com.dulich.model.pojo.User;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class TourController {
	 @Autowired
	 private PhieuDangKiDAO phieuDangKiDAO;
	 @Autowired
	 private KhachHangDAO khachHangDAO;
	 @Autowired
	 private TourDAO tourDAO;
     @Autowired
     private DanhSachNhaHangKhachSanDAO dsDanhSachNhaHangKhachSanDAO;
	 @Autowired
	 private DiaDiemDuLichDAO diaDiemDuLichDAO;
	 @Autowired
	 private DanhSachDiaDiemDAO dsDiaDiemDuLichDAO;
	 @Autowired
	 private MessageSource messageSource;
	 
	 private String errorMessages = "";
	 
	 private MultipartFile _imageTour = null;
	 private TourForm _createTour;
	 
	 ObjectMapper mapper = new ObjectMapper();
	 private List<TourForm> _listTour_search;
	 private TourForm _tourForm_search;
	 private static String TITLE = "DUYỆT TOUR";
	 private static String title = "duyệtt tour";
     private TourForm _editTourOrigion;
	 
	 @RequestMapping(value = {"/duyet_tour"}, method = RequestMethod.GET )
	 public ModelAndView duyettour_home(TourForm tourForm, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		 
		 _listTour_search = null; 
		 _tourForm_search = new TourForm();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/duyet_tour");
		mav.addObject("countTour", 0);
		mav.addObject("searchTourForm", new TourForm(2));
		mav.addObject("userFormEdit", new UserForm());
		mav.addObject("confirmTourForm", new TourForm());
		mav.addObject("confirmHuyTourForm", new TourForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	 }
	 
	 @RequestMapping(value = {"/searchDuyetTour"}, method = RequestMethod.GET )
	 public ModelAndView searchDuyetTour(@ModelAttribute("searchTourForm") TourForm searchTourForm,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0);
		 if (foward != null)
			 return foward;
		
		int countTour = 0; 
		List<TourForm> listTour = null;
		
		if(action == null){
			listTour = _listTour_search;
		}else if(action.equals("search")){
			listTour = tourDAO.getListTour(searchTourForm);
			_listTour_search = listTour;
			_tourForm_search = searchTourForm;
			if(listTour != null)
				countTour = listTour.size();
		}else if(action.equals("reset")){
			return new ModelAndView("redirect:/duyet_tour");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("admin/duyet_tour");
		mav.addObject("countTour", countTour);
		mav.addObject("listTour", listTour);
		
		mav.addObject("tourForm", _tourForm_search);
		mav.addObject("userFormEdit", new UserForm());
		mav.addObject("confirmTourForm", new TourForm());
		mav.addObject("confirmHuyTourForm", new TourForm());
		mav.addObject("TITLE", TITLE);
		mav.addObject("title", title);
	    return mav;
	 }
	 
	 
	 @RequestMapping(value = {"/comfirmDuyetTour"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String comfirmDuyetTour(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String IdTour = request.getParameter("IdTour");
		 String IdHDV = request.getParameter("IdHDV");
		 tourDAO.duyetTour(Integer.valueOf( IdTour), Integer.valueOf( IdHDV), 3);
		 String jsonInString = "";

		 jsonInString = mapper.writeValueAsString(null);
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/comfirmHuyTour"}, method = RequestMethod.POST )
	 public String comfirmHuyTour( @ModelAttribute("confirmHuyTourForm") TourForm confirmHuyTourForm) {
		 tourDAO.huyTour(confirmHuyTourForm.getMa());
		 String param = "";
		 param = (_tourForm_search != null)? this.makeURLFromForm(_tourForm_search, "search"): "";
		 return "redirect:/searchDuyetTour" + param;
	 }

	 @RequestMapping(value = {"/getKHById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getKHById(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String Id = request.getParameter("Id");
		 KhachHang rs = khachHangDAO.getKhachHang(Id);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new KhachHangForm());
		 }
		 
		 return jsonInString;
	 }
	 
	 @RequestMapping(value = {"/getTourById"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getTourByIdPDK(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 String Id = request.getParameter("Id");
		 TourForm rs = tourDAO.getTourById(Id);
		 String jsonInString = "";
		 if(rs != null){
			 jsonInString = mapper.writeValueAsString(rs);
		 }else{
			 jsonInString = mapper.writeValueAsString(new KhachHangForm());
		 }
		 
		 return jsonInString;
	 }
	 
	 private String makeURLFromForm(TourForm tourForm,String action){
		 //ma=&ten=&ma_huong_dan_vien=&ten_huong_dan_vien
		 String ma = (tourForm.getMa() != null)? tourForm.getMa().toString() : "";
		 String mahdv = (tourForm.getMa_huong_dan_vien() != null)? tourForm.getMa_huong_dan_vien().toString() : "";
		 String trangthai = (tourForm.getTrang_thai() != null)? tourForm.getTrang_thai().toString() : "";
		 String rs = "?";
		 rs += "ma=" + ma + "&";
		 rs += "ma_huong_dan_vien=" + mahdv + "&";
		 rs += "ten=" + tourForm.getTen() + "&";
		 rs += "ten_huong_dan_vien=" + tourForm.getTen_huong_dan_vien() + "&";
		 rs += "trang_thai=" + trangthai + "&";
		 rs += "action=" + action;
		 return rs;
	 }
	 
     @RequestMapping(value = "/addTour",produces = "application/json")
	 public @ResponseBody ModelAndView addTour(@Valid @ModelAttribute("tourFormAdd")TourForm tourForm,BindingResult bindingResult, Locale locale,
             @RequestParam(value = "action", required = false) String action,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		 
		 ModelAndView mav = new ModelAndView();
		 //TourForm tourForm2 = new TourForm();
		 if(action!=null)
			 switch (action) {
				case "chooseplace":
					if (bindingResult.hasErrors()) {
                        errorMessages = new String();
						 for (Object object : bindingResult.getAllErrors()) {
						    if(object instanceof ObjectError) {
						        ObjectError objectError = (ObjectError) object;
						        errorMessages += messageSource.getMessage(objectError.getCodes()[0], null, locale) + "<br>";
						    }
						}
						 errorMessages = String.format(errorMessages);
	                        mav.setViewName("admin/tour_add");
	                        mav.addObject("errorMessages", errorMessages);
	                        mav.addObject("tourFormAdd", tourForm);
	                        mav.addObject("placeList", diaDiemDuLichDAO.getAllDiadiemdulich());

					 }else{
						 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						 try {
							MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
							List<MultipartFile> files = multipartRequest.getFiles("fileUpload");
							MultipartFile file = files.get(0);
							
							Date date_start = formatter.parse(tourForm.getNgay_di());
							
							Date date_end = formatter.parse(tourForm.getNgay_ve());
							if(date_end.compareTo(date_start) <= 0 || file.getSize() <=0){
								
								mav.setViewName("admin/tour_add");
								mav.addObject("tourFormAdd", tourForm);
								mav.addObject("placeList", diaDiemDuLichDAO.getAllDiadiemdulich());
								mav.addObject("flag_error",true);
								if(date_end.compareTo(date_start) <= 0)
									mav.addObject("error_input_add_tour","ngày về phải lớn hơn ngày đi");
								if(file.getSize() <=0)
									mav.addObject("error_input_add_tour","Bạn chưa chọn file");
								
							}
							else{
								 _createTour = tourForm;
								 _imageTour = file;
								 mav.setViewName("admin/choose_place");
								 mav.addObject("placeList", diaDiemDuLichDAO.getAllDiadiemdulich());
							}
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
					break;
				case "add":
					String referer = request.getHeader("Referer");
                    System.out.println(request.getQueryString());
                    
                    String hotel = request.getParameter("hotel");
                    System.out.println(hotel);
                    String restaurant = request.getParameter("restaurant");
                    System.out.println(restaurant);

					int key_tour = tourDAO.addTour(_createTour,_imageTour);
			    	if(key_tour !=- 1){
                        if(dsDiaDiemDuLichDAO.addDsDiaDiem(request.getParameter("checked"),key_tour) && dsDanhSachNhaHangKhachSanDAO.addDsNhahangKhachsan(hotel, restaurant, request.getParameter("checked")))
			    			errorMessages = "successfully";
			    		else
			    			errorMessages = "loi database";
			    	 }else{
			    		 errorMessages = "loi database";
			    	 }
			    	
			    	mav = new  ModelAndView("redirect:" + referer); 
                    mav.addObject("errorMessages", errorMessages);
			    	break;
				case "searchplace":
					 String name = request.getParameter("searchplace");
					 mav.setViewName("admin/choose_place");
					 mav.addObject("placeList", diaDiemDuLichDAO.getDiadiemdulich(name));
					break;
					
				default:
					break;
			 }
		 else{
			 	mav.setViewName("admin/tour_add");
				mav.addObject("tourFormAdd", tourForm);
				mav.addObject("placeList", diaDiemDuLichDAO.getAllDiadiemdulich());
		 }
		
	     return mav;
	 }
	
	@RequestMapping(value = {"/tour_updatechiphi"}, method = RequestMethod.GET )
	 public ModelAndView updateChiPhi(HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 1);
		 if (foward != null)
			 return foward;
		
		 List<Tour> tourHDV = new ArrayList();
		 tourHDV = tourDAO.getListTourByHDV(request.getSession().getAttribute("userId").toString());
				
		 List<ChiPhiThucTe> tourChiPhis = new ArrayList();
		 for (Tour tour : tourHDV)
			 tourChiPhis.add(tourDAO.getChiPhiThucTe(tour.getMa().toString()));
		 
		 int countTour = tourHDV.size();
		 
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("admin/tour_updatechiphi");
		 mav.addObject("countTour", countTour);
		 mav.addObject("tourHDV", tourHDV);
		 mav.addObject("tourChiPhis", tourChiPhis);
		 mav.addObject("chiphiForm", new ChiPhiThucTeForm());
		 mav.addObject("errorMessages", "");
		 mav.addObject("TITLE", "NHẬP CHI PHÍ TOUR");
		 mav.addObject("title", "nhập chi phí tour");
		 return mav;
	 }
	
	@RequestMapping(value = {"/them_chiphi"}, method = RequestMethod.GET )
	 public ModelAndView them_ChiPhi(@ModelAttribute("chiphiForm")ChiPhiThucTeForm chiphiForm,
			 @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 1);
		 if (foward != null)
			 return foward;
		 
		  String errorMessages = "";
		  if (chiphiForm.getMa_tour().equals("0"))
			  errorMessages = "Chưa chọn mã Tour";
		  else
		  if (chiphiForm.getChi_phi_di_chuyen().equals("") || chiphiForm.getChi_phi_an_uong().equals("") ||
				  chiphiForm.getChi_phi_o().equals("") || chiphiForm.getChi_phi_phat_sinh().equals(""))
			 errorMessages = "Không được để trống";
		  else {
			  if (!chiphiForm.getChi_phi_di_chuyen().matches("[0-9]+") || !chiphiForm.getChi_phi_an_uong().matches("[0-9]+") ||
					  !chiphiForm.getChi_phi_o().matches("[0-9]+") || !chiphiForm.getChi_phi_phat_sinh().matches("[0-9]+")) 
				  errorMessages = "Các giá trị phải là số";
			 else
			 {
				 tourDAO.addChiPhiThucTe(chiphiForm);
				 errorMessages = "successfully";
			 }
		 }
		 
		 List<Tour> tourHDV = new ArrayList();
		 tourHDV = tourDAO.getListTourByHDV(action);
				
		 List<ChiPhiThucTe> tourChiPhis = new ArrayList();
		 for (Tour tour : tourHDV)
			 tourChiPhis.add(tourDAO.getChiPhiThucTe(tour.getMa().toString()));
		 
		 int countTour = tourHDV.size();
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 mav.setViewName("admin/tour_updatechiphi");
		 mav.addObject("idUser", action);
		 mav.addObject("countTour", countTour);
		 mav.addObject("tourHDV", tourHDV);
		 mav.addObject("tourChiPhis", tourChiPhis);
		 mav.addObject("chiphiForm", chiphiForm);
		 mav.addObject("errorMessages", errorMessages);
		 mav.addObject("TITLE", "NHẬP CHI PHÍ TOUR");
		 mav.addObject("title", "nhập chi phí tour");
		 return mav;
	}
	 
	 @RequestMapping("/tour")
	    public ModelAndView setupForm(TourForm tourForm, @RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
			 ModelAndView foward = UserController.Authen(request, 0, 2);
			 if (foward != null)
				 return foward;
		ModelAndView mav = new ModelAndView();

		mav.setViewName("admin/tour");
        TourForm tourTemp = new TourForm();
        this.copyTourFormTemp(_editTourOrigion, tourTemp);
        mav.addObject("tourFormEdit",tourTemp);    
        
        mav.addObject("tourFormDelete",new TourForm());    
        
        if(action!=null){
            switch (action) {
            case "searchtour":
                String name = request.getParameter("searchtour");
                List<Tour> listTour = tourDAO.getListTourbyString(name);
                mav.addObject("countTour",listTour.size());
                mav.addObject("listTour", listTour);
                mav.addObject("isEdited", false);
                break;
            case "editTourOrigion":
                List<Tour> listTour1 = tourDAO.getListTour();
                mav.addObject("countTour",listTour1.size());
                mav.addObject("listTour", listTour1);
                mav.addObject("errorMessages", errorMessages);
                mav.addObject("isEdited", true);
                String idtour = request.getParameter("idtour");
                List<DanhSachDiaDiem> listDsDiaDiem = dsDiaDiemDuLichDAO.getListByTourId_Web(idtour);
                List<DiaDiemDuLich> listDiaDiem = new ArrayList<DiaDiemDuLich>();
                for(int i = 0; i< listDsDiaDiem.size(); i++){
                     DiaDiemDuLich diaDiemDuLich = new DiaDiemDuLich();
                     diaDiemDuLich = diaDiemDuLichDAO.getById_Web(listDsDiaDiem.get(i).getKey().getMa_dia_diem().toString());
                     listDiaDiem.add(diaDiemDuLich); 
                }
                
                mav.addObject("placeListbyId", listDiaDiem);
                break;

            default:
                break;
            }
        }
        else{
            List<Tour> listTour = tourDAO.getListTour();
            mav.addObject("countTour",listTour.size());
            mav.addObject("listTour", listTour);
            mav.addObject("isEdited", false);
        }

		return mav;
		
	 }
	 
	 @RequestMapping(value = {"/getInformationTour"}, method = RequestMethod.GET, produces="application/json",headers="Accept=*/*")
	 public @ResponseBody String getInformationTour(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		 mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		 
		 String id_tour = request.getParameter("idTour");
		 System.out.println(id_tour);
		 List<DanhSachDiaDiem> listDsDiaDiem = dsDiaDiemDuLichDAO.getListByTourId_Web(id_tour);
		 List<DiaDiemDuLich> listDiaDiem = new ArrayList<DiaDiemDuLich>();
		 for(int i = 0; i< listDsDiaDiem.size(); i++){
			 DiaDiemDuLich diaDiemDuLich = new DiaDiemDuLich();
			 diaDiemDuLich = diaDiemDuLichDAO.getById_Web(listDsDiaDiem.get(i).getKey().getMa_dia_diem().toString());
			 listDiaDiem.add(diaDiemDuLich); 
		 }
		 
		 Tour rs = tourDAO.getTourByKey(id_tour);
		
		 String jsonInString = "";
		 String jsonInString1 = "";
		
		 List<String> list = new ArrayList<String>();
		 jsonInString = mapper.writeValueAsString(listDiaDiem);
		 list.add(jsonInString);
		 jsonInString1 = mapper.writeValueAsString(rs);
		 list.add(jsonInString1);
		 String result = mapper.writeValueAsString(list);
		 return result;
	 }
	 
	 @RequestMapping(value = {"/editTourOrigion"},method = RequestMethod.POST)
	 public  @ResponseBody ModelAndView  editTourOrigion(@Valid @ModelAttribute("tourFormEdit") TourForm tourForm, BindingResult bindingResult, Locale locale,HttpServletRequest request) {
	       String referer = request.getHeader("Referer");

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
		    	 tourDAO.updateTourOrigion(tourForm);
		    	 errorMessages = "successfully";
		     }
             int index = referer.indexOf("?");
             if(index != -1)
                 referer = referer.substring(0,referer.indexOf("?"));
             
             referer += "?action=editTourOrigion&idtour="+tourForm.getMa();
             _editTourOrigion = tourForm;
             tourForm = null;

	       return new ModelAndView("redirect:" + referer);
	 }
	 
	 @RequestMapping(value = {"/searchplace"}, method = RequestMethod.GET )
	 public ModelAndView search(@RequestParam(value = "action", required = false) String action, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
		 
		 System.out.println(request.getQueryString());
		 
		 ModelAndView mav = new ModelAndView();
		
		 return mav;
	 } 
	 
	 private void copyTourFormTemp(TourForm src,TourForm desc){
        if(src != null){
            desc.setNgay_ve(src.getNgay_ve());
            desc.setNgay_di(src.getNgay_ve());
            desc.setPhuong_tien(src.getPhuong_tien());
            desc.setGioi_thieu(src.getGioi_thieu());
            desc.setMa_tour_goc(src.getMa_tour_goc());
            desc.setTen(src.getTen());
            desc.setChuong_trinh(src.getChuong_trinh());
            desc.setSo_ve_con_lai(src.getSo_ve_con_lai());
            desc.setSo_ve(src.getSo_ve());
            desc.setGia(src.getGia());
            desc.setMa(src.getMa());
        }
	 }
	 
     @RequestMapping(value = {"/deleteTour"}, method = RequestMethod.POST )
     public ModelAndView delete( @ModelAttribute("tourFormDelete") TourForm deleteTour, HttpServletRequest request) {
		 ModelAndView foward = UserController.Authen(request, 0, 2);
		 if (foward != null)
			 return foward;
         
         tourDAO.huyTour(deleteTour.getMa());
         String referer = request.getHeader("Referer");
         int index = referer.indexOf("?");
         if(index != -1)
             referer = referer.substring(0,referer.indexOf("?"));
         
         //referer += "?action=deleteTour&idtour="+deleteTour.getMa();
         return new ModelAndView("redirect:" + referer);
     }
	    
}