package com.tatitati.meterreadingapp.ui.registrate;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tatitati.meterreadingapp.global_variables.GlobalVariables;
import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.User;
import com.tatitati.meterreadingapp.ui.main.MainActivity;
import com.tatitati.meterreadingapp.ui.welcome.WelcomeActivity;

public class RegistrateActivity extends AppCompatActivity {

    private RegistrateViewModel registrareViewModel;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);
        registrareViewModel = ViewModelProviders.of(this, new RegistrateViewModelFactory())
                .get(RegistrateViewModel.class);

        preferences = getSharedPreferences(GlobalVariables.APP_SETTING, Activity.MODE_PRIVATE);

        final EditText lastNameEditText = findViewById(R.id.lastName);
        final EditText firstNameEditText = findViewById(R.id.firstName);
        final EditText middleNameEditText = findViewById(R.id.middleName);
        final EditText addressNameEditText = findViewById(R.id.address);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText emailEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        final Button registrateButton = findViewById(R.id.registrate_btn_registrate);
        final Button backButton = findViewById(R.id.registrate_btn_back_to_welcome);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        registrareViewModel.getRegistrateFormState().observe(this, new Observer<RegistrateFormState>() {
            @Override
            public void onChanged(@Nullable RegistrateFormState registrationFormState) {
                if (registrationFormState == null) {
                    return;
                }
                registrateButton.setEnabled(registrationFormState.isDataValid());
                if (registrationFormState.getFisrtNameError() != null) {
                    firstNameEditText.setError(getString(registrationFormState.getFisrtNameError()));
                }
                if (registrationFormState.getMiddleNameError() != null) {
                    middleNameEditText.setError(getString(registrationFormState.getMiddleNameError()));
                }
                if (registrationFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(registrationFormState.getLastNameError()));
                }
                if (registrationFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registrationFormState.getUsernameError()));
                }
                if (registrationFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registrationFormState.getEmailError()));
                }
                if (registrationFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registrationFormState.getPasswordError()));
                }
            }
        });

        registrareViewModel.getRegistrateResult().observe(this, new Observer<RegistrateResult>() {
            @Override
            public void onChanged(@Nullable RegistrateResult registrationResult) {
                if (registrationResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registrationResult.getError() != null) {
                    showLoginFailed(registrationResult.getError());
                    return;
                }
                if (registrationResult.getSuccess() != null) {
                    preferences.edit().putInt(GlobalVariables.CURR_USER_ID, registrationResult.getSuccess().getId()).apply();
                    updateUiWithUser(registrationResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registrareViewModel.registrateDataChanged(firstNameEditText.getText().toString(), middleNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(), usernameEditText.getText().toString(),
                        emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };

        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        addressNameEditText.addTextChangedListener(afterTextChangedListener);
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        backButton.setEnabled(true);

        registrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registrareViewModel.registrate(firstNameEditText.getText().toString(), middleNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(), addressNameEditText.getText().toString(), usernameEditText.getText().toString(),
                        emailEditText.getText().toString(), passwordEditText.getText().toString());
                finish();
                startActivity(new Intent(RegistrateActivity.this, MainActivity.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrateActivity.this, WelcomeActivity.class));
            }
        });
    }

    private void updateUiWithUser(User model) {
        String welcome = "Добрый день, " + model.getFirstName() + " " + model.getMiddleName() + "!";
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
