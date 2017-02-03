package com.example.tuantu.downloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ArrayList<DownloadItem> downloadItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listDownload);
        initListDownload();
        DownloadAdapter adapter = new DownloadAdapter(getApplicationContext(), R.layout.item_download, downloadItems);
        list.setAdapter(adapter);
    }

    private void initListDownload() {
        downloadItems = new ArrayList<DownloadItem>();
        downloadItems.add(new DownloadItem("Bước đến bên em", "Trọng Hiếu", "Single", R.drawable.logo, "https://dl.winudf.com/c/APK/1531/01ff021c1260f415.apk?_fn=RmFjZWJvb2tfdjgzLjAuMC4yMC43MV9hcGtwdXJlLmNvbS5hcGs&_p=Y29tLmZhY2Vib29rLmthdGFuYQ%3D%3D&k=b75c487ed54b816fd32f6e26c2ad32c657785977"));
        downloadItems.add(new DownloadItem("Hương đêm bay xa", "Hariwon", "Single", R.drawable.logo1, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Jay_Tee/The_Mini_Album_2010/Imma_Heartbreaker_%28Ft._Emily,_Lil_Knight%29_-_Justatee.mp3"));
        downloadItems.add(new DownloadItem("Sau tất cả", "ERIK ST.319", "Single", R.drawable.logo2, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Yan_Bi/Vu_Vo_2.1_%28Ft._Bueno,_Big_Daddy%29_-_Yanbi.mp3"));
        downloadItems.add(new DownloadItem("Bước đến bên em", "Trọng Hiếu", "Single", R.drawable.logo, "https://dl.winudf.com/c/APK/1531/01ff021c1260f415.apk?_fn=RmFjZWJvb2tfdjgzLjAuMC4yMC43MV9hcGtwdXJlLmNvbS5hcGs&_p=Y29tLmZhY2Vib29rLmthdGFuYQ%3D%3D&k=b75c487ed54b816fd32f6e26c2ad32c657785977"));
        downloadItems.add(new DownloadItem("Hương đêm bay xa", "Hariwon", "Single", R.drawable.logo1, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Jay_Tee/The_Mini_Album_2010/Imma_Heartbreaker_%28Ft._Emily,_Lil_Knight%29_-_Justatee.mp3"));
        downloadItems.add(new DownloadItem("Sau tất cả", "ERIK ST.319", "Single", R.drawable.logo2, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Yan_Bi/Vu_Vo_2.1_%28Ft._Bueno,_Big_Daddy%29_-_Yanbi.mp3"));
        downloadItems.add(new DownloadItem("Bước đến bên em", "Trọng Hiếu", "Single", R.drawable.logo, "https://dl.winudf.com/c/APK/1531/01ff021c1260f415.apk?_fn=RmFjZWJvb2tfdjgzLjAuMC4yMC43MV9hcGtwdXJlLmNvbS5hcGs&_p=Y29tLmZhY2Vib29rLmthdGFuYQ%3D%3D&k=b75c487ed54b816fd32f6e26c2ad32c657785977"));
        downloadItems.add(new DownloadItem("Hương đêm bay xa", "Hariwon", "Single", R.drawable.logo1, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Jay_Tee/The_Mini_Album_2010/Imma_Heartbreaker_%28Ft._Emily,_Lil_Knight%29_-_Justatee.mp3"));
        downloadItems.add(new DownloadItem("Sau tất cả", "ERIK ST.319", "Single", R.drawable.logo2, "http://stream.raphay.com/lofi_mp3/vietnam/rap/Yan_Bi/Vu_Vo_2.1_%28Ft._Bueno,_Big_Daddy%29_-_Yanbi.mp3"));
    }

}