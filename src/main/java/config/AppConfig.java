package config;

import controller.AdminController;
import controller.ControllerFacade;
import controller.LoginController;
import controller.MemberController;
import domain.Book;
import domain.Checkout;
import domain.Member;
import domain.UserType;
import repository.BookRepository;
import repository.CheckoutRepository;
import repository.MemberRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppConfig {

    private ControllerFacade controllerFacade;
    private AdminController adminController;
    private LoginController loginController;
    private MemberController memberController;

    private MemberRepository memberRepository;
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    public ControllerFacade controllerFacade() {
        if (controllerFacade == null) {
            controllerFacade = new ControllerFacade(loginController(), adminController(), memberController());
        }
        return controllerFacade;
    }

    public AdminController adminController() {
        if (adminController == null) {

        }
        return adminController;
    }

    public LoginController loginController() {
        if (loginController == null) {

        }
        return loginController;
    }

    public MemberController memberController() {
        if (memberController == null) {

        }
        return memberController;
    }

    public MemberRepository memberRepository() {

        if (memberRepository == null) {
            Map<String, Member> members = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/members")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    boolean possible = split[2].equals("true");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    LocalDateTime time = LocalDateTime.parse(split[3], formatter);
                    UserType userType = (split[4].equals("member")) ? UserType.MEMBER : UserType.ADMIN;
                    Member member = Member.builder()
                            .userid(split[0])
                            .password(split[1])
                            .possible(possible)
                            .possibleDate(time)
                            .userType(userType)
                            .build();
                    members.put(member.getUserid(), member);
                }
            } catch (IOException e) {
                System.out.println("data/members 파일 없어");
                File memberFile = new File("./data/members");
                memberFile.getParentFile().mkdirs();
                try {
                    memberFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            memberRepository = new MemberRepository(members);
        }

        return memberRepository;
    }

    public BookRepository bookRepository() {
        if (bookRepository == null) {
            Map<Long, Book> books = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/books")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    long bookid = Long.parseLong(split[0]);
                    boolean isActive = split[2].equals("true");

                    Book book = Book.builder()
                            .id(bookid)
                            .title(split[1])
                            .isActive(isActive)
                            .build();
                    books.put(book.getId(), book);
                }
            } catch (IOException e) {
                System.out.println("data/members 파일 없어");
                File memberFile = new File("./data/books");
                memberFile.getParentFile().mkdirs();
                try {
                    memberFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            bookRepository = new BookRepository(books);
        }

        return bookRepository;
    }

    public CheckoutRepository checkoutRepository() {
        if (checkoutRepository == null) {
            Map<Long, Checkout> checkouts = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/checkouts")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    long checkoutid = Long.parseLong(split[0]);
                    String memberid = split[1];
                    long bookid = Long.parseLong(split[2]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    LocalDateTime time = LocalDateTime.parse(split[3], formatter);

                    Checkout checkout = Checkout.builder()
                            .id(checkoutid)
                            .userid(memberid)
                            .bookid(bookid)
                            .checkoutDate(time)
                            .build();
                    checkouts.put(checkout.getId(), checkout);
                }
            } catch (IOException e) {
                System.out.println("data/checkouts 파일 없어");
                File memberFile = new File("./data/checkouts");
                memberFile.getParentFile().mkdirs();
                try {
                    memberFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            checkoutRepository = new CheckoutRepository(checkouts);
        }

        return checkoutRepository;
    }
}
