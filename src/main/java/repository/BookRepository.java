package repository;

import domain.Book;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookRepository {

    private final Map<Long, Book> books;

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public void save(Book book) {
        books.put(book.getId(), book);
    }

    public List<Book> findAllByTitle(String title) {
        return books.values()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }


}
