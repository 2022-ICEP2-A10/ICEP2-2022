package librarian;

import exceptions.ArgumentException;
import exceptions.CommandException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import domain.Status;
import domain.StatusType;
import domain.Book;
import domain.Checkout;
import domain.Member;
import repository.BookRepository;
import repository.CheckoutRepository;
import repository.MemberRepository;

import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.util.Map.Entry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@RequiredArgsConstructor
public class Librarian {


	private final BookRepository books;
	private final MemberRepository memberRepository;
	private final CheckoutRepository checkoutRepository;
	


	// help
	public void librarian_help(String[] args) {
		if (args.length != 0) {
			System.out.println("인자의 개수가 잘못되었습니다.");
            throw new ArgumentException();
        }else {
        	System.out.println("사서 프롬프트의 명령어입니다.");
    		System.out.println("- help : 현재 프롬프트에서 입력할 수 있는 명령어를 출력합니다.");
    		System.out.println("- register : 도서를 등록합니다.");
    		System.out.println("- members : 회원정보를 출력합니다.");
    		System.out.println("- loans : 대출정보를 출력합니다.");
    		System.out.println("- logout : 로그아웃 합니다.");
        }
	}

	// 대출 정보 출력
	public void show_Checkout_Book(String[] args) {
		if (args.length != 0) {
			System.out.println("인자의 개수가 잘못되었습니다.");
			throw new ArgumentException();
        }
		else {
			List<Checkout> checkouts=checkoutRepository.findAll();
			
			HashMap<String, ArrayList<Long>> map = new HashMap<>();
			for (int i = 0; i < checkouts.size(); i++) {
				Checkout ch = checkouts.get(i);
				if (map.containsKey(ch.getUserid())) {
					ArrayList<Long> ar = map.get(ch.getUserid());
					ar.add(ch.getBookid());
				} else {
					map.put(ch.getUserid(), new ArrayList<Long>() {
						{
							add(ch.getBookid());
						}
					});
				}
			}
			for (Entry<String, ArrayList<Long>> entrySet : map.entrySet()) {
				String userid = entrySet.getKey();
				ArrayList<Long> bookids = entrySet.getValue();
				System.out.print(userid + ":");
				for (int i = 0; i < bookids.size()-1; i++) {
					System.out.print(bookids.get(i) + ",");
				}
				System.out.println(bookids.get(bookids.size()-1));
			}
			
			
		}
	}

	// 회원 정보 출력
	public void show_Memberlist(String[] args) {
		if (args.length != 0) {
			System.out.println("인자의 개수가 잘못되었습니다.");
            throw new ArgumentException();
        }
		else {
			List<Member> members = memberRepository.findAll();
			for (int i = 0; i < members.size();i++) {
				Member mem = members.get(i);
				String userid = mem.getUserid();
				LocalDateTime time = mem.getPossibleDate();
				if (mem.isPossible()) {
					System.out.println(userid + ": 대출가능");
				} else {
					System.out.println(userid + ": 대출불가 ~ " + time);
				}
			}
		}
	}

	// 도서 등록
	public void regist_Book(String[] bookname) throws IOException {
		// 비정상 종료 전 저장
		if (bookname.length <= 0) {
			System.out.println("인자의 개수가 잘못되었습니다.");
			throw new ArgumentException();
		}
		else {
			String name = "";
			for (int i = 0; i < bookname.length; i++) {
				name = name + bookname[i];
			}


			Book inputbook= Book.builder()
                     .title(name)
                     .isActive(true)
                     .build();
			books.save(inputbook);
			System.out.println("도서가 등록되었습니다.");
		}	

	}

	// 로그 아웃과
	// 동시에 등록한 도서 데이터파일에 입력
	public void logout(String[] args) {
		if (args.length != 0) {
			System.out.println("인자의 개수가 잘못되었습니다.");
            throw new ArgumentException();
        }
		else {
			System.out.println("로그아웃 되었습니다.");
			books.destroy();
			Status.changeStatus(StatusType.LOGIN);	
		}

	}
	public void exit(String[] args) {
		System.out.println("종료 되었습니다.");
		books.destroy();
	}
}