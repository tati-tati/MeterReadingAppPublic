package com.tatitati.meterreadingapp.ui.main.meterDetails;

import androidx.annotation.Nullable;

import com.tatitati.meterreadingapp.database.database_objects.Meter;

public class MeterResult {
    @Nullable
    private Meter success;
    @Nullable
    private Integer error;

    MeterResult(@Nullable Integer error) {
        this.error = error;
    }

    MeterResult(@Nullable Meter success) {
        this.success = success;
    }

    @Nullable
    Meter getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
