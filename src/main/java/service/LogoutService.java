package service;

import domain.Status;
import domain.StatusType;

public class LogoutService {

	public void logout() {
		System.out.println("로그아웃 되었습니다.");
		Status.changeStatus(StatusType.LOGIN);
	}
}
