package service;

import lombok.RequiredArgsConstructor;

import repository.BookRepository;
import domain.Book;
import java.util.List;

@RequiredArgsConstructor
public class SearchService {

	private final BookRepository bookRepository;

	 public void search(String[] args) {
		 final String title = String.join(" ", args);

		 List<Book> books = bookRepository.findAllByTitle(title);
		 System.out.println("검색된 도서입니다.");
		 for(Book book:books) {
			 String active;
			 if (book.isActive()) {
				 active = "가능";
			 } else {
				 active = "불가능";
			 }
			 System.out.println(book.getId() + "\t" + book.getTitle() + "\n대출 가능 여부: " + active);
		 }
		 
	 }
}
