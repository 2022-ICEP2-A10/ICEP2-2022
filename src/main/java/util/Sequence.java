package util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Sequence {
    private Long sequence;

    public Long nextSequence() {
        return sequence++;
    }
}
