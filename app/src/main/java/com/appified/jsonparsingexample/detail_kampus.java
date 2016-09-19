package com.appified.jsonparsingexample;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class detail_kampus extends ActionBarActivity {
    SharedPreferences sp;
    TextView d_idkampus, jurusan;
    SharedPreferences.Editor spe;
    DBHandler handler;
    adapter_jurusan adapter_jur;
    ListView list_jurusan;
    ArrayList<jurusan> jur_array_list;
    String url = "http://ricots.hol.es/copy_jurusan.php?id_kampus=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kampus);
        d_idkampus = (TextView) findViewById(R.id.detail_id_kampus);
        jurusan = (TextView) findViewById(R.id.jurusan);
        list_jurusan = (ListView) findViewById(R.id.list_jurusan);
        sp = this.getSharedPreferences("parameter", 0);
        spe = sp.edit();

        String tp_idkampus = sp.getString("parameter","");
        d_idkampus.setText(tp_idkampus);
        System.out.println("hasilnya "+tp_idkampus);
        final String url_jur = url + d_idkampus.getText().toString();
        handler = new DBHandler(this);
        NetworkUtils utils = new NetworkUtils(detail_kampus.this);

        if(handler.getjur() == 0 && utils.isConnectingToInternet())
        {
            new DataFetcherTask().execute();
        }
        else
        {
            ArrayList<jurusan> jurursan_list = handler.getAlljur();
            adapter_jur = new adapter_jurusan(detail_kampus.this,jurursan_list);
            list_jurusan.setAdapter(adapter_jur);
            adapter_jur.notifyDataSetChanged();
            //list_jurusan.setAdapter(null);
        }
    }

    class DataFetcherTask extends AsyncTask<Void,Void,Void> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(Void... params) {
            String serverData = null;
            // String object to store fetched data from server
            // Http Request Code start
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://beta.json-generator.com/api/json/get/GAqnlDN");
            //final String url_jur = url + d_idkampus.getText().toString();
            HttpGet httpGet = new HttpGet(d_idkampus.getText().toString());
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                serverData = EntityUtils.toString(httpEntity);
                Log.d("response", serverData);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            // Http Request Code end
            // Json Parsing Code Start
            try {
                jur_array_list = new ArrayList<jurusan>();
                JSONObject jsonObject = new JSONObject(serverData);
                JSONArray jsonArray = jsonObject.getJSONArray("jurusan");
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                    String d_jurusan = jsonObjectCity.getString("jurusan");
                    String d_id_kampusnya = jsonObjectCity.getString("id_kampus");
                    jurusan city = new jurusan();
                    city.setJurusan(d_jurusan);
                    city.setIdkampus(d_id_kampusnya);
                    handler.addjur(city);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //adapter_jur.notifyDataSetChanged();
            //Json Parsing code end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<jurusan> jurursan_list = handler.getAlljur();
            adapter_jur = new adapter_jurusan(detail_kampus.this,jurursan_list);
            list_jurusan.setAdapter(adapter_jur);
            adapter_jur.notifyDataSetChanged();
            //list_jurusan.setAdapter(null);
        }
    }
}