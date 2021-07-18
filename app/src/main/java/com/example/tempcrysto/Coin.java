package com.example.tempcrysto;

import android.graphics.Color;
import android.util.Log;


import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Coin {
    public LineGraphSeries<DataPoint> series24h;
    public LineGraphSeries<DataPoint> series7d;
    public LineGraphSeries<DataPoint> series1y;
    public DataPoint dp;
    public String name_;
    public int id_;
    public Double price_;
    public Double change_h;
    public Double change_d;
    public Double change_y;
    public String iconUrl_;
    public JSONObject array;
    public String response_;
    public int max = 365;

    public Coin(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coinranking1.p.rapidapi.com/coin/" + id) //1,2,3,4,5
                .get()
                .addHeader("x-rapidapi-key", "0958e6b13amsha1af0be4a42f302p1c183ajsndee25287ea1a")
                .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    response_ = response.body().string();
                    array = null;
                    try {
                        array = new JSONObject(response_);
                        array = array.getJSONObject("data");
                        array = array.getJSONObject("coin");
                        name_ = (array.getString("name"));
                        price_ = (array.getDouble("price"));
                        change_h = (array.getDouble("change"));
                        id_ = (array.getInt("id"));
                        iconUrl_ = (array.getString("iconUrl"));
                        iconUrl_=iconUrl_.replace(".svg",".png");
                        series24h=null;
                        series7d=null;
                        series1y=null;
                        series24h = make_series24h(id_);
                        series7d = make_series7d(id_);
                        Thread.sleep(1000);
                        series1y = make_series1y(id_);
                        Thread.sleep(1000);

                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();

                    }
                }
            }
        });
    }
    public Coin(int id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coinranking1.p.rapidapi.com/coin/" + id) //1,2,3,4,5
                .get()
                .addHeader("x-rapidapi-key", "0958e6b13amsha1af0be4a42f302p1c183ajsndee25287ea1a")
                .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    response_ = response.body().string();
                    array = null;
                    try {
                        array = new JSONObject(response_);
                        array = array.getJSONObject("data");
                        array = array.getJSONObject("coin");
                        name_ = (array.getString("name"));
                        price_ = (array.getDouble("price"));
                        change_h = (array.getDouble("change"));
                        id_ = (array.getInt("id"));
                        iconUrl_ = (array.getString("iconUrl"));
                        iconUrl_=iconUrl_.replace(".svg",".png");

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }
        });
    }
    public LineGraphSeries<DataPoint>  make_series7d(int id) throws IOException, JSONException {//LineGraphSeries<DataPoint>
        LineGraphSeries<DataPoint> newOne=new LineGraphSeries<>();
        OkHttpClient client = new OkHttpClient();
        String idStr=String.valueOf(id);
        String re="https://coinranking1.p.rapidapi.com/coin/" +
                idStr +
                "/history/7d";
        Request request = new Request.Builder()
                .url(re)
                .get()
                .addHeader("x-rapidapi-key", "0958e6b13amsha1af0be4a42f302p1c183ajsndee25287ea1a")
                .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String rrr = response.body().string();
        JSONObject jsonObj= new JSONObject(rrr);



        jsonObj = jsonObj.getJSONObject("data");
        change_d = (jsonObj.getDouble("change"));
        Log.d("change_", String.valueOf(change_d));


        JSONArray c =jsonObj.getJSONArray("history");
        int max = c.length();
        for (int i = 0 ; i < c.length(); i++) {
            JSONObject objects = c.getJSONObject(i);
            String priceString =objects.getString("price");
            long time =objects.getLong("timestamp");
            DataPoint Ndp = new DataPoint(Double.parseDouble(String.valueOf(i)), Double.parseDouble(priceString));

            newOne.appendData(Ndp,true,max+1);
            if(change_d<0){
                newOne.setColor(Color.parseColor("#C30C0C")); // red
            }else{
                newOne.setColor(Color.parseColor("#16B626")); // green
            }
        }
        Log.d("make_series7d =>"," finish !");
        return newOne;

    }
    public LineGraphSeries<DataPoint>  make_series1y( int id) throws IOException, JSONException {
        LineGraphSeries<DataPoint> newOne=new LineGraphSeries<>();
        OkHttpClient client = new OkHttpClient();
        String idStr=String.valueOf(id);
        String re="https://coinranking1.p.rapidapi.com/coin/" +
                idStr +
                "/history/1y";
        Request request = new Request.Builder()
                .url(re)
                .get()
                .addHeader("x-rapidapi-key", "0958e6b13amsha1af0be4a42f302p1c183ajsndee25287ea1a")
                .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String rrr = response.body().string();
        JSONObject jsonObj= new JSONObject(rrr);


        jsonObj = jsonObj.getJSONObject("data");
        change_y = (jsonObj.getDouble("change"));
        Log.d("change_", String.valueOf(change_y));


        JSONArray c =jsonObj.getJSONArray("history");
        int max = c.length();
        for (int i = 0 ; i < c.length(); i++) {
            JSONObject objects = c.getJSONObject(i);
            String priceString =objects.getString("price");
            long time =objects.getLong("timestamp");
            DataPoint Ndp = new DataPoint(Double.parseDouble(String.valueOf(i)), Double.parseDouble(priceString));
            newOne.appendData(Ndp,true,max+1 );
            if(change_y<0){
                newOne.setColor(Color.parseColor("#C30C0C")); // red
            }else{
                newOne.setColor(Color.parseColor("#16B626")); // green
            }
        }
        Log.d("make_series1y =>"," finish !");
        return newOne;
    }
    public LineGraphSeries<DataPoint>  make_series24h( int id) throws JSONException, IOException {
        LineGraphSeries<DataPoint> newOne=new LineGraphSeries<>();
        OkHttpClient client = new OkHttpClient();

        String idStr=String.valueOf(id);
        String re="https://coinranking1.p.rapidapi.com/coin/" +
                idStr +
                "/history/24h";
        Request request = new Request.Builder()
                .url(re)
                .get()
                .addHeader("x-rapidapi-key", "0958e6b13amsha1af0be4a42f302p1c183ajsndee25287ea1a")
                .addHeader("x-rapidapi-host", "coinranking1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String rrr = response.body().string();
        JSONObject jsonObj= new JSONObject(rrr);

        jsonObj = jsonObj.getJSONObject("data");
        change_h = (jsonObj.getDouble("change"));
        Log.d("change_", String.valueOf(change_h));

        JSONArray c =jsonObj.getJSONArray("history");
        int max = c.length();
        for (int i = 0 ; i < c.length(); i++) {
            JSONObject objects = c.getJSONObject(i);
            String priceString =objects.getString("price");
            //Log.d("price",priceString);
            long time =objects.getLong("timestamp");
            DataPoint Ndp = new DataPoint(Double.parseDouble(String.valueOf(i)), Double.parseDouble(priceString));
            newOne.appendData(Ndp,true,max+1 );
            if(change_h<0){
                newOne.setColor(Color.parseColor("#C30C0C")); // red
            }else{
                newOne.setColor(Color.parseColor("#16B626")); // green
            }
        }
        Log.d("make_series24h =>"," finish !");
        return newOne;
    }

}