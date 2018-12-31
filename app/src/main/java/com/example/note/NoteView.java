package com.example.note;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 编辑页面
 */

public class NoteView extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.del:
                
                // 设置删除事件
                Intent intent = getIntent();
                final String id = ""+intent.getIntExtra("id",-1);
                dbHelper = new MyDatabaseHelper(this,"Notedb.db",null,1);
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Note","id = ?",new String[]{id});
                NoteView.this.finish();

                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                break;
        }
        return true;
    }

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

        Intent intent = getIntent();
        final String id = ""+intent.getIntExtra("id",-1);
        String TITLE = intent.getStringExtra("title");
        String NOTE = intent.getStringExtra("note");
        title.setText(TITLE);
        note.setText(NOTE);

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
                    db.update("Note",values,"id = ?",new String[]{id});

                    Toast.makeText(NoteView.this, "保存成功", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
