package com.example.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 添加页面
 */

public class NotePage extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new MyDatabaseHelper(this,"Notedb.db",null,1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final EditText title = (EditText) findViewById(R.id.input_title);
        final EditText note = (EditText) findViewById(R.id.input_note);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().equals("")){
                    Snackbar.make(view, "请输入标题", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else if(note.getText().toString().equals("")){
                    Snackbar.make(view, "请输入内容", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    ContentValues values = new ContentValues();
                    values.put("title",title.getText().toString());
                    values.put("note",note.getText().toString());
                    db.insert("Note",null,values);

                    Toast.makeText(NotePage.this, "保存成功", Toast.LENGTH_SHORT).show();
                    NotePage.this.finish();
                }

            }
        });
    }

}
