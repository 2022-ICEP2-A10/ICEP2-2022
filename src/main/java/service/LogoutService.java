package service;

import util.CurrentPrompt;
import domain.PromptStatusType;

public class LogoutService {

	public void logout() {
		System.out.println("로그아웃 되었습니다.");
		CurrentPrompt.changeStatus(PromptStatusType.LOGIN);
	}
}
