package controller;

import exceptions.ArgumentException;
import exceptions.CommandException;
import exceptions.MemberException;
import exceptions.ReserveException;
import lombok.RequiredArgsConstructor;

import service.*;

@RequiredArgsConstructor
public class MemberController {

    private final HelpService helpService;
    private final CheckoutService checkoutService;
    private final ReturnBookService returnBookService;
    private final SearchService searchService;
    private final MyLoanService myloanService;
    private final LogoutService logoutService;
    private final ReserveService reserveService;

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
            case "reserve": {
                reserve(args);
                break;
            }
            case "cancel": {
                cancel(args);
                break;
            }

            default:
                throw new CommandException();
        }
    }

    private void help(String[] args) {
        if (args.length != 0) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }

        helpService.help();
    }

    private void checkout(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
        try {
            checkoutService.checkout(args);
        } catch (MemberException | ReserveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }

        try {
            returnBookService.returnBook(args);
        } catch(MemberException e) {
            System.out.println(e.getMessage());
        }
    }

    private void search(String[] args) {
    	if (args.length < 1) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
    	
    	searchService.search(args);
    }

    private void myloan(String[] args) {
    	if (args.length != 0) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
    	
    	myloanService.myloan(args);
    }

    private void logout(String[] args) {
    	if (args.length != 0) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
    	
    	logoutService.logout();
    }

    private void reserve(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
        try {
            reserveService.reserve(args);
        } catch (MemberException | ReserveException e) {
            System.out.println(e.getMessage());
        }
    }
    private void cancel(String[] args) {
    	if (args.length != 1) {
            throw new ArgumentException("비정상적인 입력입니다.\n인자의 개수가 잘못되었습니다.");
        }
    	
        try {
            CancelService.cancel(args);
        } catch (MemberException | ReserveException e) {
            System.out.println(e.getMessage());
        }
    }
}
