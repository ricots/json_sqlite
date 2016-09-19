package com.appified.jsonparsingexample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    ListView listView;
    CityAdapter adapter;
    //ArrayList<City> cityArrayList;
    DBHandler handler;
    ImageView logo;
    private List<City> array = new ArrayList<City>();
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    String url = "http://ricots.hol.es/copy_jurusan.php?id_kampus=";
    Spinner spin_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        logo = (ImageView) findViewById(R.id.logo);

        sp = getSharedPreferences("parameter", 0);
        spe = sp.edit();

        String list[]={"silahkan pilih","http://ricots.hol.es/copy_jurusan.php?id_kampus=1","http://ricots.hol.es/copy_jurusan.php?id_kampus=2",
                "http://ricots.hol.es/copy_jurusan.php?id_kampus=3","http://ricots.hol.es/copy_jurusan.php?id_kampus=4",
                "http://ricots.hol.es/copy_jurusan.php?id_kampus=5","http://ricots.hol.es/copy_jurusan.php?id_kampus=6"};
        spin_link = (Spinner) findViewById(R.id.spin_link);
        ArrayAdapter<String> AdapterList = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,list);
        spin_link.setAdapter(AdapterList);

        handler = new DBHandler(this);
        NetworkUtils utils = new NetworkUtils(MainActivity.this);

        if(handler.getCityCount() == 0 && utils.isConnectingToInternet())
        {
            new DataFetcherTask().execute();
        }
        else
        {
            ArrayList<City> cityList = handler.getAllCity();
            adapter = new CityAdapter(MainActivity.this,cityList);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //City map =  array.get(position);

                String tes = spin_link.getSelectedItem().toString();
                String id_kampus = ((TextView) view.findViewById(R.id.txtViewCityName)).getText().toString();
                Intent in = new Intent(MainActivity.this,detail_kampus.class);
                String url_jur = url + id_kampus;
                spe.putString("parameter", tes);
                spe.commit();
                startActivity(in);
            }
        });
    }

    class DataFetcherTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String serverData = null;// String object to store fetched data from server
            // Http Request Code start
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://beta.json-generator.com/api/json/get/GAqnlDN");
            HttpGet httpGet = new HttpGet("http://ricots.hol.es/spiner.php");
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                serverData = EntityUtils.toString(httpEntity);
                Log.d("response", serverData);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Http Request Code end
            // Json Parsing Code Start
            try {
                array = new ArrayList<City>();
                JSONObject jsonObject = new JSONObject(serverData);
                //JSONArray jsonArray = jsonObject.getJSONArray("cities");
                JSONArray jsonArray = jsonObject.getJSONArray("hasil");
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                    String cityName = jsonObjectCity.getString("id_kampus");
                    String cityState = jsonObjectCity.getString("nama_kampus");
                    String cityDescription = jsonObjectCity.getString("alamat_kampus");
                    String logonya = jsonObjectCity.getString("logo");

                    City city = new City();
                    city.setName(cityName);
                    city.setState(cityState);
                    city.setDescription(cityDescription);
                    city.setLogo(logonya);
                    handler.addCity(city);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Json Parsing code end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<City> cityList = handler.getAllCity();
            adapter = new CityAdapter(MainActivity.this,cityList);
            listView.setAdapter(adapter);
        }
    }
}
