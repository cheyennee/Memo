package com.example.memo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.memo.DB.IntentActivity;
import com.example.memo.DB.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private MyDatabaseHelper helper = new MyDatabaseHelper(this, "Memo.db", null, 1);;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        onInit();
        adapter = new ListViewAdapter(MainActivity.this,R.layout.list_item,items);
        listView.setAdapter(adapter);
        listView.addFooterView(new ViewStub(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, IntentActivity.class);
                intent.putExtra("pos",position);
                pos = position;
                startActivityForResult(intent,2);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("Warning!").setMessage("Are you sure to delete this memo?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = helper.getWritableDatabase();
                                db.delete("memotable","date = ?",new String[]{items.get(position).getDate()});
                                items.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            /*case R.id.choose:
                intent = new Intent(MainActivity.this,ChooseActivity.class);
                startActivity(intent);
                break;*/
            case R.id.add:
                intent = new Intent(MainActivity.this,ContentActivity.class);
                startActivityForResult(intent,1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                SQLiteDatabase db = helper.getWritableDatabase();
                Item temp = new Item();
                String content = null,date = null;
                Cursor cursor = db.query("memotable",null,null,null,null,null,null);
                cursor.moveToLast();
                content = cursor.getString(cursor.getColumnIndex("content"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                cursor.close();
                temp.setContent(content);
                temp.setDate(date);
                items.add(temp);
                adapter.notifyDataSetChanged();
                break;
            case 2:
                String tempContent,tempDate;
                /*tempContent = getIntent().getStringExtra("content");
                tempDate = getIntent().getStringExtra("date");*/
                //tempContent = getIntent().getStringExtra("content");
                //tempDate = getIntent().getStringExtra("date");
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                tempContent = pref.getString("content",null);
                tempDate = pref.getString("date",null);
                items.get(pos).setContent(tempContent);
                items.get(pos).setDate(tempDate);
                //items.get(pos).setContent("hello");
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void onInit() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("memotable", null, null, null, null, null, null);
        String content = null, date = null;
        if (cursor.moveToFirst()) {
            do {
                content = cursor.getString(cursor.getColumnIndex("content"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                Item temp = new Item();
                temp.setContent(content);
                temp.setDate(date);
                items.add(temp);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
