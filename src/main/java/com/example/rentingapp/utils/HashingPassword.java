package com.example.rentingapp.utils;


import com.example.rentingapp.exception.WrongPasswordException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.log4j.Logger;


public final class HashingPassword {

    private static final Logger LOG = Logger.getLogger(HashingPassword.class);

    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public static String hash(String pass) {
        LOG.trace("Creating hash for password: "+pass);
        String hash= argon2.hash(4, 1024 * 1024, 8, pass);
        LOG.trace("Hashed pass: "+hash);
        return hash;
    }
    public static void verify(String hash, String pass) throws WrongPasswordException {
        if(!argon2.verify(hash, pass.toCharArray())){
            throw new WrongPasswordException();
        }
        LOG.trace("Success");
    }
}
