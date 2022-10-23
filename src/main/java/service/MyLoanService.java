package service;

import lombok.RequiredArgsConstructor;
import repository.CheckoutRepository;
import repository.BookRepository;
import domain.Book;
import domain.Checkout;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import util.CurrentMember;

@RequiredArgsConstructor
public class MyLoanService {

	private final CheckoutRepository checkoutRepository;
	private final BookRepository bookRepository;
	 public void myloan(String[] args) {

		 List<Checkout> checkout = checkoutRepository.findAllByUserid(CurrentMember.getCurrentMember().getUserid());
		 
		 System.out.println("현재 대출 중인 도서의 목록입니다.");
		 
		 for(Checkout c:checkout) {
			 Optional<Book> book = bookRepository.findById(c.getBookid());
			 
			 LocalDateTime d = c.getCheckoutDate().plusDays(7);
			 String str = c.getBookid() + " " + book.get().getTitle() + "\n"
			 +"반납기한: "+ d.getYear() + d.getMonthValue() + d.getDayOfMonth();
			 
			 System.out.println(str);		 
		 }
	 }
}
