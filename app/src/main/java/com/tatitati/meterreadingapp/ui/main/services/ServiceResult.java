package com.tatitati.meterreadingapp.ui.main.services;

import androidx.annotation.Nullable;

import com.tatitati.meterreadingapp.database.database_objects.Service;

public class ServiceResult {
    @Nullable
    private Service success;
    @Nullable
    private Integer error;

    ServiceResult(@Nullable Integer error) {
        this.error = error;
    }

    ServiceResult(@Nullable Service success) {
        this.success = success;
    }

    @Nullable
    Service getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
