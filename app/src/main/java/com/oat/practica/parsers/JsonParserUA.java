package com.oat.practica.parsers;

import com.oat.practica.entities.EntityBanks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParserUA {
    private ArrayList<EntityBanks> banksArrayList;

    public JsonParserUA(){
        banksArrayList = new ArrayList<>();
    }

    public ArrayList<EntityBanks> getBanksArrayList() {
        return banksArrayList;
    }

    public boolean parsingData(String json){
        boolean status = true;

        try{
            JSONObject main = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(main.getString("devices"));

            for (int i =0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                EntityBanks banks = new EntityBanks();

                banks.setCity(jsonObject.getString("cityRU"));
                banks.setAddress(jsonObject.getString("fullAddressRu"));
                banks.setPlace(jsonObject.getString("placeRu"));
                banks.setType(jsonObject.getString("type"));
                banks.setTimeWork(jsonObject.getString("tw"));

                banksArrayList.add(banks);
            }
        } catch (JSONException e) {
            status = false;
        }

        return status;
    }
}
