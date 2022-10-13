package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class Member {

    private String userid;
    private String password;
    private boolean possible;
    private LocalDateTime possibleDate;
}
