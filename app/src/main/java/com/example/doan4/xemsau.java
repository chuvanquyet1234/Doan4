package com.example.doan4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan4.Chitiet;
import com.example.doan4.CustemAdapter;
import com.example.doan4.R;
import com.example.doan4.TinTuc;

import java.util.ArrayList;

public class  xemsau extends AppCompatActivity {
    int pos;
    TextView tieudett;
    String DATABASE_NAME = "tintuc1.db";
    SQLiteDatabase database1;
    Toolbar toolbar;
    ListView xemsau;
    CustemAdapter adapter;
    ArrayList<TinTuc> arr=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemsau);
        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Tin tức xem sau!");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        tieudett=(TextView) findViewById(R.id.txttieude);
        xemsau=(ListView) findViewById(R.id.list_xemsau);

        readdata();
        registerForContextMenu(xemsau);
        xemsau.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                return false;
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menuthemxoa, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemxem:
                Intent intent=new Intent(xemsau.this, Chitiet.class);
                intent.putExtra("link",arr.get(pos));
                startActivity(intent);
                break;
            case R.id.itemxoa:
                final AlertDialog.Builder al = new AlertDialog.Builder(xemsau.this);
                al.setTitle("Thông Báo");
                al.setMessage("Bạn có chắc chắn muốn xóa tin tức này ?");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tieude = arr.get(pos).tieude;
                        database1 = Database.initDatabase(xemsau.this, DATABASE_NAME);
                        String sql = "Delete from tintucc where Tieude='" +tieude + "'";
                        database1.execSQL(sql);
                        readdata();
                        // adapter.notifyDataSetChanged();
                        Toast.makeText(xemsau.this,"Đã Xoá!!!",Toast.LENGTH_SHORT).show();
                    }
                });
                al.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(xemsau.this,"Đã huỷ!!",Toast.LENGTH_SHORT).show();

                    }
                });
                al.create().show();

        }


        return super.onContextItemSelected(item);
    }

    private void readdata() {

        database1 = Database.initDatabase(xemsau.this, DATABASE_NAME);
        Cursor cursor = database1.rawQuery("select * from tintucc", null);
        arr.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tieude = cursor.getString(0);
            String anh = cursor.getString(2);
            String link = cursor.getString(1);
            String thoigiancn = cursor.getString(3);
            arr.add(new TinTuc(tieude,anh,link,thoigiancn));
        }
        cursor.close();
        //   if (arr.size()>0) {
//            Log.d("DDD", "readdata: " + arr.size()+"-"+arr.get(1).getLink());
        adapter = new CustemAdapter(xemsau.this, android.R.layout.simple_list_item_1, arr);
        xemsau.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        }else {
//            Toast.makeText(luutintuc.this, "không có tin tức nào đươc lưu", Toast.LENGTH_SHORT).show();
//        }
    }

}
