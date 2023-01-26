package com.example.project.web.command;

import com.example.project.web.command.admin.ManagerRegistrCommand;
import com.example.project.web.command.base.ErrorCommand;
import com.example.project.web.command.base.LoginCommand;
import com.example.project.web.command.user.UserRegistrCommand;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static final Logger LOG = Logger.getLogger(CommandManager.class);
    private final Map<String, Command> commands;

    public CommandManager() {
        commands = new HashMap<>();

        //////////////      ENTER POINT       ///////////////
        commands.put("login", new LoginCommand());
        commands.put("user_reg", new UserRegistrCommand());
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
