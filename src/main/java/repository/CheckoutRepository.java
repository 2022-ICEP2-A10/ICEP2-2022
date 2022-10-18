package repository;

import domain.Checkout;
import lombok.RequiredArgsConstructor;
import util.Sequence;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                new FileOutputStream("./data/checkouts")))) {
            checkouts.values()
                    .forEach(checkout -> {
                        try {
                            writer.write(checkout.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
