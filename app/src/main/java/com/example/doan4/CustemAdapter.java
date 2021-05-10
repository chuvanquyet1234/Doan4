package com.example.doan4;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustemAdapter extends ArrayAdapter<TinTuc> {
    Context context;
    int resource;
    ArrayList<TinTuc> arr;

    public CustemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TinTuc> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arr= (ArrayList<TinTuc>) objects;
    }



    @Override
    public int getCount() { return arr.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.custom_list_item,null);
        //ánh xạ
        ImageView anhtt = (ImageView) row.findViewById(R.id.anhtrangchu);
        TextView tieudett=(TextView) row.findViewById(R.id.txttieude);
        TextView thoigian=(TextView) row.findViewById(R.id.thoigian);

       TinTuc tt=arr.get(position);
        tieudett.setText(tt.tieude);
        thoigian.setText(tt.thoigiancn);
        if (!tt.anh.equals("")){
            Picasso.get().load(tt.anh).into(anhtt);}

        return row;
    }
    public void sortTintuc(String s){
        s=s.toUpperCase();
        int k=0;
        for (int i=0;i<arr.size();i++){
            TinTuc
                    a=arr.get(i);
            String ten=a.tieude.toUpperCase();
            if(ten.equals(s)){
                arr.set(k,a);
                k++;
            }
        }
        notifyDataSetChanged();
    }
}
