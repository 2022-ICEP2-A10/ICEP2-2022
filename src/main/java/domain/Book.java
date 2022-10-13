package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Book {
    private Long id;
    private String title;
    private boolean isActive;
}
