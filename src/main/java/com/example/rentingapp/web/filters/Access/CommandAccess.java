package com.example.rentingapp.web.filters.Access;

import java.util.HashSet;
import java.util.Set;

import static com.example.rentingapp.web.command.constants.Commands.*;

public final class CommandAccess {
    private static final Set<String> guestCommands = new HashSet<>();
    private static final Set<String> userCommands = new HashSet<>();
    private static final Set<String> adminCommands = new HashSet<>();
    private static final Set<String> managerCommands = new HashSet<>();
    private static final Set<String> loggedCommands = new HashSet<>();

    static {
        guestCommands.add(USER_REG);
        guestCommands.add(LOGIN);
        guestCommands.add(SHOW_CARS);
        guestCommands.add(SHOW_CAR);
        guestCommands.add(RESTORE);
        guestCommands.add(ERROR);
    }

    static {
        loggedCommands.addAll(guestCommands);
        loggedCommands.add(LOG_OUT);
        loggedCommands.add(EDIT);
    }

    static {
        userCommands.addAll(guestCommands);
        userCommands.addAll(loggedCommands);
        userCommands.add(TOP_UP);
        userCommands.add(SHOW_MY_ORDERS);
        userCommands.add(MAKE_ORDER);
        userCommands.add(PAY_ORDER);
        userCommands.add(SET_DATES);
    }

    static {
        adminCommands.addAll(guestCommands);
        adminCommands.addAll(loggedCommands);
        adminCommands.add(SHOW_ADMIN_CARS);
        adminCommands.add(SHOW_ADMIN_USERS);
        adminCommands.add(SHOW_ADMIN_MANAGERS);
        adminCommands.add(MANAGER_REG);
        adminCommands.add(ADD_CAR);
        adminCommands.add(EDIT_CAR);
        adminCommands.add(DELETE_CAR);
        adminCommands.add(UPDATE_USER_STATUS);
        adminCommands.add(DISPLAY_INFO_MNG);
        adminCommands.add(EDIT_MNG);
    }

    static {
        managerCommands.addAll(guestCommands);
        managerCommands.addAll(loggedCommands);
        managerCommands.add(VIEW_ORDERS);
        managerCommands.add(DISPLAY_ORDER);
        managerCommands.add(REJECT);
        managerCommands.add(REGISTER_RETURN);
    }

    public static Set<String> getGuestCommands() {
        return guestCommands;
    }

    public static Set<String> getUserCommands() {
        return userCommands;
    }

    public static Set<String> getAdminCommands() {
        return adminCommands;
    }

    public static Set<String> getManagerCommands() {
        return managerCommands;
    }

}
