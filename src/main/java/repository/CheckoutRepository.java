package repository;

import domain.Checkout;
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
public class CheckoutRepository implements FileBaseDatabase {

    private final Map<Long, Checkout> checkouts;
    private final Sequence sequence;

    public void save(Checkout checkout) {
        checkout.setId(sequence.nextSequence());
        checkouts.put(checkout.getId(), checkout);
    }

    public List<Checkout> findAll() {
        return new ArrayList<>(checkouts.values());
    }

    public Optional<Checkout> findById(Long checkoutid) {
        return Optional.ofNullable(checkouts.get(checkoutid));
    }

    public List<Checkout> findAllByUserid(String userid) {
        return checkouts.values()
                .stream()
                .filter(checkout -> checkout.getUserid().equals(userid))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        checkouts.remove(id);
    }

    @Override
    public void destroy() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/checkouts"), StandardCharsets.UTF_8))) {
            for (Checkout checkout : checkouts.values()) {
                writer.write(checkout.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
