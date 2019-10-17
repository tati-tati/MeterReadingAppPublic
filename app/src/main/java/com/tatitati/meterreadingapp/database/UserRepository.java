package com.tatitati.meterreadingapp.database;

import com.tatitati.meterreadingapp.database.database_objects.Meter;
import com.tatitati.meterreadingapp.database.database_objects.MeterReading;
import com.tatitati.meterreadingapp.database.database_objects.Service;
import com.tatitati.meterreadingapp.database.database_objects.User;
import com.tatitati.meterreadingapp.restful.RestApiConnection;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore

    //this is used as an user in cash
    private User user = null;

    // private constructor : singleton access
    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
//        restApiConnection = null;
        RestApiConnection.closeConnection();
//        if (databaseConnection != null) databaseConnection.close();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<User> getUserByUsername(String username){
        this.user = null;
        this.user = RestApiConnection.getConnection().getUserByUsername(username);
        if (user == null) {
            return new Result.Error(new Exception("no user found"));
        }
        return new Result.Success<>(user);
    }

    public Result<User> createUser (User user) {
        this.user = null;
        this.user = RestApiConnection.getConnection().createUser(user);
        if (user == null){
            return new Result.Error(new Exception("user is not signed in"));
        }
        return new Result.Success<>(this.user);
    }

    public Result<User> getUserById(int userId){
        user = null;
        user = RestApiConnection.getConnection().getUserById(userId);
        if (user == null) {
            return new Result.Error(new Exception("no user found"));
        }
        return new Result.Success<>(user);
    }

    public User getUser() {
        return user;
    }

    public Result<Meter> createMeter(Meter meter){
        if (user == null){
            return new Result.Error(new Exception("user not found"));
        }
        if (meter == null){
            return new Result.Error(new Exception("meter not found"));
        }
        List<Meter> userMeters = user.getMeters();
        userMeters.add(meter);
        Meter meterR = RestApiConnection.getConnection().createMeter(user, meter);
        if (meterR == null){
            return new Result.Error(new Exception("no meter added"));
        }
        user = RestApiConnection.getConnection().getUserById(user.getId());
        return new Result.Success<>(meterR);
    }

    public Result<Meter> createMeterReading(Meter meter, MeterReading meterReading){
        if (meter == null){
            return new Result.Error(new Exception("meter not found"));
        }
        Meter meterR = RestApiConnection.getConnection().createMeterReading(meter, meterReading);
        if (meterR == null){
            return new Result.Error(new Exception("no meter added"));
        }
        user = RestApiConnection.getConnection().getUserById(user.getId());
        return new Result.Success<>(meterR);
    }

    public Result<Service> createService(Service service){
        if (service == null){
            return new Result.Error(new Exception("service not found"));
        }
        Service service1 = RestApiConnection.getConnection().createService(user.getId(), service);
        if (service1 == null){
            return new Result.Error(new Exception("no service added"));
        }
        user = RestApiConnection.getConnection().getUserById(user.getId());
        return new Result.Success<>(service1);
    }

}
