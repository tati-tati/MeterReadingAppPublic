package com.tatitati.meterreadingapp.restful;

import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.Service;
import com.tatitati.meterreadingapp.database.database_objects.User;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.http.*;

public interface RestApiService {
    @GET("{id}")
    Call<User> getUser(@Path("id") Integer id);

    @GET("users/")
    Call<User> getUserByUsername(@Query("name") String username);

    @POST("create")
    Call<User> createUser(@Body User user);

    @DELETE("{id}")
    Call<User> deleteUser(@Path("id") Integer id);

    @PUT("{id}")
    Call<User> updateUser(@Path("id") Integer id);

    @PUT("{id}/meters/create")
    Call<Meter> createMeter(@Path("id") Integer id, @Body Meter meter);

    @PUT("meters/{id}/createReading")
    Call<Meter> createMeterReading(@Path("id") Integer id, @Body MeterReading meterReading);

    @PUT("{id}/services/create")
    Call<Service> createService(@Path("id") Integer id, @Body Service service);
}
