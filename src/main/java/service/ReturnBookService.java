package service;

import domain.Book;
import domain.Member;
import exceptions.MemberException;
import exceptions.ArgumentException;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import domain.Checkout;
import repository.CheckoutRepository;
import repository.BookRepository;
import repository.MemberRepository;
import util.CurrentMember;

@RequiredArgsConstructor
public class ReturnBookService {

    private final CheckoutRepository checkoutRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    
    public void returnBook(String[] args) {
    	long returnID;
    	try {
    		returnID = Long.parseLong(args[0]); //Long 타입 변환
    	} catch (NumberFormatException e) {
    		throw new ArgumentException("비정상적인 입력입니다.");
    	}

        List<Checkout> checkouts = checkoutRepository.findAllByUserid(CurrentMember.getCurrentMember().getUserid());

        long bookID = 0;

        for (Checkout checkout : checkouts) {
            if (returnID == checkout.getBookid()) {
                checkoutRepository.deleteById(checkout.getId());
                LocalDateTime returnDateTime = LocalDateTime.now();//현재 시각 = 반납 일자
                LocalDateTime checkoutDateTime = checkout.getCheckoutDate();// 대출 당시 시각

                long betweenDays = Duration.between(checkoutDateTime, returnDateTime).toDays();

                if (betweenDays > 7) {//시간 차가 7일보다 크면(무조건 반납 일자가 나중이어야 함) 연체
                    Member member = CurrentMember.getCurrentMember();
                    member.setPossible(false);
                    member.setPossibleDate(returnDateTime.plusDays(betweenDays - 7));
                    memberRepository.save(member);
                    System.out.println("반납된 도서가 연체되었습니다.");
                }
                Book book = bookRepository.findById(returnID).get();
                book.setActive(true);
                bookRepository.save(book);
                System.out.println("도서 반납이 완료되었습니다.");
                return;
            }
        }

        throw new MemberException("등록되어 있지 않은 도서입니다");//등록 안 되었을 시(미대출 시도 포함)

    }
}
