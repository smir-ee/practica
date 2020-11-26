package com.example.bezmetnyybank;

import com.example.bezmetnyybank.entities.BanksEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UaParserJson {

    private ArrayList<BanksEntity> banksArrayList;

    public UaParserJson(){
        banksArrayList = new ArrayList<>();
    }

    public ArrayList<BanksEntity> getBanksArrayList() {
        return banksArrayList;
    }

    public boolean parsingData(String json){
        boolean status = true;

        try{
            JSONObject main = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(main.getString("devices"));

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                BanksEntity banks = new BanksEntity();

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
