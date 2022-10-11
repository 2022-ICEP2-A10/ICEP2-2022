package controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberController {
    private final List<String> memberCommands = List.of("help", "checkout", "return", "search", "myloan", "logout");

    public boolean contains(String command) {
        return memberCommands.contains(command);
    }

    public void execute(String command, String[] args) {
        switch (command) {
            case "help": {
                help(args);
            }
            case "checkout": {
                checkout(args);
            }
            case "return": {
                returnBook(args);
            }
            case "search": {
                search(args);
            }
            case "myloan": {
                myloan(args);
            }
            case "logout": {
                logout(args);
            }

        }
    }

    private void help(String[] args) {

    }

    private void checkout(String[] args) {

    }

    private void returnBook(String[] args) {

    }

    private void search(String[] args) {

    }

    private void myloan(String[] args) {

    }

    private void logout(String[] args) {

    }
}
