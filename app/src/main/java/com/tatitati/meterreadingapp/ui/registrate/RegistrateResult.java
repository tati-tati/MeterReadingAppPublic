package com.tatitati.meterreadingapp.ui.registrate;

import androidx.annotation.Nullable;

import com.tatitati.meterreadingapp.database.database_objects.User;

/**
 * Authentication result : success (user details) or error message.
 */
class RegistrateResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    RegistrateResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistrateResult(@Nullable User success) {
        this.success = success;
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
