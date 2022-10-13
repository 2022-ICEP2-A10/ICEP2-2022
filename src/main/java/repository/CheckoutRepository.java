package repository;

import domain.Checkout;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CheckoutRepository {

    private final Map<Long, Checkout> checkouts;

    public void save(Checkout checkout) {
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

    public void delete(Long id) {
        checkouts.remove(id);
    }

}
