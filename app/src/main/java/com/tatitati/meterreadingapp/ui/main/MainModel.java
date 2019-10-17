package com.tatitati.meterreadingapp.ui.main;

import com.tatitati.meterreadingapp.database.UserRepository;

public class MainModel {
    void logout(){
        UserRepository.getInstance().logout();
    }
}
