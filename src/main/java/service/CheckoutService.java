package service;

import domain.Book;
import domain.Member;
import exceptions.MemberException;
import exceptions.ArgumentException;
import lombok.RequiredArgsConstructor;

import domain.Checkout;
import repository.CheckoutRepository;
import repository.BookRepository;
import repository.MemberRepository;
import util.CurrentMember;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CheckoutService {

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;
    public void checkout(String[] args) {
    	long checkoutID;
    	try {
    		checkoutID = Long.parseLong(args[0]); //Long 타입 변환
    	} catch (NumberFormatException e) {
    		throw new ArgumentException("비정상적인 입력입니다.");
    	}

        final Member member = CurrentMember.getCurrentMember();

        if (!member.isPossible()) {//대출 가능 여부
            throw new MemberException("사용자가 대출 불가능 상태입니다.");// 대출 불가능 시
        } else {
            Checkout checkout = Checkout.builder()
                                //.userid();
                                .bookid(checkoutID)
                                .checkoutDate(LocalDateTime.now())
                                .build();

            checkoutRepository.save(checkout);
            Book book = bookRepository.findById(checkoutID)
                    .orElseThrow(() -> new MemberException("등록되어 있지 않은 도서입니다"));
            book.setActive(false);

            bookRepository.save(book);

            System.out.println("도서 대출이 완료되었습니다.");
            return;
        }

    }
}
