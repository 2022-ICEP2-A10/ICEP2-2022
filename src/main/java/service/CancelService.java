package service;

import domain.Book;
import domain.Member;
import exceptions.MemberException;
import exceptions.ArgumentException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import domain.Checkout;
import repository.CheckoutRepository;
import repository.BookRepository;
import repository.MemberRepository;
import util.CurrentMember;

@RequiredArgsConstructor
public class CancelService {

    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    
    public void cancel(String[] args) {
    	long cancelID;
    	try {
    		cancelID = Long.parseLong(args[0]); //Long 타입 변환
    	} catch (NumberFormatException e) {
    		throw new ArgumentException("비정상적인 입력입니다.");
    	}

        List<Reserve> reserves = reserveRepository.findAllByUserid(CurrentMember.getCurrentMember().getUserid());

        long bookID = 0;

        for (Reserve reserve : reserves) {
            if (cancelID == reserve.getBookid()) {
            	reserveRepository.deleteById(reserve.getId());

                Book book = bookRepository.findById(cancelID).get();
                book.setActive(true);
                bookRepository.save(book);
                System.out.println("예약이 취소되었습니다.");
                return;
            }
        }

        throw new MemberException("예약 중인 도서가 아닙니다");//등록 안 되었을 시(미예약 시도 포함)

    }
}
