package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Member 클래스 빌더 패턴 사용 예시
 * Member member = Member.builder()
 *           .userid("user123")
 *           .password("pswd123")
 *           .possible(true)
 *           .userType(UserType.MEMBER)
 *           .build()
 */
@Builder
@Setter
@Getter
public class Member {

    private String userid;
    private String password;
    private boolean possible;
    private LocalDateTime possibleDate;
    private UserType userType;

    @Override
    public String toString() {
        String date = null;
        if (possibleDate != null) {
            date = possibleDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return userid + "\t" + password + "\t" + possible + "\t" + date + "\t" + userType;
    }
}
