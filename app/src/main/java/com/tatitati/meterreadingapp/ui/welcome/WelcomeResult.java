package com.tatitati.meterreadingapp.ui.welcome;

import androidx.annotation.Nullable;

import com.tatitati.meterreadingapp.database.database_objects.User;

class WelcomeResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    WelcomeResult(@Nullable User success) {
        this.success = success;
    }

    WelcomeResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    User getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
