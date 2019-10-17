package com.tatitati.meterreadingapp.ui.main.meters;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterType;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MetersFragmentModel {

    private UserRepository repository;


    public MetersFragmentModel(){
        repository = UserRepository.getInstance();
    }

    ArrayList<Map<String, String>> getMeterTypes(User user){
        ArrayList<Map<String, String>> typeHeaders = new ArrayList<>();
        List<Meter> meters = user.getMeters();
        List<String> typeList = new ArrayList<>();
        for (Meter meter:meters) {
            if (!typeList.contains(meter.getMeterType().getType())){
                typeList.add(meter.getMeterType().getType());
            }
        }
        Arrays.sort(typeList.toArray());

        for (String type : typeList){
            HashMap<String, String> map = new HashMap<>();
            map.put("meterType", type);
            typeHeaders.add(map);
        }
        return typeHeaders;
    }

    ArrayList<ArrayList<Map<String, String>>> getMetersByType(User user){
        List<Meter> meters = user.getMeters();
        List<String> typeList = new ArrayList<>();
        for (Meter meter:meters) {
            if (!typeList.contains(meter.getMeterType().getType())){
                typeList.add(meter.getMeterType().getType());
            }
        }
        Arrays.sort(typeList.toArray());

        ArrayList<ArrayList<Map<String, String>>> child = new ArrayList<>();
        ArrayList<Map<String, String>> childList;

        for (String type : typeList){
            childList = new ArrayList<>();
            for (Meter meter : meters){
                if (meter.getMeterType().getType().equals(type)){
                    Map<String, String> tempMap = new HashMap<>();
                    tempMap.put("meterNumber", meter.getMeterNumber());
                    childList.add(tempMap);
                }
            }
            child.add(childList);
        }
        return child;
    }

    Map<String, MeterType> getMeterTypeMap(User user){
        List<Meter> meters = user.getMeters();
        Map<String, MeterType> map = new HashMap<>();
        for (Meter meter : meters){
            if (!map.keySet().contains(meter.getMeterType().getType())){
                map.put(meter.getMeterType().getType(), meter.getMeterType());
            }
        }
        return map;
    }

    Map<String, Meter> getMeterMap(User user){
        List<Meter> meters = user.getMeters();
        Map<String, Meter> map = new HashMap<>();
        for (Meter meter : meters){
            if (!map.keySet().contains(meter.getMeterNumber())){
                map.put(meter.getMeterNumber(), meter);
            }
        }
        return map;
    }

    MetersListAdapter getArrayAdapter(Context context, FragmentManager fragmentManager){
        User user = UserRepository.getInstance().getUser();
        if (user == null) return null;


        MetersListAdapter adapter = new MetersListAdapter(context, fragmentManager);
        adapter.setGroupList(getMeterTypes(user));
        adapter.setChildList(getMetersByType(user));
        adapter.setMeterTypeMap(getMeterTypeMap(user));
        adapter.setMeterMap(getMeterMap(user));

        return adapter;
    }


}
