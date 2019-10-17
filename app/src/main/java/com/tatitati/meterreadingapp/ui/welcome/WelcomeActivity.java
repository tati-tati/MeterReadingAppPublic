package com.tatitati.meterreadingapp.ui.welcome;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tatitati.meterreadingapp.global_variables.GlobalVariables;
import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.ui.main.MainActivity;
import com.tatitati.meterreadingapp.ui.registrate.RegistrateActivity;
import com.tatitati.meterreadingapp.ui.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    private WelcomeViewModel welcomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeViewModel = ViewModelProviders.of(this, new WelcomeViewModelFactory()).get(WelcomeViewModel.class);

        welcomeViewModel.welcome(this.getSharedPreferences(GlobalVariables.APP_SETTING, Activity.MODE_PRIVATE));

        final Button signupButton = findViewById(R.id.btn_singup);
        final Button registrateButton = findViewById(R.id.btn_registrate);

        signupButton.setEnabled(false);
        registrateButton.setEnabled(false);

        welcomeViewModel.getWelcomeResult().observe(this, new Observer<WelcomeResult>() {
            @Override
            public void onChanged(WelcomeResult welcomeResult) {
                if (welcomeResult == null){
                    return;
                }
                if (welcomeResult.getError() != null){
//                    showAuthorizeFailed(welcomeResult.getError());
                    if (welcomeResult.getError() == R.string.first_user_authorise || welcomeResult.getError() == R.string.no_user_found_in_db){
                        signupButton.setEnabled(true);
                        registrateButton.setEnabled(true);
                    }
                    if (welcomeResult.getError() == R.string.no_internet_connection){
                        showAuthorizeFailed(welcomeResult.getError());
                        return;
                    }
                    return;
                }
                if (welcomeResult.getSuccess() != null){
                    finish();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        registrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegistrateActivity.class));
            }
        });
    }

    private void showAuthorizeFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
