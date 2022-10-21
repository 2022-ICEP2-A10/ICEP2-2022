package controller;

import exceptions.CommandException;
import librarian.Librarian;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import repository.BookRepository;
import repository.CheckoutRepository;
import repository.MemberRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Checkout;
import domain.Member;
import domain.Status;


@RequiredArgsConstructor
public class AdminController {

	private final Librarian lib;


	
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
            default:
                throw new CommandException();
        }
    }

    private void help(String[] args) {
    	lib.librarian_help(args);

    }

    private void register(String[] args) {
    	lib.regist_Book(args);
    }

    private void members(String[] args) {
    	lib.show_Memberlist(args);
    }

    private void loans(String[] args) {
    	lib.show_Checkout_Book(args);
    }
    private void logout(String[] args) {
    	lib.logout(args);
    }



}
