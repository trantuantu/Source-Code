package com.dulich.model.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dulich.form.PlaceForm;
import com.dulich.model.pojo.DiaDiemDuLich;


public interface DiaDiemDuLichDAO {
	public DiaDiemDuLich getById_Web(String id);
	public List<DiaDiemDuLich> getAllDiadiemdulich();
	public List<DiaDiemDuLich> getDiadiemdulich(String ten);
    public Boolean addPlace(PlaceForm placeForm,MultipartFile file); 
    public DiaDiemDuLich getDiadiemdulichbyTen(String ten);
    public Boolean updatePlace(PlaceForm placeForm,MultipartFile file);
    public List<DiaDiemDuLich> getListDiaDiemDuLich(PlaceForm placeForm);
    public Boolean deletePlace(String id);

}
