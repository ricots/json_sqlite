package com.appified.jsonparsingexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PP on 6/18/2015.
 */
public class adapter_jurusan extends BaseAdapter {
    Context context;
    ArrayList<jurusan> listData;
    private static adapter_jurusan mInstance;
    //DBHandler dbHandler;

    public void onCreate() {
        mInstance = this;
    }

    public adapter_jurusan(Context context, ArrayList<jurusan> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        public TextView detailid_kampus,jurusan;
        ListView list_jurursan;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_jurusan, null);
            viewHolder = new ViewHolder();
            //holder = new ImageHolder();
            viewHolder.detailid_kampus = (TextView) view.findViewById(R.id.detailid_kampus);
            viewHolder.jurusan = (TextView) view.findViewById(R.id.jurusan);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            //holder = (ImageHolder)view.getTag();
        }
        //if(imageLoader==null);
        jurusan jr = listData.get(position);
        String id_kampusnya = jr.getIdkampus();
        String jurursannya = jr.getJurusan();
        //String logo_kampus = city.getLogo();
        //Log.d("isine ", logo_kampus);
        viewHolder.detailid_kampus.setText(id_kampusnya);
        viewHolder.jurusan.setText(jurursannya);
        return view;

        }
}
