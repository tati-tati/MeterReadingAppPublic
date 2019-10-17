package com.tatitati.meterreadingapp.ui.registrate;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.tatitati.meterreadingapp.database.UserRepository;

/**
 * ViewModel provider factory to instantiate RegistrateViewModel.
 * Required given RegistrateViewModel has a non-empty constructor
 */
public class RegistrateViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrateViewModel.class)) {
            return (T) new RegistrateViewModel(UserRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
