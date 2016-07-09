package com.jspiders.moblie.imdbratingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    ListView movieslv;
    ArrayList<String> titles;
    ImageView posteriv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieslv =(ListView) findViewById(R.id.lvmovies);
        posteriv = (ImageView) findViewById(R.id.ivposter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://www.omdbapi.com/?s=Batman&page=2", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                String jsonresponse = new String(responseBody);
                //responsetv.setText(s1);
                Gson gson = new Gson();
                Response responseobj = gson.fromJson(jsonresponse,Response.class);
                ArrayList<Response.Search> list =(ArrayList<Response.Search>) responseobj.getSearch();

                titles = new ArrayList<String>();

                for (Response.Search s:list)
                {
                    Log.d("[MOVIES TITLE]",s.getTitle());
                    titles.add(s.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,titles);
                movieslv.setAdapter(adapter);

                Response.Search s1 =  list.get(0);
                Picasso.with(MainActivity.this).load(s1.getPoster()).into(posteriv);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

            }
        });

    }
}
