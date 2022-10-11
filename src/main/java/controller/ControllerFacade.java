package controller;

import domain.Status;
import exceptions.CommandException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ControllerFacade {

    private final LoginController loginController;

    private final AdminController adminController;

    private final MemberController memberController;

    public void execute(String command, String[] args) {
        switch (Status.getCurStatus()) {
            case LOGIN: {
                if (loginController.contains(command)) {
                    loginController.execute(command, args);
                } else {
                    throw new CommandException();
                }
            }
            case ADMIN: {
                if (adminController.contains(command)) {
                    adminController.execute(command, args);
                } else {
                    throw new CommandException();
                }
            }
            case MEMBER: {
                if (memberController.contains(command)) {
                    memberController.execute(command, args);
                } else {
                    throw new CommandException();
                }
            }
        }
    }
}
