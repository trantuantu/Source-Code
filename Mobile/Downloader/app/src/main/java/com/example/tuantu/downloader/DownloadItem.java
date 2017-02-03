package com.example.tuantu.downloader;

/**
 * Created by TUAN TU on 6/30/2016.
 */
public class DownloadItem {
    public String fileName;
    public String title1;
    public String title2;
    public String link;
    public int imageId;
    public boolean isDownload;

    DownloadItem(String fileName, String title1, String title2, int imageId, String link)
    {
        this.fileName = fileName;
        this.title1 = title1;
        this.title2 = title2;
        this.imageId = imageId;
        this.link = link;
    }
}