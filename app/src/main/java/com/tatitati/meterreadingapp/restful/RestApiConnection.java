package com.tatitati.meterreadingapp.restful;

import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.Service;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiConnection {
    // local comp address
    private static final String url = "http://192.168.1.159:8080/api/";
    private static RestApiConnection connection;
    private static final RestApiService service;

    private RestApiConnection(){}

    public static RestApiConnection getConnection(){
        if (connection == null){
            connection = new RestApiConnection();
        }
        return connection;
    }

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestApiService.class);
    }

    public static void closeConnection(){
        if (connection != null) connection = null;
    }

    public User getUserById(Integer id){
        Call<User> call = service.getUser(id);
        Response<User> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsername(String username){
        Call<User> call = service.getUserByUsername(username);
        Response<User> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(User user){
        Call<User> call = service.createUser(user);
        Response<User> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Meter createMeter(User user, Meter meter){
        Call<Meter> call = service.createMeter(user.getId(), meter);
        Response<Meter> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Meter createMeterReading(Meter meter, MeterReading meterReading){
        Call<Meter> call = service.createMeterReading(meter.getId(), meterReading);
        Response<Meter> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Service createService(Integer userId, Service service1){
        Call<Service> call = service.createService(userId, service1);
        Response<Service> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.body() != null){
                return response.body();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
