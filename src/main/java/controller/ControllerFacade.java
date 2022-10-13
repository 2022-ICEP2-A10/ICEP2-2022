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
                loginController.execute(command, args);
                break;
            }
            case ADMIN: {
                adminController.execute(command, args);
                break;
            }
            case MEMBER: {
                memberController.execute(command, args);
                break;
            }
        }
    }
}
