package com.tatitati.meterreadingapp.ui.main.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.Service;

import java.util.List;

public class ServicesListAdapter extends BaseAdapter {

    private Context context;
    private List<Service> items;

    public ServicesListAdapter(Context context, List<Service> items) {
        this.context= context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.service_item, null);
        }
        TextView serviceName = view.findViewById(R.id.service_name);
        TextView email = view.findViewById(R.id.service_email);
        TextView phone = view.findViewById(R.id.service_phone);
        Service curService = (Service) getItem(i);
        serviceName.setText(curService.getServiceName());
        email.setText(curService.getEmail());
        phone.setText(curService.getPhoneNumber());
        return view;
    }
}
