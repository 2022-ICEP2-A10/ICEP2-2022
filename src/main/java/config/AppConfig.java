package config;

import controller.AdminController;
import controller.ControllerFacade;
import controller.LoginController;
import controller.MemberController;
import domain.*;
import repository.ReserveRepository;
import service.AdminService;
import presentation.MainPrompt;
import repository.BookRepository;
import repository.CheckoutRepository;
import repository.MemberRepository;
import service.*;
import util.CommandParser;
import util.Sequence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppConfig {

    private CommandParser commandParser;
    private MainPrompt mainPrompt;
    private ControllerFacade controllerFacade;
    private AdminController adminController;
    private LoginController loginController;
    private MemberController memberController;

    private CheckoutService checkoutService;
    private HelpService helpService;
    private LoginService loginService;
    private LogoutService logoutService;
    private MyLoanService myLoanService;
    private ReturnBookService returnBookService;
    private SearchService searchService;
    private SignupService signupService;
    private ReserveService reserveService;
    
    private MemberRepository memberRepository;
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private ReserveRepository reserveRepository;

    private Sequence sequence;
    
    private AdminService adminService;

    public MainPrompt mainPrompt() {
        if (mainPrompt == null) {
            mainPrompt = new MainPrompt(commandParser(), controllerFacade());
        }
        return mainPrompt;
    }

    private CommandParser commandParser() {
        if (commandParser == null) {
            commandParser = new CommandParser();
        }
        return commandParser;
    }

    private ControllerFacade controllerFacade() {
        if (controllerFacade == null) {
            controllerFacade = new ControllerFacade(loginController(), adminController(), memberController());
        }
        return controllerFacade;
    }

    private AdminService adminService() {
    	if (adminService == null) {
        	adminService = new AdminService(bookRepository(), memberRepository(),
                    checkoutRepository(), reserveRepository(), sequence());
        }
    	return adminService;
    }

    private AdminController adminController() {
        if (adminController == null) {
        	adminController = new AdminController(adminService());
        }
        return adminController;
    }

    private LoginController loginController() {
        if (loginController == null) {
            loginController = new LoginController(loginService(), signupService(), adminService());
        }
        return loginController;
    }

    private MemberController memberController() {
        if (memberController == null) {
            memberController = new MemberController(helpService(), checkoutService(), returnBookService(),
                    searchService(), myLoanService(), logoutService(), reserveService());
        }
        return memberController;
    }

    private ReserveService reserveService() {
        if (reserveService == null) {
            reserveService = new ReserveService(bookRepository(), reserveRepository());
        }
        return reserveService;
    }

    private CheckoutService checkoutService() {
        if (checkoutService == null) {
            checkoutService = new CheckoutService(bookRepository(), checkoutRepository(), reserveRepository());
        }
        return checkoutService;
    }

    private HelpService helpService() {
        if (helpService == null) {
            helpService = new HelpService();
        }
        return helpService;
    }

    private LoginService loginService() {
        if (loginService == null) {
            loginService = new LoginService(memberRepository());
        }
        return loginService;
    }

    private LogoutService logoutService() {
        if (logoutService == null) {
            logoutService = new LogoutService();
        }
        return logoutService;
    }

    private MyLoanService myLoanService() {
        if (myLoanService == null) {
            myLoanService = new MyLoanService(checkoutRepository(), bookRepository());
        }
        return myLoanService;
    }

    private ReturnBookService returnBookService() {
        if (returnBookService == null) {
            returnBookService = new ReturnBookService(checkoutRepository(), memberRepository(), bookRepository());
        }
        return returnBookService;
    }

    private SearchService searchService() {
        if (searchService == null) {
            searchService = new SearchService(bookRepository());
        }
        return searchService;
    }

    private SignupService signupService() {
        if (signupService == null) {
            signupService = new SignupService(memberRepository());
        }
        return signupService;
    }

    private Sequence sequence() {
        if (sequence == null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/next_sequence"), StandardCharsets.UTF_8))) {
                String line = reader.readLine();
                long nextSequence = Long.parseLong(line);
                sequence = new Sequence(nextSequence);
            } catch (IOException e) {
                makeDatafile("./data/next_sequence");
                sequence = new Sequence(1L);
            } catch (NumberFormatException e) {
                sequence = new Sequence(1L);
            }
        }
        return sequence;
    }

    private MemberRepository memberRepository() {
        if (memberRepository == null) {
            Map<String, Member> members = new LinkedHashMap<>() {{
                Member admin = Member.builder()
                        .userid("admin")
                        .password("admin")
                        .userType(UserType.ADMIN)
                        .build();
                put("admin", admin);
            }};

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/members"), StandardCharsets.UTF_8))) {

                String line;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    boolean possible = Boolean.parseBoolean(split[2]);
                    LocalDateTime possibleDate;
                    try {
                        possibleDate = LocalDateTime.parse(split[3], formatter);
                        // 현재 시간이 대출 가능 날짜 이후면 대출 가능으로 변경
                        if(LocalDateTime.now().isBefore(possibleDate)) {
                            possibleDate = null;
                            possible = true;
                        }
                    } catch (DateTimeParseException e) {
                        possibleDate = null;
                    }

                    UserType userType = UserType.valueOf(split[4]);
                    Member member = Member.builder()
                            .userid(split[0])
                            .password(split[1])
                            .possible(possible)
                            .possibleDate(possibleDate)
                            .userType(userType)
                            .build();
                    members.put(member.getUserid(), member);
                }
            } catch (IOException e) {
                makeDatafile("./data/members");
            }

            memberRepository = new MemberRepository(members);
        }

        return memberRepository;
    }

    private BookRepository bookRepository() {
        if (bookRepository == null) {
            Map<Long, Book> books = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/books"), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    long bookid = Long.parseLong(split[0]);
                    boolean isActive = Boolean.parseBoolean(split[2]);

                    Book book = Book.builder()
                            .id(bookid)
                            .title(split[1])
                            .isActive(isActive)
                            .build();
                    books.put(book.getId(), book);
                }
            } catch (IOException e) {
                makeDatafile("./data/books");
            }
            bookRepository = new BookRepository(books, sequence());
        }

        return bookRepository;
    }

    private CheckoutRepository checkoutRepository() {
        if (checkoutRepository == null) {
            Map<Long, Checkout> checkouts = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/checkouts"), StandardCharsets.UTF_8))) {

                String line;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    long checkoutid = Long.parseLong(split[0]);
                    String memberid = split[1];
                    long bookid = Long.parseLong(split[2]);
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
                makeDatafile("./data/checkouts");
            }
            checkoutRepository = new CheckoutRepository(checkouts, sequence());
        }

        return checkoutRepository;
    }

    private ReserveRepository reserveRepository() {
        if (reserveRepository == null) {
            Map<Long, Reserve> reserves = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/reserves"), StandardCharsets.UTF_8))) {

                String line;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    long reserveid = Long.parseLong(split[0]);
                    String memberid = split[1];
                    LocalDateTime reservedDate = LocalDateTime.parse(split[2], formatter);

                    Period period = Period.between(reservedDate.toLocalDate(), LocalDate.now());
                    if (period.getDays() > 7) {
                        continue;
                    }
                    Reserve reserve = Reserve.builder()
                            .bookid(reserveid)
                            .userid(memberid)
                            .reservedDate(reservedDate)
                            .build();
                    reserves.put(reserveid, reserve);
                }
            } catch (IOException e) {
                makeDatafile("./data/reserves");
            }
            reserveRepository = new ReserveRepository(reserves);
        }
        return reserveRepository;
    }

    private void makeDatafile(String pathname) {
        System.out.println(pathname + " 새로 생성");
        File file = new File(pathname);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
