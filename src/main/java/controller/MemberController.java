package controller;

import exceptions.CommandException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberController {

    public void execute(String command, String[] args) {
        switch (command) {
            case "help": {
                help(args);
                break;
            }
            case "checkout": {
                checkout(args);
                break;
            }
            case "return": {
                returnBook(args);
                break;
            }
            case "search": {
                search(args);
                break;
            }
            case "myloan": {
                myloan(args);
                break;
            }
            case "logout": {
                logout(args);
                break;
            }
            default:
                throw new CommandException();
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
