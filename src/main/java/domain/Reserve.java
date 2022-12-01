package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Reserve {

    private Long id;
    private String userid;
    private Long bookid;
    private LocalDateTime reservedDate;
}
