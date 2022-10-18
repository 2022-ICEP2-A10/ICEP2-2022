package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
        return userid + "\t" + password + "\t" + possible + "\t" + possibleDate + "\t" + userType;
    }
}
