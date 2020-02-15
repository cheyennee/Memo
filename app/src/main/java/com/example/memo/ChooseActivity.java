package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.memo.DB.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    private ListView listView;
    private ChooseAdapter adapter;
    private List<ChooseItem> items = new ArrayList<>();
    private MyDatabaseHelper helper = new MyDatabaseHelper(this, "Memo.db", null, 1);;
    private int pos;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        listView = findViewById(R.id.listview);
        onInit();
        adapter = new ChooseAdapter(ChooseActivity.this,R.layout.layout_choose_item,items);
        listView.setAdapter(adapter);
        listView.addFooterView(new ViewStub(this));
        delete = findViewById(R.id.delete);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                int index = 0;

                adapter.notifyDataSetChanged();
            }
        });
    }
    private void onInit() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("memotable", null, null, null, null, null, null);
        String content = null, date = null;
        if (cursor.moveToFirst()) {
            do {
                content = cursor.getString(cursor.getColumnIndex("content"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                ChooseItem temp = new ChooseItem();
                temp.setContent(content);
                temp.setDate(date);
                //checkStates.put(i,false);
                items.add(temp);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
