package service;

import lombok.RequiredArgsConstructor;
import exceptions.SignupException;

@RequiredArgsConstructor
public class SignupService {
    public void signup(String[] args) {
        final String inputId = args[0];
        final String inputPassword = args[1];

        final String idRegex = "[a-zA-Z0-9]{4, 10}";
        final String passwordRegex= "[a-zA-Z0-9]{4, 10}";

        if (!(inputId.matches(idRegex) && inputPassword.matches(passwordRegex))) {
            final String message = "비정상적인 입력입니다.\n"
                + "- 아이디 : 길이 4 이상 10 이하의 알파벳 또는 숫자로 구성된 문자열\n"
                + "- 비밀번호 : 길이 4 이상 20 이하의 알파벳 또는 숫자로 구성된 문자열\n"
                + "로 입력해주세요.";
            throw new SignupException(message);
        } else if (false/* exist(inputId) */) {
            throw new SignupException("중복된 아이디입니다.");
        } else {
            System.out.println("회원가입이 완료되었습니다.");
            System.out.println("id: " + inputId);
            System.out.println("password: " + inputPassword);

            // add member

            return;
        }
    }
}
