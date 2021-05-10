package com.example.doan4;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Chitiet extends AppCompatActivity {
    ArrayList<TinTuc> arr;
    String link;
    Button xemsau;
    Button yeuthich;
    String DATABASE_NAME = "tintuc1.db";
    SQLiteDatabase database1;


    //    private String filename = "tintuc.txt";
//    private String filepath = "coder.vm";
//    File myInternalFile;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    ArrayList<tintuc> arrluutin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiettintuc);
        arr = new ArrayList<>();
        WebView chitiet = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
       // arr = new ArrayList<>();

       //String link=intent.getStringExtra("link");
        final TinTuc tintuc1 = (com.example.doan4.TinTuc) intent.getSerializableExtra("link");
        link = tintuc1.getLink();
        chitiet.getSettings().setJavaScriptEnabled(true);
        chitiet.loadUrl(link);
        chitiet.setWebViewClient(new WebViewClient());



        xemsau=(Button) findViewById(R.id.btnthemxemsau);
        xemsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude=tintuc1.tieude;
                String anh=tintuc1.anh;
                String duongdan=tintuc1.link;
                String tgcapnhat=tintuc1.thoigiancn;

                database1 = Database.initDatabase(Chitiet.this, DATABASE_NAME);
                ContentValues ct = new ContentValues();
                ct.put("Tieude",tieude);
                ct.put("Anh",anh);
                ct.put("Link",duongdan);
                ct.put("ThoiGiancn",tgcapnhat);

                database1.insert("tintucc", null, ct);
                Toast.makeText(Chitiet.this,"Bạn đã thêm vào xem sau!",Toast.LENGTH_SHORT).show();
            }
        });


        yeuthich=(Button) findViewById(R.id.btnthemyeụthich);
        yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieude=tintuc1.tieude;
                String anh=tintuc1.anh;
                String duongdan=tintuc1.link;
                String tgcapnhat=tintuc1.thoigiancn;

                database1 = Database.initDatabase(Chitiet.this, DATABASE_NAME);
                ContentValues ct = new ContentValues();
                ct.put("Tieude",tieude);
                ct.put("Anh",anh);
                ct.put("Link",duongdan);
                ct.put("ThoiGiancn",tgcapnhat);


                database1.insert("ttintuc", null, ct);
                Toast.makeText(Chitiet.this,"Bạn đã thêm vào yêu thích!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Share(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+ link));
        startActivity(browserIntent);
    }
}
