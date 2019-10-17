package com.tatitati.meterreadingapp.ui.main.services;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Service;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceFragmentModel {

    private Context context;


    public ServiceFragmentModel(Context context) {
        this.context = context;
    }

    ServicesListAdapter getServiceListAdapter(){
        User user = UserRepository.getInstance().getUser();
        if (user == null) return null;
        List<Service> serviceList = user.getUserServices();
        Map<String, Service> serviceMap = new HashMap<>();
        for (Service service : serviceList){
            serviceMap.put("serviceName", service);
        }
        return new ServicesListAdapter(context, serviceList);
    }

    ArrayAdapter<String> getAdapter(){
        User user = UserRepository.getInstance().getUser();
        if (user == null) return null;
        List<Service> serviceList = user.getUserServices();
        String[] strings = new String[serviceList.size()];
        int i = 0;
        for (Service service : serviceList){
            strings[i] = service.getServiceName();
            i++;
        }

        return new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, R.id.history_list, strings);
    }
}
