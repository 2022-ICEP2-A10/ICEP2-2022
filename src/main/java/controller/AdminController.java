package controller;

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
            }
            case "register": {
                register(args);
            }
            case "members": {
                members(args);
            }
            case "loans": {
                loans(args);
            }
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
