package com.example.tuantu.imageloader;

import android.graphics.pdf.PdfDocument;

/**
 * Created by TUAN TU on 8/9/2016.
 */
public class PagerItem {
    public String image;
    public String name_vn;
    public String name_en;
    public String point;

    public PagerItem(String image, String name_vn, String name_en, String point)
    {
        this.image = image;
        this.name_vn = name_vn;
        this.name_en = name_en;
        this.point = point;
    }
}
