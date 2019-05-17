package cn.mcandroid.test25.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.mcandroid.test25.R;

public class MyArrayAdapter extends BaseAdapter {
    private String[] str;
    private Context context;
    private int LayoutId;

    public MyArrayAdapter(Context context, String[] data, int LayoutId) {
        this.context = context;
        str = data;
        this.LayoutId = LayoutId;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = (TextView) View.inflate(context, LayoutId, null);
        tv.setText(str[i]);
        return tv;
    }
}
