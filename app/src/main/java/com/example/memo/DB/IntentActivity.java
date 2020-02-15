package com.example.memo.DB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.memo.MainActivity;
import com.example.memo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentActivity extends AppCompatActivity {

    private TextView date;
    private EditText content;
    private ImageView back;
    private String contentString,dateString;
    private MyDatabaseHelper helper = new MyDatabaseHelper(this,"Memo.db",null,1);
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        pos = getIntent().getIntExtra("pos",0);
        setContentView(R.layout.activity_content);
        date = findViewById(R.id.date);
        back = findViewById(R.id.back);
        content = findViewById(R.id.content);
        content.setSelection(content.getSelectionEnd());
        set();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempContent = content.getText().toString();
                if(tempContent.equals(contentString)){
                    finish();
                }else{
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String nowTime = sdf.format(now);
                    values.put("date",nowTime);
                    values.put("content",tempContent);
                    db.update("memotable",values,"content = ?",new String[]{contentString});
                    //Intent intent = new Intent(IntentActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("content",tempContent);
                    editor.putString("date",nowTime);
                    editor.commit();
                    /*Intent intent = new Intent();
                    setResult(2);
                    intent.putExtra("content",tempContent);
                    intent.putExtra("date",nowTime);*/
                    finish();
                }
            }
        });
    }
    private void set(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("memotable",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            for(int i=0;i<pos;i++)
                cursor.moveToNext();
            contentString = cursor.getString(cursor.getColumnIndex("content"));
            dateString = cursor.getString(cursor.getColumnIndex("date"));
            content.setText(contentString);
            date.setText(dateString);
            cursor.close();
        }
    }

    @Override
    public void onBackPressed() {
        String tempContent = content.getText().toString();
        if(tempContent.equals(contentString)){
            finish();
        }else{
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String nowTime = sdf.format(now);
            values.put("date",nowTime);
            values.put("content",tempContent);
            db.update("memotable",values,"content = ?",new String[]{contentString});
            Intent intent = new Intent(IntentActivity.this, MainActivity.class);
            setResult(2);
            intent.putExtra("content",tempContent);
            intent.putExtra("date",nowTime);
            finish();
        }
    }
}
