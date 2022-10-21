package service;

import domain.Status;
import domain.StatusType;

public class LogoutService {

	public void logout() {
		Status.changeStatus(StatusType.LOGIN);
	}
}
