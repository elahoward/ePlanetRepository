package com.ejh.eplanetsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
        String search = prefs.getSearch();
        planetList=getPlanets(search);
        planetRecyclerViewAdapter = new planetRecyclerViewAdapter(this, planetList);
        recycleView.setAdapter(planetRecyclerViewAdapter);

    }

    public List <planet> getPlanets(String searchTerm){
    planetList.clear();
    String myUrl= "https://planets-info-by-newbapi.p.rapidapi.com/?s"+searchTerm+"&r=json";
        JsonObjectRequest jobjectRequest = new JsonObjectRequest(myUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    for (int i = 0; i < 8; i++) {
                        String s = Integer.toString(i);
                        JSONArray planetArray = response.getJSONArray(s);
                    JSONObject planetObj = planetArray.getJSONObject(0);
                    planet planet = new planet();
                    planet.setName(planetObj.getString("name"));
                    planet.setDesc(planetObj.getString("description"));
                    planet.setWiki(planetObj.getString("wikiLink"));
                    planetList.add(planet); //tout cela est probablement faux :(( je le corrigerai
                    }
                    planetRecyclerViewAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws  AuthFailureError
        {
            Map<String, String> params= new HashMap<>();
            params.put ("X-RapidAPI-Host", "planets-info-by-newbapi.p.rapidapi.com");
            params.put ("X-RapidAPI-Key", "84ea392cf5msh0064c776cf95c0cp10e832jsnf107ffbd881e");
            return params;
        }
        };
            rq.add(jobjectRequest);
    return planetList;
    }
}