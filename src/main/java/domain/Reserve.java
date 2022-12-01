package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
public class Reserve {

    private Long bookid;
    private String userid;
    private LocalDateTime reservedDate;

    @Override
    public String toString() {
        String date = null;
        if (reservedDate != null) {
            date = reservedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return bookid + "\t" + userid + "\t" + date;
    }
}
