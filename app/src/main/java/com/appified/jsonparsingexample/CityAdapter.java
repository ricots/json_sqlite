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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PP on 6/18/2015.
 */
public class CityAdapter extends BaseAdapter {
    Context context;
    ArrayList<City> listData;
    private static CityAdapter mInstance;

    public void onCreate() {
        mInstance = this;
    }

    public CityAdapter(Context context,ArrayList<City> listData){
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
        public TextView textViewCityName,txtVienama_kampus;
        private ImageView logo;
        ListView list;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_item, null);
            viewHolder = new ViewHolder();
            //holder = new ImageHolder();
            viewHolder.textViewCityName = (TextView) view.findViewById(R.id.txtViewCityName);
            viewHolder.txtVienama_kampus = (TextView) view.findViewById(R.id.txtVienama_kampus);
            viewHolder.logo = (ImageView) view.findViewById(R.id.logo);
            viewHolder.list = (ListView) view.findViewById(R.id.listview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            //holder = (ImageHolder)view.getTag();
        }
        //if(imageLoader==null);
        City city = listData.get(position);
        String nama_kampus = city.getState();
        String cityName = city.getName();
        //String logo_kampus = city.getLogo();
        //Log.d("isine ", logo_kampus);

        viewHolder.logo.setImageResource(R.drawable.ic_star_black);
        new DownloadImageTask(viewHolder.logo).execute(listData.get(position).getLogo());
        viewHolder.textViewCityName.setText(cityName);
        viewHolder.txtVienama_kampus.setText(nama_kampus);

      /* viewHolder.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               City map =  listData.get(position);
               String id_kampus = ((TextView) view.findViewById(R.id.txtViewCityName)).getText().toString();
                //String id_kampus = viewHolder.textViewCityName.getText().toString();
               Intent in = new Intent(context,detail_kampus.class);
               Bundle bundle = new Bundle();
               bundle.putString("kirim_id_kampus",id_kampus);
               in.putExtras(bundle);
               view.getContext().startActivity(in);
           }
       });*/
            return view;

        }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }


    }

    public static CityAdapter getInstance() {
        // TODO Auto-generated method stub
        return mInstance;
    }

    static class ImageHolder
    {
        ImageView logo;
        //TextView txtTitle;
    }
}
