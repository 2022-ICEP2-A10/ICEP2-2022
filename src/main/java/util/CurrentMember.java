package util;

import domain.Member;

public class CurrentMember {

    private static Member curMember;

    public static Member getCurrentMember() {
        return curMember;
    }

    public static void setCurMember(Member member) {
        curMember = member;
    }
}
