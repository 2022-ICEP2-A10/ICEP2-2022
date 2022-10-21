package service;

public class HelpService {
	public void help() {
		System.out.println("회원 프롬프트의 명령어입니다.");
		System.out.println("help : 현재 프롬프트에서 입력할 수 있는 명령어를 출력합니다.");
		System.out.println("search [책이름] : 도서를 검색합니다.");
		System.out.println("checkout [식별자] : 도서를 대출합니다.");
		System.out.println("return [식별자] : 도서를 반납합니다.");
		System.out.println("myloan : 내 대출 목록을 출력합니다.");
		System.out.println("logout : 회원 프롬프트를 종료하고 로그인 프롬프트로 이동합니다.");
	}
}
