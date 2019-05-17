package cn.mcandroid.test25;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.mcandroid.test25.adapter.MyArrayAdapter;
import cn.mcandroid.test25.db.info;
import cn.mcandroid.test25.db.infoDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private EditText ed;
    private TextView tv;
    private boolean isAction;
    private ListView lv;
    private infoDao dao;
    private List<info> list;

    private int IDBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.ed_msg);
        tv = (TextView) findViewById(R.id.tv);
        lv = (ListView) findViewById(R.id.lv);
        ed.setText(ReadSP("a"));
        tv.setOnClickListener(this);
        ed.addTextChangedListener(new MyTextWatcher());
        dao = new infoDao(this);
        setListView();


    }

    private void setListView() {
        list = dao.getALLInfo();
        String data[] = new String[list.size()];
        for (int i = 0; i < data.length; i++) {
            info in = list.get(i);
            data[i] = in.getMsg();
            in = null;
        }
        MyArrayAdapter adapter = new MyArrayAdapter(this, data, R.layout.arr_item);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                info info = dao.getInfoByID(list.get(i).getId());
                ed.setText(info.getMsg());
                tv.setText("提交");
                isAction = true;
                IDBuffer = info.getId();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String str = ed.getText().toString();
        writhSP("a", str);
    }



    private void writhSP(String fileName, String str) {
        SharedPreferences.Editor sp = getSharedPreferences(fileName, MODE_PRIVATE).edit();
        sp.putString("msg", str);
        sp.apply();
    }


    private String ReadSP(String fileName) {
        SharedPreferences sp = getSharedPreferences(fileName, MODE_PRIVATE);
        return sp.getString("msg", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                if (isAction) {
                    handleUpClick();
                }
                handleClick();

                break;
            default:
                break;
        }

    }

    private void handleUpClick() {
        if(IDBuffer>0){
            info info=new info();
            info.setId(IDBuffer);
            info.setUpmsg(ed.getText().toString());
            if (dao.updateInfo(info)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示").setMessage("修改成功").show();
                setListView();
                ed.setText("");
                isAction=false;
                tv.setText("添加");
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("警告").setMessage("修改失败").show();
                setListView();

            }
        }


    }


    private void handleClick() {
        String str = ed.getText().toString();
        if (!str.equals("")) {
            info info = new info();
            info.setMsg(str);
            if (dao.addInfo(info)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示").setMessage("添加成功").show();
                setListView();
                ed.setText("");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("警告").setMessage("添加失败").show();
                setListView();
            }


        }

    }




    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        final int id = list.get(i).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        String str = list.get(i).getMsg();
        if (str.length() > 6) {
            str = str.substring(0, 5) + "...";
        }
        builder.setTitle("是否删除?").setMessage("“" + str + "”" + "\n这条记录，删除将无法撤销!")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNeutralButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dao.deleteInfo(id)) {
                    Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                    setListView();
                } else {
                    Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
        return true;
    }

    private class  MyTextWatcher implements  TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()==0){
                isAction=false;
                tv.setText("添加");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    }
}
