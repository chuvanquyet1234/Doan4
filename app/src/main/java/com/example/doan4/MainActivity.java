package com.example.doan4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.doan4.R.*;
import static com.example.doan4.R.id.drawerlayout;

public class MainActivity extends AppCompatActivity {
    ListView tc,tk;
    DrawerLayout menu;
    CustemAdapter adapter;
    public TabLayout mtablrlayout;
    private ViewPager mviewpager;
    private Viewpageradapter viewpageradapter;
    EditText timkiem;
    ArrayList<TinTuc> arrTimkiem;
    ArrayList<TinTuc> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        mtablrlayout.setupWithViewPager(mviewpager);
        addViewpager(mviewpager);
        new MyTask().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");

        mtablrlayout.setupWithViewPager(mviewpager);

        tk = (ListView) findViewById(R.id.list_new);

        addViewpager(mviewpager);
        viewpageradapter = new Viewpageradapter(getSupportFragmentManager());
        TinTuc tt = new TinTuc();
//        mic=(ImageButton) findViewById(R.id.btnmic);
//        mic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent speIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                speIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                speIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-en");
//              //  speIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"vi");
//                try{
//                    startActivityForResult(speIntent,RECOGNIZER_RESULT);
//                    timkiem.setText("");
//                }catch (ActivityNotFoundException e){
//                    Toast.makeText(getApplicationContext(),"không hỗ trợ",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//
//            }
//        });

        timkiem = (EditText) findViewById(R.id.edttimkiem);
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                tintrongngayFragment tintrongngayFragment = new tintrongngayFragment();

                s = timkiem.getText().toString().toUpperCase();
                String d = s.toString().toUpperCase();
//                Toast.makeText(TrangChu.this,"có;"+d,Toast.LENGTH_SHORT).show();
                arrTimkiem=new ArrayList<>();
                if (!d.equals("")) {
                    for (int i = 0; i < arr.size(); i++) {
                        TinTuc tt = arr.get(i);
                        String ten = tt.tieude.toUpperCase().trim();
                        if (ten.contains(d)) {
                            arrTimkiem.add(tt);
                        }
                    }
                    if (arrTimkiem.size() > 0) {
//                     Toast.makeText(TrangChu.this,"có",Toast.LENGTH_SHORT).show();
                        mviewpager.setVisibility(View.GONE);
                        adapter = new CustemAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrTimkiem);
                        tc.setAdapter(adapter);
                        tc.setVisibility(View.VISIBLE);
                    }
                    else {
//                        Toast.makeText(TrangChu.this,"không có từ khóa cần tìm",Toast.LENGTH_SHORT).show();
                        tc.setVisibility(View.GONE);
                        mviewpager.setVisibility(View.VISIBLE);
                    }
                } else {
                    tc.setVisibility(View.GONE);
                    mviewpager.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        tc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,Chitiet.class);
                Toast.makeText(MainActivity.this,arrTimkiem.get(position).getLink(),Toast.LENGTH_SHORT).show();
                intent.putExtra("link",arrTimkiem.get(position));
                startActivity(intent);
            }
        });
    }


    private void initViewPager() {
        ViewPager viewPager = findViewById(id.view);
        TabLayout tabLayout = findViewById(id.tab);
        tc = (ListView) findViewById(R.id.listview);
        menu = (DrawerLayout) findViewById(drawerlayout);
        mtablrlayout = findViewById(R.id.tab);
        mviewpager = findViewById(R.id.view);
        arr = new ArrayList<>();

    }
    private void addViewpager(ViewPager viewPager) {
        Viewpageradapter viewpageradapter = new Viewpageradapter(getSupportFragmentManager());
        viewpageradapter.addTab(new TintuctrongngayFragment(), "Tin Trong Ngày");
        viewpageradapter.addTab(new thegioiFragment(), "Thế Giới");
        viewpageradapter.addTab(new thoisuFragment(), "Thời Sự");
        viewpageradapter.addTab(new suckhoeFragment(), "Sức Khoẻ");
        viewpageradapter.addTab(new thethaoFragment(), "Thể Thao");
        viewpageradapter.addTab(new xeFragment(), "Xe");
       viewpageradapter.addTab(new startupFragment(), "Startup");
        viewpageradapter.addTab(new dulichFragment(), "Du lịch");
        viewpageradapter.addTab(new kinhdoanhFragment(), "Kinh Doanh");
       viewPager.setAdapter(viewpageradapter);
    }


    public void dsxemsau(View view) {
        Intent it=new Intent(MainActivity.this,xemsau.class);
        startActivity(it);
    }

    public void dsthongtin(View view) {
            Intent it= new Intent(MainActivity.this,thongtin.class);
            startActivity(it);

    }

    public void dsyeuthich(View view) {
        Intent it=new Intent(MainActivity.this,yeuthich.class);
        startActivity(it);
    }

    class MyTask extends AsyncTask<String, Void, ArrayList<TinTuc>> {

        @Override
        protected ArrayList<TinTuc> doInBackground(String... strings) {
//
            try {
                Document document = Jsoup.connect(strings[0]).get();
                Elements element = document.select("item");
                TinTuc tt = null;
                for (Element elm : element) {
                    tt = new TinTuc();
                    tt.setTieude(elm.select("title").text().replaceAll("&#34;", "\""));
                    tt.setAnh(Jsoup.parse(elm.select("description").text()).select("img").attr("src"));
                    tt.setLink(elm.select("link").text());
                    tt.setThoigiancn(elm.select("pubDate").text());
                    arr.add(tt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return arr;
        }
    }


}