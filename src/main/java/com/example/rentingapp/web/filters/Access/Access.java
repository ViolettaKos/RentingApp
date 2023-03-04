package com.example.rentingapp.web.filters.Access;

import org.apache.log4j.Logger;

import java.util.Set;

import static com.example.rentingapp.web.command.constants.Model.*;

public final class Access {
    private static final Logger LOG = Logger.getLogger(Access.class.getName());
    private final String servletPath;
    private final String command;
    private Set<String> pages;
    private Set<String> commands;

    private Access(String servletPath, String command) {
        this.servletPath = servletPath;
        this.command = command;
        setPages();
        setCommands();
    }

    private Access(String servletPath, String command, String role) {
        this.servletPath = servletPath;
        this.command = command;
        setPages(role);
        setCommands(role);
    }

    private void setCommands(String role) {
        LOG.trace("Role: " + role);
        switch (role) {
            case USER: {
                commands = CommandAccess.getUserCommands();
                break;
            }
            case ADMIN: {
                commands = CommandAccess.getAdminCommands();
                break;
            }
            case MANAGER: {
                commands = CommandAccess.getManagerCommands();
            }
        }
    }

    private void setPages(String role) {
        switch (role) {
            case USER: {
                pages = PageAccess.getUserPages();
                break;
            }
            case ADMIN: {
                pages = PageAccess.getAdminPages();
                break;
            }
            case MANAGER: {
                pages = PageAccess.getManagerPages();
            }
        }
    }

    private void setPages() {
        pages = PageAccess.getGuestPages();
    }

    private void setCommands() {
        commands = CommandAccess.getGuestCommands();
    }

    public static Access getAccess(String servletPath, String command) {
        return new Access(servletPath, command);
    }

    public static Access getAccess(String servletPath, String command, String role) {
        return new Access(servletPath, command, role);
    }

    public boolean checkAccess() {
        return !checkPages() || !checkCommands();
    }

    private boolean checkPages() {
        if (servletPath != null) {
            return pages.contains(servletPath.substring(1));
        }
        return true;
    }

    private boolean checkCommands() {
        if (command != null) {
            return commands.contains(command);
        }
        return true;
    }
}
