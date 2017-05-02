package com.dulich.model.dao;

import com.dulich.form.TourForm;
import com.dulich.model.pojo.TourThucTe;


public interface TourRealityDAO {
	
	public String createTourReality(TourForm tourForm);
	
	public TourThucTe getTourThucTeByKey(String key_tour_original) ;
	public Boolean updateTourReality(TourForm tourForm);
	
	public TourThucTe getTourThucTebyKeyThucte(String key_tour_reality);

}
