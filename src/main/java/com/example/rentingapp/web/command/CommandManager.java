package com.example.rentingapp.web.command;

import com.example.rentingapp.web.command.admin.*;
import com.example.rentingapp.web.command.base.*;

import static com.example.rentingapp.web.command.constants.Commands.*;

import com.example.rentingapp.web.command.car.ShowCarCommand;
import com.example.rentingapp.web.command.car.ShowCarsCommand;
import com.example.rentingapp.web.command.manager.DisplayOrderCommand;
import com.example.rentingapp.web.command.manager.RegisterReturnCommand;
import com.example.rentingapp.web.command.manager.RejectOrderCommand;
import com.example.rentingapp.web.command.manager.ViewOrdersCommand;
import com.example.rentingapp.web.command.user.*;
import com.example.rentingapp.web.listeners.EmailContext;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Logger LOG = Logger.getLogger(CommandManager.class);
    private final Map<String, Command> commands;
    private static final EmailContext EMAIL_CONTEXT = EmailContext.getEmailContext();

    public CommandManager() {

        commands = new HashMap<>();

        //////////////      ENTRY POINT       ///////////////
        commands.put(LOGIN, new LoginCommand());
        commands.put(USER_REG, new UserRegistrCommand(EMAIL_CONTEXT));
        commands.put(LOG_OUT, new LogOutCommand());
        commands.put(RESTORE, new RestorePassCommand(EMAIL_CONTEXT));

        //////////////      PROFILE ACTIONS       ///////////////
        commands.put(EDIT, new EditCommand());
        commands.put(TOP_UP, new TopUpCommand());
        commands.put(SHOW_MY_ORDERS, new ShowMyOrdersCommand());

        //////////////      CAR ACTIONS       ///////////////
        commands.put(SHOW_CARS, new ShowCarsCommand());
        commands.put(SHOW_CAR, new ShowCarCommand());

        //////////////      ORDER ACTIONS       ///////////////
        commands.put(MAKE_ORDER, new MakeOrderCommand(EMAIL_CONTEXT));
        commands.put(PAY_ORDER, new PayOrderCommand(EMAIL_CONTEXT));
        commands.put(SET_DATES, new SetDatesCommand());

        //////////////      ADMIN ACTIONS       ///////////////
        commands.put(SHOW_ADMIN_CARS, new ShowAdminCarsCommand());
        commands.put(SHOW_ADMIN_USERS, new ShowAdminUsersCommand());
        commands.put(SHOW_ADMIN_MANAGERS, new ShowManagersCommand());
        commands.put(MANAGER_REG, new ManagerRegistrCommand());
        commands.put(ADD_CAR, new AddCarCommand());
        commands.put(EDIT_CAR, new EditCarCommand());
        commands.put(DELETE_CAR, new DeleteCarCommand());
        commands.put(UPDATE_USER_STATUS, new UpdateUserStatusCommand(EMAIL_CONTEXT));
        commands.put(DISPLAY_INFO_MNG, new DisplayInfoManager());
        commands.put(EDIT_MNG, new EditManagerCommand());

        //////////////      MANAGER ACTIONS       ///////////////
        commands.put(VIEW_ORDERS, new ViewOrdersCommand());
        commands.put(DISPLAY_ORDER, new DisplayOrderCommand());
        commands.put(REJECT, new RejectOrderCommand(EMAIL_CONTEXT));
        commands.put(REGISTER_RETURN, new RegisterReturnCommand(EMAIL_CONTEXT));

        commands.put(ERROR, new ErrorCommand());
    }

    public Command defineCommand(String command) {
        if (command == null || !commands.containsKey(command)) {
            LOG.trace("Command " + command + " do not exists!");
            return commands.get(ERROR);
        }
        return commands.get(command);
    }
}
