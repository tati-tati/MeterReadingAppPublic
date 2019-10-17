package com.tatitati.meterreadingapp.ui.main.meterDetails;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterType;
import com.tatitati.meterreadingapp.database.database_objects.Service;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeterDetailsFragmentModel {
    private Context context;
    private AlertDialog.Builder ad;
    private MutableLiveData<MeterResult> meterResult = new MutableLiveData<>();
    private MutableLiveData<String> meterState = new MutableLiveData<>();
    private MutableLiveData<String> selectedService = new MutableLiveData<>();

    public MeterDetailsFragmentModel(Context context){
        this.context = context;
    }

    LiveData<String> getMeterState() {
        return meterState;
    }

    LiveData<String> getSelectedService() {
        return selectedService;
    }

    LiveData<MeterResult> getMeterResult() {
        return meterResult;
    }

    void openDateDialog(DatePickerDialog.OnDateSetListener dateSetListener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, dateSetListener, year, month, day);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    void openServiceDialog(){
        List<String> strings = new ArrayList<>();
        for (Service service : getServiceList()){
            strings.add(service.getServiceName());
        }
        final String[] services = strings.toArray(new String[]{});
        ad = new AlertDialog.Builder(context);
        ad.setTitle("Компании");
        ad.setItems(services, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selectedService.setValue(services[which]);
            }
        });
        ad.show();
    }

    Service findService(String serviceName){
        User user = UserRepository.getInstance().getUser();
        List<Service> services = user.getUserServices();
        for (Service service : services){
            if (service.getServiceName() == serviceName) return service;
        }
        return null;
    }

    List<Service> getServiceList(){
        User user = UserRepository.getInstance().getUser();
        if (user == null) return null;
        return user.getUserServices();
    }

    void saveMeter(int day, int month, int year, String number, MeterType type, Service service){
        if(number == null){
            meterState.setValue("Неверный номер счетчика");
            return;
        }
        if (type == null) {
            meterState.setValue("Выберите тип ресурса");
            return;
        }
        if (day == 0 && month == 0 && year == 0){
            meterState.setValue("Неверная дата");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(day, month, year);
        Meter meter = new Meter(calendar.getTimeInMillis(), number, type);

        new DAO().execute(meter);
    }

    private class DAO extends AsyncTask<Meter, Void, Result<Meter>>{

        @Override
        protected Result<Meter> doInBackground(Meter... meters) {
            Result<Meter> result = null;
            for(Meter meter : meters){
                result = UserRepository.getInstance().createMeter(meter);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<Meter> result) {
            if (result instanceof Result.Success) {
                Meter data = ((Result.Success<Meter>) result).getData();
                meterResult.setValue(new MeterResult(data));
            } else {
                meterResult.setValue(new MeterResult(R.string.meter_not_added));
            }
        }
    }
}
