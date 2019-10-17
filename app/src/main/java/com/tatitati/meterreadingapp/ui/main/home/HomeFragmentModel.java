package com.tatitati.meterreadingapp.ui.main.home;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragmentModel {

    private Context context;

    public HomeFragmentModel(Context context) {
        this.context = context;
    }

    ArrayAdapter<String> getAdapter(){
        User user = UserRepository.getInstance().getUser();
        if (user == null) return null;

        List<Meter> meterList = user.getMeters();
        List<String> strings = new ArrayList<>();
        for(Meter meter : meterList){
            if(meter.getMeterReadings().size() == 0) continue;
            MeterReading[] currMeterReadings = meter.getMeterReadings().toArray(new MeterReading[]{});
            Arrays.sort(currMeterReadings, Collections.<MeterReading>reverseOrder());
            Date date = new Date(currMeterReadings[0].getDate());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            strings.add(meter.getMeterType().getType() + ": №" + meter.getMeterNumber() + " " + formatter.format(date) + " - " + currMeterReadings[0].getValue());
        }

        return new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strings.toArray(new String[]{}));
    }
}
