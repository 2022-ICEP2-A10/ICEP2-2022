package controller;

import exceptions.CommandException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminController {
    private final List<String> adminCommands = List.of("help", "register", "members", "loans");

    public boolean contains(String command) {
        return adminCommands.contains(command);
    }

    public void execute(String command, String[] args) {
        switch (command) {
            case "help": {
                help(args);
                break;
            }
            case "register": {
                register(args);
                break;
            }
            case "members": {
                members(args);
                break;
            }
            case "loans": {
                loans(args);
                break;
            }
            default:
                throw new CommandException();
        }
    }

    private void help(String[] args) {

    }

    private void register(String[] args) {

    }

    private void members(String[] args) {

    }

    private void loans(String[] args) {

    }
}
