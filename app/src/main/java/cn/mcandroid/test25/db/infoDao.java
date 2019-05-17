package cn.mcandroid.test25.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class infoDao {
    private Context context;
    private MySQLiteOpenHelper helper;

    public infoDao(Context context) {
        this.context = context;
        helper = new MySQLiteOpenHelper(context);
    }

    public boolean addInfo(info info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("msg", info.getMsg());
        long s = db.insert("info", null, values);
        db.close();
        if (s != -1) {
            return true;
        }
        return false;
    }


    public List<info> getALLInfo() {
        List<info> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            info i = new info(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("msg")));
            list.add(i);
            i = null;
        }
        db.close();
        return list;
    }


    public boolean deleteInfo(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String str = "" + id;
        int num = db.delete("info", "id = ?", new String[]{str});
        if (num > 0) {
            return true;
        }
        return false;
    }


    public info getInfoByID(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", null, "id = ?", new String[]{id + ""}, null, null, null);
        info in = null;
        while (cursor.moveToNext()) {
            in = new info(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("msg")));
        }
        db.close();
        return in;
    }

    public boolean updateInfo(info info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuffer sql = new StringBuffer("  1=1 ");
        List list = new ArrayList();
        if (info.getId() != 0) {
            sql.append(" and id= ? ");
            list.add(info.getId());
        }
        if (info.getMsg() != null) {
            sql.append(" and msg = ? ");
            list.add(info.getMsg());
        }
        String[] strarr = new String[list.size()];
        for (int i = 0; i < strarr.length; i++) {
            strarr[i] = list.get(i).toString();
        }
        if (info.getUpid() > 0) {
            values.put("id", info.getUpid());
        }

        if (info.getUpmsg() != null) {
            values.put("msg", info.getUpmsg());
        }

        int num = db.update("info", values, sql.toString(), strarr);
        db.close();
        if (num > 0) {
            return true;
        }
        return false;
    }

}
