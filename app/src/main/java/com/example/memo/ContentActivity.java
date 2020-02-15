package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memo.DB.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentActivity extends AppCompatActivity {

    private TextView date;
    private EditText content;
    private ImageView back;
    private String contentString;
    private MyDatabaseHelper helper = new MyDatabaseHelper(this,"Memo.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);
        date = findViewById(R.id.date);
        back = findViewById(R.id.back);
        content = findViewById(R.id.content);
        setDate();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentString = content.getText().toString();
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if("".equals(contentString)){
                    finish();
                }else{
                    values.put("content",contentString);
                    values.put("date",setDate());
                    db.insert("memotable",null,values);
                    Intent intent = new Intent(ContentActivity.this,MainActivity.class);
                    setResult(1,intent);
                    finish();
                }
            }
        });
    }
    private String setDate(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = sdf.format(now);
        date.setText(dateString);
        return dateString;
    }

    @Override
    public void onBackPressed() {
        contentString = content.getText().toString();
        if("".equals(contentString)){
            finish();
        }else{
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("content",contentString);
            values.put("date",setDate());
            db.insert("memotable",null,values);
            Intent intent = new Intent(ContentActivity.this,MainActivity.class);
            setResult(1,intent);
            finish();
        }
    }
}
