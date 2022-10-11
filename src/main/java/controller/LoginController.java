package controller;

import exceptions.ArgumentException;
import exceptions.CommandException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LoginController {
    private final List<String> loginCommands = List.of("help", "login", "signup", "exit");

    public boolean contains(String command) {
        return loginCommands.contains(command);
    }

    public void execute(String command, String[] args) {
        switch (command) {
            case "help": {
                help(args);
                break;
            }
            case "login": {
                login(args);
                break;
            }
            case "signup": {
                signup(args);
                break;
            }
            case "exit": {
                exit(args);
                break;
            }
            default:
                throw new CommandException();
        }
    }

    private void help(String[] args) {
        if (args.length != 0) {
            throw new ArgumentException();
        }
    }

    private void login(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }

    }

    private void signup(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }
    }

    private void exit(String[] args) {
        if (args.length != 0) {
            throw new ArgumentException();
        }
    }
}
