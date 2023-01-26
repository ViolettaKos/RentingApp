package com.example.rentingapp.web.command;

public class CommandFactory {

    private static CommandFactory instance=new CommandFactory();
    private CommandManager commandManager = new CommandManager();


    private static CommandFactory getInstance() {
        if (instance == null){
            instance = new CommandFactory();
        }
        return instance;
    }

    public static Command defineCommand(String command) {
        return getInstance().commandManager.defineCommand(command);
    }
}
