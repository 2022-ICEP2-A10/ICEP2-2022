package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Book 클래스 빌더 패턴 예시
 * Book book = Book.builder()
 *         .title("책이름")
 *         .isActive(true)
 *         .build()
 */
@Builder
@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private boolean isActive;
}
