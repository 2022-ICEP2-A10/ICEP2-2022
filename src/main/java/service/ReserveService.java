package service;

import domain.Book;
import domain.Member;
import domain.Reserve;
import exceptions.ArgumentException;
import exceptions.MemberException;
import exceptions.ReserveException;
import lombok.RequiredArgsConstructor;
import repository.BookRepository;
import repository.ReserveRepository;
import util.CurrentMember;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReserveService {

    private final BookRepository bookRepository;
    private final ReserveRepository reserveRepository;

    public void reserve(String[] args) {
        long reserveId;
        try {
            reserveId = Long.parseLong(args[0]); //Long 타입 변환
        } catch (NumberFormatException e) {
            throw new ArgumentException("비정상적인 입력입니다.");
        }

        Book book = bookRepository.findById(reserveId)
                .orElseThrow(() -> new MemberException("등록되어 있지 않은 책입니다."));
        if (book.isActive()) {
            throw new ReserveException("대출 가능한 책입니다.");
        }

        Member currentMember = CurrentMember.getCurrentMember();
        if (!currentMember.isPossible()) {
            if (currentMember.getPossibleDate().isAfter(LocalDateTime.now())) {
                throw new MemberException("사용자가 대출/예약 불가능 상태입니다.");
            }
        }

        reserveRepository.findById(reserveId)
                .ifPresent(reserve -> {
                    throw new ReserveException("이미 예약된 책입니다.");
                });

        Reserve reserve = Reserve.builder()
                .userid(currentMember.getUserid())
                .bookid(reserveId)
                .reservedDate(LocalDateTime.now())
                .build();
        reserveRepository.save(reserve);
    }
    
    public void myreserve(String[] args) {
		List<Reserve> reserve = reserveRepository.findAllByUserid(CurrentMember.getCurrentMember().getUserid());

		System.out.println("현재 예약 중인 도서의 목록입니다.");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (Reserve r : reserve) {
			Optional<Book> book = bookRepository.findById(r.getBookid());
			LocalDateTime d = r.getReservedDate();
			LocalDateTime expire = r.getReservedDate().plusDays(7);
			String active;
			if (book.get().isActive()) {
				active = "O";
			} else {
				active = "X";
			}
			String str = r.getBookid() + " " + book.get().getTitle() + "\n" 
					+ "반납 여부: " + active + "\n"
					+ "예약 날짜: " + d.format(dateTimeFormatter) + "\n"
					+ "예약 만료 날짜: " + expire.format(dateTimeFormatter);

			System.out.println(str);
		}
	}
}
