package domain;

public class Status {
    private static StatusType curStatus = StatusType.LOGIN;

    public static StatusType getCurStatus() {
        return curStatus;
    }

    public void changeStatus(StatusType statusType) {
        curStatus = statusType;
    }
}
