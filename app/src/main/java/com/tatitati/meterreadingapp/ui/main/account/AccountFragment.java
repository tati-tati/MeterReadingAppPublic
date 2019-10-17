package com.tatitati.meterreadingapp.ui.main.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.database.database_objects.User;

public class AccountFragment extends Fragment {
    private AccountFragmentModel accountFragmentModel;
    private EditText lastName;
    private EditText firstName;
    private EditText middleName;
    private EditText address;
    private EditText username;
    private EditText email;
    private EditText password;
    private Button saveUserChanges;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        accountFragmentModel = new AccountFragmentModel(view.getContext());

        lastName = view.findViewById(R.id.lastName_user_changes);
        firstName = view.findViewById(R.id.firstName_user_changes);
        middleName = view.findViewById(R.id.middleName_user_changes);
        address = view.findViewById(R.id.address_user_changes);
        username = view.findViewById(R.id.username_user_changes);
        email = view.findViewById(R.id.email_user_changes);
        password = view.findViewById(R.id.password_user_changes);
        saveUserChanges = view.findViewById(R.id.save_user_changes);
        ProgressBar loading = view.findViewById(R.id.loading_user_changes);

        fillUserData();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                accountFragmentModel.onAccountDataChanged(lastName.getText().toString(), firstName.getText().toString(),
                        middleName.getText().toString(), address.getText().toString(),
                        username.getText().toString(), email.getText().toString(), password.getText().toString(), saveUserChanges);
            }
        };

        lastName.addTextChangedListener(textWatcher);
        firstName.addTextChangedListener(textWatcher);
        middleName.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
        username.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);


        return view;
    }

    void fillUserData(){
        User user = accountFragmentModel.getUser();
        if (user == null) return;
        lastName.setText(user.getLastName());
        middleName.setText(user.getMiddleName());
        firstName.setText(user.getFirstName());
        address.setText(user.getAddress());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
    }
}
