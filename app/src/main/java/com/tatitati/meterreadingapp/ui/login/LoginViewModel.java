package com.tatitati.meterreadingapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        new DAO(password).execute(username);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private class DAO extends AsyncTask<String, Void, Result<User>>{
        private String password;

        public DAO(String password){
            this.password = password;
        }

        @Override
        protected Result<User> doInBackground(String... strings) {
            Result<User> result = null;
            for (String string : strings){
                result = UserRepository.getInstance().getUserByUsername(string);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<User> userResult) {
            if (userResult instanceof Result.Success) {
                User data = ((Result.Success<User>) userResult).getData();
                if (data.getPassword().equals(password)){
                    loginResult.setValue(new LoginResult(data));
                }
            }
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
}
