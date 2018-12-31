package com.example.note;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;



public class MyDatabaseHelper extends SQLiteOpenHelper {


    private Context mContext;

    public  MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override

    /**
     * 创建数据库  创建时自动调用
     */
    public void onCreate(SQLiteDatabase db){

            String sql = "create table Note (" +
                "id integer primary key autoincrement," +
                "title text," +
                "note text) ";
        db.execSQL(sql);
    }


    /**
     * 更新数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }

}
