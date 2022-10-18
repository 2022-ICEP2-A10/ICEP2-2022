package repository;

import domain.Book;
import lombok.RequiredArgsConstructor;
import util.Sequence;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookRepository implements FileBaseDatabase {

    private final Map<Long, Book> books;
    private final Sequence sequence;

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public void save(Book book) {
        if (book.getId() == null) {
            book.setId(sequence.nextSequence());
        }
        books.put(book.getId(), book);
    }

    public List<Book> findAllByTitle(String title) {
        return books.values()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public void destroy() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/books")))) {
            for (Book book : books.values()) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
