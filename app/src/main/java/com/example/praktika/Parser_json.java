package com.example.praktika;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser_json {
    private ArrayList<Entity_Banks> banksArrayList;

    public Parser_json(){
        banksArrayList = new ArrayList<>();
    }

    public ArrayList<Entity_Banks> getBanksArrayList() {
        return banksArrayList;
    }

    public boolean parsingData(String json){
        boolean status = true;

        try{
            JSONObject main = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(main.getString("devices"));

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                Entity_Banks banks = new Entity_Banks();

                banks.setCity(jsonObject.getString("cityRU"));
                banks.setAddress(jsonObject.getString("fullAddressRu"));
                banks.setPlace(jsonObject.getString("placeRu"));
                banks.setType(jsonObject.getString("type"));
                banks.setTimeWork(jsonObject.getString("tw"));
                banks.setPosition(jsonObject.getDouble("longitude"), jsonObject.getDouble("latitude"));

                banksArrayList.add(banks);
            }
        } catch (JSONException e) {
            status = false;
        }

        return status;
    }
}
