package com.tatitati.meterreadingapp.ui.main.account;

import android.content.Context;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tatitati.meterreadingapp.database.UserRepository;
import com.tatitati.meterreadingapp.database.database_objects.User;

public class AccountFragmentModel {
    private Context context;

    public AccountFragmentModel(Context context) {
        this.context = context;
    }

    User getUser(){
        return UserRepository.getInstance().getUser();
    }

    void onAccountDataChanged(@Nullable String lastName, @Nullable String firstName, @Nullable String middleName,
                              @Nullable String address, @Nullable String username, @Nullable String email,
                              @Nullable String password, Button save){
        if (!isInputTextValid(lastName)){
            showError("Заполните Фамилию");
            return;
        }
        if (!isInputTextValid(firstName)){
            showError("Заполните Имя");
            return;
        }
        if (!isInputTextValid(address)){
            showError("Заполните Адрес");
            return;
        }
        if (!isInputTextValid(username)){
            showError("Заполните Имя пользователя");
            return;
        }
        if (!isEmailValid(email)){
            showError("Некорректный email");
            return;
        }
        if (!isPasswordValid(password)){
            showError("Пароль слишком короткий");
            return;
        }
        save.setEnabled(true);

    }

    boolean isInputTextValid(String text){
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

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    void showError(String error){
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}
