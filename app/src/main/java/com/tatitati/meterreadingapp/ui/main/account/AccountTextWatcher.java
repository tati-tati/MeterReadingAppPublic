package com.tatitati.meterreadingapp.ui.main.account;

import android.text.Editable;
import android.text.TextWatcher;

public class AccountTextWatcher <T> implements TextWatcher {

    private T t;

    public AccountTextWatcher(T t) {
        this.t = t;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
