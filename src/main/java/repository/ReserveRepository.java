package repository;

import domain.Reserve;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ReserveRepository {

    private final Map<Long, Reserve> reserves;

    public Optional<Reserve> findById(long bookid) {
        return Optional.ofNullable(reserves.get(bookid));
    }

    public void deleteById(long bookid) {
        reserves.remove(bookid);
    }
}
