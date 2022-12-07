package controller;

import exceptions.CommandException;
import service.AdminService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

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
            case "logout": {
            	logout(args);
            	break;
            }
            case "reserves":{
            	reserves(args);
            	break;
            }
            default:
                throw new CommandException();
        }
    }

    private void help(String[] args) {
    	adminService.librarian_help(args);
    }

    private void register(String[] args) {
    	adminService.regist_Book(args);
    }

    private void members(String[] args) {
    	adminService.show_Memberlist(args);
    }

    private void loans(String[] args) {
    	adminService.show_Checkout_Book(args);
    }
    private void logout(String[] args) {
    	adminService.logout(args);
    }
    private void reserves(String[] args) {
    	adminService.reserves(args);
    }

    

}
