package com.example.rentingapp.utils;

import com.example.rentingapp.exception.WrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.rentingapp.constants.Constants.PASSWORD_VAL;
import static com.example.rentingapp.utils.HashingPassword.hash;
import static com.example.rentingapp.utils.HashingPassword.verify;

public class HashingPasswordTest {


    @Test
    void hashTest() {
        Assertions.assertNotEquals(PASSWORD_VAL, hash(PASSWORD_VAL));
    }

    @Test
    void verifyTest() {
        Assertions.assertDoesNotThrow(() -> verify(hash(PASSWORD_VAL), PASSWORD_VAL));
        Assertions.assertThrows(WrongPasswordException.class, () -> verify(hash(PASSWORD_VAL), "dfdfse"));
    }
}
