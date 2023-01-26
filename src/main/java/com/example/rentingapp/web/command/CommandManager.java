package com.example.rentingapp.web.command;

import com.example.rentingapp.web.command.admin.ManagerRegistrCommand;
import com.example.rentingapp.web.command.admin.ShowAdminCars;
import com.example.rentingapp.web.command.admin.ShowAdminUsers;
import com.example.rentingapp.web.command.admin.ShowManagersCommand;
import com.example.rentingapp.web.command.base.*;

import static com.example.rentingapp.web.command.constants.Commands.*;

import com.example.rentingapp.web.command.car.ShowCarCommand;
import com.example.rentingapp.web.command.car.ShowCarsCommand;
import com.example.rentingapp.web.command.user.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Logger LOG = Logger.getLogger(CommandManager.class);
    private final Map<String, Command> commands;

    public CommandManager() {

        commands = new HashMap<>();

        //////////////      ENTRY POINT       ///////////////
        commands.put(LOGIN, new LoginCommand());
        commands.put(USER_REG, new UserRegistrCommand());
        commands.put(LOG_OUT, new LogOutCommand());

        //////////////      PROFILE ACTIONS       ///////////////
        commands.put(EDIT, new EditCommand());
        commands.put(TOP_UP, new TopUpCommand());
        commands.put(SHOW_MY_ORDERS, new ShowMyOrdersCommand());

        //////////////      CAR ACTIONS       ///////////////
        commands.put(SHOW_CARS, new ShowCarsCommand());
        commands.put(SHOW_CAR, new ShowCarCommand());

        //////////////      ORDER ACTIONS       ///////////////
        commands.put(MAKE_ORDER, new MakeOrderCommand());
        commands.put(PAY_ORDER, new PayOrderCommand());

        //////////////      ADMIN ACTIONS       ///////////////
        commands.put(SHOW_ADMIN_CARS, new ShowAdminCars());
        commands.put(SHOW_ADMIN_USERS, new ShowAdminUsers());
        commands.put(SHOW_ADMIN_MANAGERS, new ShowManagersCommand());

        commands.put("manager_ref", new ManagerRegistrCommand());

        commands.put("error", new ErrorCommand());
    }

    public Command defineCommand(String command) {
        if (command==null || !commands.containsKey(command)) {
            LOG.trace("Command "+ command+ " do not exists!");
            return commands.get("error");
        }
        return commands.get(command);
    }
}
