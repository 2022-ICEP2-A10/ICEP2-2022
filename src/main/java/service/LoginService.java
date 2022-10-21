package service;

import lombok.RequiredArgsConstructor;
import exceptions.LoginException;
import repository.MemberRepository;
import domain.Member;

@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public void login(String[] args) {
        final String inputID = args[0];
        final String inputPassword = args[1];

        Member member = memberRepository.findById(inputID)
                .orElseThrow(() -> new LoginException("가입되어 있지 않은 회원입니다."));
        
        if (member.getPassword().equals(inputPassword)) {
            throw new LoginException("비밀번호가 틀렸습니다.");
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
}
