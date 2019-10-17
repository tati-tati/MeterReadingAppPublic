package com.tatitati.meterreadingapp.ui.welcome;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.Result;
import com.tatitati.meterreadingapp.database.database_objects.User;
import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.global_variables.GlobalVariables;

public class WelcomeViewModel extends ViewModel {

    private MutableLiveData<WelcomeResult> welcomeResult = new MutableLiveData<>();

    LiveData<WelcomeResult> getWelcomeResult (){
        return welcomeResult;
    }

    void welcome(SharedPreferences preferences){
//        authorize(1);
//        the following code is commented ONLY FOR DEV
        if (preferences.contains(GlobalVariables.CURR_USER_ID)){
            authorize(preferences.getInt(GlobalVariables.CURR_USER_ID, 0));
        } else {
            welcomeResult.setValue(new WelcomeResult(R.string.first_user_authorise));
        }
    }

    private void authorize(int userId){
        new DAO().execute(userId);
    }

    private class DAO extends AsyncTask<Integer, Void, Result<User>>{

        @Override
        protected Result<User> doInBackground(Integer... integers) {
            Result<User> result = null;
            for (Integer id: integers) {
                result = UserRepository.getInstance().getUserById(id);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Result<User> result) {
            if (result instanceof Result.Success){
                User data = ((Result.Success<User>) result).getData();
                welcomeResult.setValue(new WelcomeResult(data));
            } else {
                welcomeResult.setValue(new WelcomeResult(R.string.no_internet_connection));
            }
        }
    }


}
