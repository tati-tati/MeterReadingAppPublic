package com.tatitati.meterreadingapp.ui.main.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.Service;

public class AddServiceModel {
    private Context context;
    private Service service;

    public MutableLiveData<ServiceResult> getServiceResult() {
        return serviceResult;
    }

    private MutableLiveData<ServiceResult> serviceResult = new MutableLiveData<>();

    public AddServiceModel(Context context) {
        this.context = context;
    }

    public void checkValid(String name, String email, String phone){
        if (isEmailValid(email) && isTextFieldValid(name) && isTextFieldValid(phone)){
            saveNewService(name, email, phone);
        } else {
            Toast.makeText(context, "Проверьте введенные данные", Toast.LENGTH_SHORT);
        }
    }

    private boolean isTextFieldValid(String text) {
        if (text == null || text.equals("")) {
            return false;
        } else {
            return !text.trim().isEmpty();
        }
    }

    private boolean isEmailValid(String email){
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    void saveNewService(String name, String email, String phone){
        Service service = new Service();
        service.setServiceName(name);
        service.setEmail(email);
        service.setPhoneNumber(phone);

        new DAO().execute(service);
    }

    private class DAO extends AsyncTask<Service, Void, Result<Service>>{
        @Override
        protected Result<Service> doInBackground(Service... services) {
            Result<Service> result = null;
            for (Service service : services){
                result = UserRepository.getInstance().createService(service);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<Service> result) {
            if (result instanceof Result.Success) {
                Service data = ((Result.Success<Service>) result).getData();
                serviceResult.setValue(new ServiceResult(data));
            } else {
                serviceResult.setValue(new ServiceResult(R.string.meter_reading_not_added));
            }
        }
    }
}
