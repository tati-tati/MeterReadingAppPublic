package com.tatitati.meterreadingapp.ui.registrate;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;


import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.database.database_objects.User;
import com.tatitati.meterreadingapp.database.UserRepository;


public class RegistrateViewModel extends ViewModel {

    private MutableLiveData<RegistrateFormState> registrateFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrateResult> registrateResult = new MutableLiveData<>();
    private UserRepository userRepository;

    RegistrateViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<RegistrateFormState> getRegistrateFormState() {
        return registrateFormState;
    }

    LiveData<RegistrateResult> getRegistrateResult() {
        return registrateResult;
    }

    public void registrate(String firstName, @Nullable String middleName, String lastName,
                           String address, String username, String email, String password){
        User user = new User();
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        new ClassDAO().execute(user);
    }

    public void registrateDataChanged(String firstName, @Nullable String middleNameString, String lastName,
                                 String username, String email, String password) {

        if (!isTextFieldValid(lastName)){
            registrateFormState.setValue(new RegistrateFormState(null, null, R.string.invalid_lastName, null, null, null, null));
        } else if (!isTextFieldValid(firstName)){
            registrateFormState.setValue(new RegistrateFormState(R.string.invalid_firstName, null, null, null, null, null, null));
        } else if (!isTextFieldValid(username)){
            registrateFormState.setValue(new RegistrateFormState(null, null, null, null, R.string.invalid_username, null, null));
        } else if (!isEmailValid(email)) {
            registrateFormState.setValue(new RegistrateFormState(null, null, null, null, null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            registrateFormState.setValue(new RegistrateFormState(null, null, null, null, null, null, R.string.invalid_password));
        } else {
            registrateFormState.setValue(new RegistrateFormState(true));
        }
    }

    // A placeholder text validation check
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

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private class ClassDAO extends AsyncTask<User, Void, Result<User>>{

        @Override
        protected Result<User> doInBackground(User... objects) {
            Result<User> result = null;
            for (User user : objects) {
                result = userRepository.createUser(user);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<User> result) {
            if (result instanceof Result.Success) {
                User data = ((Result.Success<User>) result).getData();
                registrateResult.setValue(new RegistrateResult(data));
            } else {
                registrateResult.setValue(new RegistrateResult(R.string.login_failed));
            }
        }
    }
}
