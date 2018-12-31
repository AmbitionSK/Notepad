package com.example.note;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> noteList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 设置toolbar
        setSupportActionBar(toolbar);

        // sqlite
        // 只实例化对象并不会创建数据库，需使用getWritableDatabase()
        dbHelper = new MyDatabaseHelper(this,"Notedb.db",null,1);

        // 点击按钮样式通过两个地方更改
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
                Intent intent = new Intent(MainActivity.this,NotePage.class);
                startActivity(intent);
            }
        });
    }

    private void initNotes(){

        // 创建或者打开可读写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Note",null,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String note = cursor.getString(cursor.getColumnIndex("note"));
                Note Note = new Note();
                Note.setId(id);
                Note.setTitle(title);
                Note.setNote(note);
                noteList.add(Note);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    // 安卓生命周期
    // onCreate() -> onStart()
    @Override
    protected void onStart(){
        super.onStart();

        // 清空noteList 第一次加载
        noteList.clear();
        // 初始化Notes
        this.initNotes();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // 瀑布流模式  错列的gridLayout
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        NoteAdapter adapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(adapter);
    }


}
