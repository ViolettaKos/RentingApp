package com.example.rentingapp.utils;

import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.IncorrectEmailException;
import com.example.rentingapp.exception.NotAdultException;
import com.example.rentingapp.exception.PasswordNotMatchesException;
import com.example.rentingapp.service.impl.UserServiceImpl;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.rentingapp.utils.RegexConstants.*;

public final class Validator {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    public void isMatched(String pass, String repeated_pass) throws PasswordNotMatchesException, IncorrectDataException {
        if (passIsEmpty(pass) || repeated_pass.isEmpty() || !pass.equals(repeated_pass))
            throw new PasswordNotMatchesException();
    }

    public boolean passIsEmpty(String pass) throws IncorrectDataException {
        if (pass.isEmpty())
            throw new IncorrectDataException();
        return false;
    }

    public void validateEmail(String email) throws IncorrectEmailException {
        if (!EmailValidator.getInstance().isValid(email))
            throw new IncorrectEmailException();
    }

    public void validateData(String data) throws IncorrectDataException {
        checkRegex(data);
    }

    public void validateLogin(String login) throws IncorrectDataException {
        if (login.isEmpty() || !login.matches(USERNAME_PATTERN)) {
            throw new IncorrectDataException();
        }
    }


    private static void checkRegex(String data) throws IncorrectDataException {
        if (data.isEmpty() || !data.matches(NAME_PATTERN)) {
            throw new IncorrectDataException();
        }
    }

    public void validateTelephone(String telephone) throws IncorrectDataException {
        if (telephone.isEmpty() || !telephone.matches(PHONE_PATTERN)) {
            throw new IncorrectDataException();
        }
    }

    public void checkAge(String age) throws NotAdultException {
        if (age.isEmpty())
            throw new NotAdultException();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        LocalDate date = LocalDate.parse(age, formatter);
        LocalDate localDate = LocalDate.now();
        if (18 > Period.between(date, localDate).getYears())
            throw new NotAdultException();
        LOG.trace("Age is valid");
    }

    public void checkDate(String from, String to) throws IncorrectDataException {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        sdformat.setLenient(false);
        try {
            Date d1 = sdformat.parse(from);
            Date d2 = sdformat.parse(to);
            int year1 = getYearFromDate(d1);
            int year2=getYearFromDate(d2);
            if (d1.before(new Date()) || d2.before(new Date())) {
                LOG.trace("Date from or to occurs after now Date");
                throw new IncorrectDataException();
            }
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            if (year1 > currentYear + 1 || year2 > currentYear + 1) {
                LOG.trace("Input year is more than the current year plus 1");
                throw new IncorrectDataException();
            }
            if (d1.compareTo(d2) > 0) {
                LOG.trace("Date from occurs after Date to");
                throw new IncorrectDataException();
            } else if (d1.compareTo(d2) < 0) {
                LOG.trace("Date to occurs after Date from");
            } else if (d1.compareTo(d2) == 0) {
                LOG.trace("Date from == Date to");
            }
        } catch (ParseException e) {
            LOG.error("Exception while parsing dates!");
            throw new IncorrectDataException();
        }
    }

    private int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public boolean isEnoughMoney(int amount, int user_money) {
        return user_money >= amount;
    }
}
