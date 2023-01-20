package com.ejh.eplanetsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ejh.eplanetsapp.Data.planetRecyclerViewAdapter;
import com.ejh.eplanetsapp.Model.planet;
import com.ejh.eplanetsapp.R;
import com.ejh.eplanetsapp.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleView;
    private planetRecyclerViewAdapter planetRecyclerViewAdapter;
    private List<planet> planetList;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq= Volley.newRequestQueue(this);
        recycleView=findViewById(R.id.recycleView);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        planetList= new ArrayList<>();

        Prefs prefs = new Prefs(MainActivity.this);
        planetList=getPlanets();
        planetRecyclerViewAdapter = new planetRecyclerViewAdapter(this, planetList);
        recycleView.setAdapter(planetRecyclerViewAdapter);
    }
    public List <planet> getPlanets(){
    planetList.clear();
    String myUrl= "https://planets-info-by-newbapi.p.rapidapi.com/api/v1/planet/list";
        JsonArrayRequest jarrayRequest = new JsonArrayRequest(myUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                        for (int s = 0; s < 8; s++) {
                            JSONObject planetObj = response.getJSONObject(s);
                            planet planet = new planet();
                            planet.setName(planetObj.getString("name"));
                            planet.setDesc(planetObj.getString("description"));
                            planet.setWiki(planetObj.getString("wikiLink"));
                            planet.setImage(planetObj.getJSONArray("imgSrc").getJSONObject(0).getString("img"));
                            planetList.add(planet);
                            Log.d("jason",planet.getName());
                        }
                    planetRecyclerViewAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    planetRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "oh no" +error);

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws  AuthFailureError
        {
            Map<String, String> params= new HashMap<>();
            params.put("X-RapidAPI-Host", "planets-info-by-newbapi.p.rapidapi.com");
            params.put("X-RapidAPI-Key", "84ea392cf5msh0064c776cf95c0cp10e832jsnf107ffbd881e");
            return params;

        }
        };
            rq.add(jarrayRequest);
    return planetList;
    }
}