package cn.mcandroid.test25.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context ) {
        super(context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table info(" +
                "id integer primary key autoincrement," +
                "msg varchar(1000) )");
        sqLiteDatabase.execSQL("insert into info(msg) values('你好，我叫便签')");
        sqLiteDatabase.execSQL("insert into info(msg) values('编辑完成，点击添加即可。')");
        sqLiteDatabase.execSQL("insert into info(msg) values('选中一条，长按即可删除。')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
