package com.dulich.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dulich.model.dao.TourDAO;
import com.dulich.model.pojo.Register;
import com.dulich.model.pojo.Tour;
 
@Component
public class RegisterValidator implements Validator {

	 @Autowired
	 private TourDAO tourDAO;
	
   public boolean supports(Class<?> clazz) {
       return clazz == Register.class;
   }
 
   public void validate(Object target, Errors errors) {
       Register register = (Register) target;
 
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ma_tour", "NotEmpty.bookingForm");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "so_luong", "NotEmpty.bookingForm");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ten_khach_hang", "NotEmpty.bookingForm");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sdt", "NotEmpty.bookingForm");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmnd", "NotEmpty.bookingForm");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dia_chi", "NotEmpty.bookingForm");
      
       if(!register.getSdt().equals(""))
    	   for (int i = 0; i < register.getSdt().length(); i++)
    		   if (register.getSdt().charAt(i) < '0' || register.getSdt().charAt(i) > '9') {
    			   errors.rejectValue("sdt", "Pattern.bookingForm");
    			   break;
    		   }
       
       if (register.getChinh_sua() != null && register.getGhi_chu().equals("")) 
    	   errors.rejectValue("ghi_chu", "Condition.bookingForm");
       
       Tour tour = tourDAO.getTourById_Web(Integer.valueOf(register.getMa_tour()));
       
       if(!register.getSo_luong().equals(""))
    	   if (tour.getSo_ve_con_lai() < Integer.valueOf(register.getSo_luong()))
    		   errors.rejectValue("so_luong", "Condition.soluong.bookingForm");
   }
 
}
