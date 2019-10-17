package com.tatitati.meterreadingapp.ui.main.meterDetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class MeterItemModel {

    private Context context;
    private Meter meter;
    private AlertDialog.Builder ad;
    private String newValue;
    private MutableLiveData<MeterResult> meterResult = new MutableLiveData<>();

    public MeterItemModel(Context context, Meter meter) {
        this.context = context;
        this.meter = meter;
    }

    LiveData<MeterResult> getMeterResult() {
        return meterResult;
    }

    MeterReading getLastMeterReading(Meter meter){
        if (meter.getMeterReadings().size() == 0) return null;
        MeterReading[] currMeterReadings = meter.getMeterReadings().toArray(new MeterReading[]{});
        Arrays.sort(currMeterReadings, Collections.<MeterReading>reverseOrder());

        return currMeterReadings[0];
    }

    Intent getEmailIntent(Meter meter, String value){
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { meter.getmService().getEmail() });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Показания по счетчику");
        User user = UserRepository.getInstance().getUser();
        String body = String.format("%s %s %s, %s: Номер счетчика %s, показания %s",
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getAddress(),
                meter.getMeterNumber(),
                value
                );
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        return emailIntent;
    }

    void openServiceDialog(String value){
        newValue = value;
        ad = new AlertDialog.Builder(context);
        ad.setTitle("Вы отправили показания?");
        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateMeterValue();
            }
        });
        ad.show();
    }

    void updateMeterValue(){
        MeterReading meterReading = new MeterReading();
        meterReading.setValue(Double.parseDouble(newValue));
        meterReading.setDate(Calendar.getInstance().getTimeInMillis());

        new DAO().execute(meterReading);
    }

    private class DAO extends AsyncTask<MeterReading, Void, Result<Meter>> {

        @Override
        protected Result<Meter> doInBackground(MeterReading... meterReadings) {
            Result<Meter> result = null;
            for(MeterReading meterReading : meterReadings){
                result = UserRepository.getInstance().createMeterReading(meter, meterReading);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<Meter> result) {
            if (result instanceof Result.Success) {
                Meter data = ((Result.Success<Meter>) result).getData();
                meterResult.setValue(new MeterResult(data));
            } else {
                meterResult.setValue(new MeterResult(R.string.meter_reading_not_added));
            }
        }
    }
}
