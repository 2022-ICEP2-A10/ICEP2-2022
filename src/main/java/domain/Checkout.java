package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
public class Checkout {
    private Long id;
    private String userid;
    private Long bookid;
    private LocalDateTime checkoutDate;
}
