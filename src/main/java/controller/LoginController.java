package controller;

import exceptions.ArgumentException;
import exceptions.CommandException;
import lombok.RequiredArgsConstructor;
import domain.Status;
import domain.StatusType;

import java.util.List;

@RequiredArgsConstructor
public class LoginController {
    private final List<String> loginCommands = List.of("help", "login", "signup", "exit");

    public boolean contains(String command) {
        return loginCommands.contains(command);
    }

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

        System.out.println("로그인 프롬프트의 명령어입니다.");
        System.out.println("- help : 현재 프롬프트에서 입력할 수 있는 명령어를 출력합니다.");
        System.out.println("- login [ID] [비밀번호] : 정해진 회원정보로 로그인합니다.");
        System.out.println("- signup [ID] [비밀번호] : 회원가입합니다.");
        System.out.println("- exit : 파일을 저장하고 프로그램을 종료합니다.");
    }

    private void login(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }

        final String inputID = args[0];
        final String inputPassword = args[1];

        if (false /* !exist('id') */) {
            System.out.println("가입되어 있지 않은 회원입니다.");

            return;
        } else if (false /* id.password.equals(inputPassword) */) {
            System.out.println("비밀번호가 틀렸습니다.");

            return;
        } else if (inputID.equals("admin")) {
            System.out.println("사서로 로그인했습니다.");
            // changeStatus(StatusType.ADMIN);

            return;
        } else {
            System.out.println("회원으로 로그인했습니다.");
            // changeStatus(StatisType.MEMBER);

            return;
        }
    }

    private void signup(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException();
        }

        final String inputId = args[0];
        final String inputPassword = args[1];

        final String idRegex = "[a-zA-Z0-9]{4, 10}";
        final String passwordRegex= "[a-zA-Z0-9]{4, 10}";

        if (!(inputId.matches(idRegex) && inputPassword.matches(passwordRegex))) {
            System.out.println("비정상적인 입력입니다.");
            System.out.println("- 아이디 : 길이 4 이상 10 이하의 알파벳 또는 숫자로 구성된 문자열");
            System.out.println("- 비밀번호 : 길이 4 이상 20 이하의 알파벳 또는 숫자로 구성된 문자열");
            System.out.println("로 입력해주세요.");

            return;
        } else if (false/* exist(inputId) */) {
            System.out.println("중복된 아이디입니다.");

            return;
        } else {
            // add user
            System.out.println("회원가입이 완료되었습니다.");
            System.out.println("id: " + inputId);
            System.out.println("password: " + inputPassword);

            return;
        }
    }

    private void exit(String[] args) {
        if (args.length != 0) {
            throw new ArgumentException();
        }

        // file save and close
    }
}
