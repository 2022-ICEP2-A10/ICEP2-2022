package repository;

import domain.Member;
import domain.Reserve;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ReserveRepository implements FileBaseDatabase {

    private final Map<Long, Reserve> reserves;

    public void save(Reserve reserve) {
        reserves.put(reserve.getBookid(), reserve);
    }

    public Optional<Reserve> findById(long bookid) {
        return Optional.ofNullable(reserves.get(bookid));
    }

    public void deleteById(long bookid) {
        reserves.remove(bookid);
    }

    @Override
    public void destroy() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/reserves"), StandardCharsets.UTF_8))) {
            for (Reserve reserve : reserves.values()) {
                writer.write(reserve.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
