package com.spybug.sudokusolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    int[] data;
    Context context;
    LayoutInflater layoutInfater;

    public BoardAdapter(Context context) {
        super();
        //this.data = data;
        data = new int[81];
        for (int i = 1; i < 82; i++) {
            data[i - 1] = i;
        }
        this.context = context;
        layoutInfater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        }
        else {
            textView = (TextView) convertView;
        }
        textView.setText(data[position] + "");
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        return textView;
    }
}
