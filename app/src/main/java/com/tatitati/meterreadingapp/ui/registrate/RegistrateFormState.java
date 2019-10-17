package com.tatitati.meterreadingapp.ui.registrate;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegistrateFormState {
    @Nullable
    private Integer fisrtNameError;
    @Nullable
    private Integer middleNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer addressError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    RegistrateFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    RegistrateFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    RegistrateFormState(@Nullable Integer fisrtNameError, @Nullable Integer middleNameError,
                               @Nullable Integer lastNameError, @Nullable Integer addressError,
                               @Nullable Integer usernameError, @Nullable Integer emailError,
                               @Nullable Integer passwordError) {
        this.fisrtNameError = fisrtNameError;
        this.middleNameError = middleNameError;
        this.lastNameError = lastNameError;
        this.addressError = addressError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    @Nullable
    Integer getFisrtNameError() {
        return fisrtNameError;
    }

    @Nullable
    Integer getMiddleNameError() {
        return middleNameError;
    }

    @Nullable
    Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    Integer getAddressError() {
        return addressError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }
}
