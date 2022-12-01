package service;

import domain.Book;
import domain.Member;
import exceptions.MemberException;
import exceptions.ArgumentException;
import exceptions.ReserveException;
import lombok.RequiredArgsConstructor;

import domain.Checkout;
import repository.CheckoutRepository;
import repository.BookRepository;
import repository.ReserveRepository;
import util.CurrentMember;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@RequiredArgsConstructor
public class CheckoutService {

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;
	private final ReserveRepository reserveRepository;

    public void checkout(String[] args) {
    	long checkoutID;
    	try {
    		checkoutID = Long.parseLong(args[0]); //Long 타입 변환
    	} catch (NumberFormatException e) {
    		throw new ArgumentException("비정상적인 입력입니다.");
    	}

        final Member member = CurrentMember.getCurrentMember();

		if (!member.isPossible()) {//대출 가능 여부
        	if (member.getPossibleDate().isAfter(LocalDateTime.now())) {
        		throw new MemberException("사용자가 대출 불가능 상태입니다.");// 대출 불가능 시
        	}
        }

		Book book = bookRepository.findById(checkoutID)
				.orElseThrow(() -> new MemberException("등록되어 있지 않은 도서입니다"));

		if(!book.isActive()) {
			throw new MemberException("대출 중 도서입니다");
		}

		reserveRepository.findById(checkoutID)
				.ifPresent(reserve -> {
					Period period = Period.between(reserve.getReservedDate().toLocalDate(), LocalDate.now());
					if (period.getDays() <= 7 && !reserve.getUserid().equals(member.getUserid())) {
						throw new ReserveException("이미 예약된 책입니다.");
					}
					reserveRepository.deleteById(checkoutID);
				});
  
        Checkout checkout = Checkout.builder()
                .userid(member.getUserid())
                .bookid(checkoutID)
                .checkoutDate(LocalDateTime.now())
                .build();

	    checkoutRepository.save(checkout);
	
	    book.setActive(false);
	    bookRepository.save(book);
	
	    System.out.println("도서 대출이 완료되었습니다.");
    }
}
