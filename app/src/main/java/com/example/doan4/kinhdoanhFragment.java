package com.example.doan4;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class kinhdoanhFragment extends Fragment {

    public static String DATABASE_NAME = "tintuc.sqlite";
    SQLiteDatabase database1;
    public static ListView trangttkinhdoanh;
    public static CustemAdapter adapter;
    ArrayList<TinTuc> arr = new ArrayList<>();

    public kinhdoanhFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kinhdoanh, container, false);
        trangttkinhdoanh = (ListView) view.findViewById(R.id.list_kinhdoanh);
        // MyTask myTask = new MyTask();
        new kinhdoanhFragment.MyTask().execute("https://vnexpress.net/rss/kinh-doanh.rss");
//        Toast.makeText(getActivity(),"tieu de"+arr.get(0).tieude,Toast.LENGTH_SHORT).show();
        Log.d("BBB", "doInBackground: "+arr.size());
        trangttkinhdoanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Chitiet.class);
                intent.putExtra("link",arr.get(position));
//                intent.putExtra("link",arr.get(position).getLink());
                startActivity(intent);
                final TinTuc tintuc1= (com.example.doan4.TinTuc) intent.getSerializableExtra("link");
                String tieude=tintuc1.tieude;
                String anh=tintuc1.anh;
                String duongdan=tintuc1.link;
                String tgcapnhat=tintuc1.thoigiancn;

//                database1 = Database.initDatabase(getActivity(), DATABASE_NAME);
//                ContentValues ct = new ContentValues();
//                ct.put("Tieude",tieude);
//                ct.put("Anh",anh);
//                ct.put("Link",duongdan);
//                ct.put("ThoiGian",tgcapnhat);
//
//                database1.insert("history", null, ct);
////               // Toast.makeText(chitiettintuc.this,"B???n ???? l??u tin t???c",Toast.LENGTH_SHORT).show();

            }
        });

        return view;
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
                    tt.setTieude(elm.select("title").text().replaceAll("&#34;","\""));
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

        @Override
        protected void onPostExecute(ArrayList<TinTuc> tintucs) {
            super.onPostExecute(tintucs);
            adapter= new CustemAdapter(getActivity(),R.layout.custom_list_item,arr);
            trangttkinhdoanh.setAdapter(adapter);

        }
    }
}