package controller;

import exceptions.ArgumentException;
import exceptions.CommandException;
import exceptions.LoginException;
import exceptions.SignupException;
import lombok.RequiredArgsConstructor;

import service.LoginService;
import service.SignupService;
import service.AdminService;
import util.CurrentPrompt;
import domain.PromptStatusType;

@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SignupService signupService;
    private final AdminService adminService;

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

        System.out.println("로그인 프롬프트의 명령어입니다.\n"
            + "- help : 현재 프롬프트에서 입력할 수 있는 명령어를 출력합니다.\n"
            + "- login [ID] [비밀번호] : 정해진 회원정보로 로그인합니다.\n"
            + "- signup [ID] [비밀번호] : 회원가입합니다.\n"
            + "- exit : 파일을 저장하고 프로그램을 종료합니다.");
    }

    private void login(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }

        try {
            loginService.login(args);
        } catch(LoginException e) {
            System.out.println(e.getMessage());
        }
    }

    private void signup(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }

        try {
            signupService.signup(args);
        } catch(SignupException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exit(String[] args) {
        if (args.length != 0) {
            throw new ArgumentException();
        }
        System.out.println("프로그램을 종료합니다.");
        CurrentPrompt.changeStatus(PromptStatusType.EXIT);
        adminService.exit(args);
    }
}
