package com.example.rentingapp.utils;

public class RegexConstants {

    static final String NAME_PATTERN = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,30}";

    static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$";

    static final String PHONE_PATTERN =
            "^\\+?3?8?(0\\d{9})$";
}
