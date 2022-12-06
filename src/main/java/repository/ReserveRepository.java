package repository;

import domain.Checkout;
import domain.Reserve;
import lombok.RequiredArgsConstructor;
import util.Sequence;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReserveRepository implements FileBaseDatabase {

    private final Map<Long, Reserve> reserves;
    private final Sequence sequence;

    public void save(Reserve reserve) {
        reserve.setId(sequence.nextSequence());
        reserves.put(reserve.getId(), reserve);
    }

    public List<Reserve> findAll() {
        return new ArrayList<>(reserves.values());
    }

    public Optional<Reserve> findById(Long reserveid) {
        return Optional.ofNullable(reserves.get(reserveid));
    }

    public List<Reserve> findAllByUserid(String userid) {
        return reserves.values()
                .stream()
                .filter(reserve -> reserve.getUserid().equals(userid))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        reserves.remove(id);
    }

    @Override
    public void destroy() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/checkouts"), StandardCharsets.UTF_8))) {
            for (Reserve reserve : reserves.values()) {
                writer.write(reserve.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
