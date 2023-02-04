package com.example.rentingapp.web.filters.Access;

import java.util.HashSet;
import java.util.Set;

import static com.example.rentingapp.web.command.constants.Path.*;

public final class PageAccess {
    private static final Set<String> guestPages = new HashSet<>();
    private static final Set<String> userPages = new HashSet<>();
    private static final Set<String> adminPages = new HashSet<>();
    private static final Set<String> managerPages = new HashSet<>();
    private static final Set<String> loggedPages = new HashSet<>();

    static {
        guestPages.add(MAIN_PAGE);
        guestPages.add(ERROR_PAGE);
        guestPages.add(SIGN_UP_PAGE);
        guestPages.add(LOGIN_PAGE);
        guestPages.add(BOOK_PAGE);
        guestPages.add(PRODUCT_PAGE);
        guestPages.add(CONTACTS_PAGE);
        guestPages.add(RESTORE_PAGE);
    }

    static {
        loggedPages.addAll(guestPages);
        loggedPages.add(PROFILE_PAGE);
        loggedPages.add(EDIT_PAGE);
    }

    static {
        userPages.addAll(guestPages);
        userPages.addAll(loggedPages);
        userPages.add(MY_ORDERS);
        userPages.add(ORDER_PAGE);
        userPages.add(FAILURE_PAGE);
        userPages.add(SUCCESS_PAGE);
        userPages.add(PAYMENT_PAGE);
    }

    static {
        adminPages.addAll(guestPages);
        adminPages.addAll(loggedPages);
        adminPages.add(ADMIN_CARS_PAGE);
        adminPages.add(ADMIN_USERS_PAGE);
        adminPages.add(ADMIN_MNG_PAGE);
        adminPages.add(ADD_CAR_PAGE);
        adminPages.add(ADD_MNG_PAGE);
        adminPages.add(EDIT_CAR_PAGE);
        adminPages.add(EDIT_MANAGER_PAGE);
    }

    static {
        managerPages.addAll(guestPages);
        managerPages.addAll(loggedPages);
        managerPages.add(MNG_ORDERS_PAGE);
        managerPages.add(ORDER_INFO_PAGE);
        managerPages.add(REG_RETURN_PAGE);
        managerPages.add(REJECT_ORDER_PAGE);
    }


    public static Set<String> getGuestPages() {
        return guestPages;
    }

    public static Set<String> getUserPages() {
        return userPages;
    }

    public static Set<String> getAdminPages() {
        return adminPages;
    }

    public static Set<String> getManagerPages() {
        return managerPages;
    }

}
