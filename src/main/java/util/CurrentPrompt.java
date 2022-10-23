package util;

import domain.PromptStatusType;

public class CurrentPrompt {
    private static PromptStatusType curStatus = PromptStatusType.LOGIN;

    public static PromptStatusType getCurStatus() {
        return curStatus;
    }

    public static void changeStatus(PromptStatusType promptStatusType) {
        curStatus = promptStatusType;
    }
}
