package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Checkout 클래스 빌더 패턴 사용 예시
 * Checkout checkout = Checkout.builder()
 *              .bookid(100L)
 *              .checkoutDate(날짜)
 *              .build()
 */
@Builder
@Getter
@Setter
public class Reserve {
    private Long id;
    private String userid;
    private Long bookid;
    private LocalDateTime reserveDate;

    @Override
    public String toString() {
        String date = null;
        if (reserveDate != null) {
            date = reserveDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return id + "\t" + userid + "\t" + bookid + "\t" + date;
    }
}
