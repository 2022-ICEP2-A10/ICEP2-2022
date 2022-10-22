package config;

import controller.AdminController;
import controller.ControllerFacade;
import controller.LoginController;
import controller.MemberController;
import domain.Book;
import domain.Checkout;
import domain.Member;
import domain.Status;
import domain.UserType;
import librarian.Librarian;
import presentation.MainPrompt;
import repository.BookRepository;
import repository.CheckoutRepository;
import repository.MemberRepository;
import service.*;
import util.CommandParser;
import util.Sequence;

import java.io.*;
import java.time.LocalDateTime;
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
    
    private MemberRepository memberRepository;
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    private Sequence sequence;
    
    private Librarian librarian;

    public MainPrompt mainPrompt() {
        if (mainPrompt == null) {
            mainPrompt = new MainPrompt(commandParser(), controllerFacade());
        }
        return mainPrompt;
    }

    public CommandParser commandParser() {
        if (commandParser == null) {
            commandParser = new CommandParser();
        }
        return commandParser;
    }

    public ControllerFacade controllerFacade() {
        if (controllerFacade == null) {
            controllerFacade = new ControllerFacade(loginController(), adminController(), memberController());
        }
        return controllerFacade;
    }
   
    public Librarian makeLibrarian() {
    	if (librarian == null) {
        	librarian=new Librarian(bookRepository(), memberRepository(), checkoutRepository(), sequence());
        }
    	return librarian;
    }
    
   
    public AdminController adminController() {
        if (adminController == null) {
        	adminController=new AdminController(makeLibrarian());
        }
        return adminController;
    }

    public LoginController loginController() {
        if (loginController == null) {
            loginController = new LoginController(loginService(), signupService(), makeLibrarian());
        }
        return loginController;
    }

    public MemberController memberController() {
        if (memberController == null) {
            memberController = new MemberController(helpService(), checkoutService(), returnBookService(),
                    searchService(), myLoanService(), logoutService());
        }
        return memberController;
    }

    public CheckoutService checkoutService() {
        if (checkoutService == null) {
            checkoutService = new CheckoutService(bookRepository(), checkoutRepository());
        }
        return checkoutService;
    }

    public HelpService helpService() {
        if (helpService == null) {
            helpService = new HelpService();
        }
        return helpService;
    }

    public LoginService loginService() {
        if (loginService == null) {
            loginService = new LoginService(memberRepository());
        }
        return loginService;
    }

    public LogoutService logoutService() {
        if (logoutService == null) {
            logoutService = new LogoutService();
        }
        return logoutService;
    }

    public MyLoanService myLoanService() {
        if (myLoanService == null) {
            myLoanService = new MyLoanService(checkoutRepository());
        }
        return myLoanService;
    }

    public ReturnBookService returnBookService() {
        if (returnBookService == null) {
            returnBookService = new ReturnBookService(checkoutRepository(), memberRepository(), bookRepository());
        }
        return returnBookService;
    }

    public SearchService searchService() {
        if (searchService == null) {
            searchService = new SearchService(bookRepository());
        }
        return searchService;
    }

    public SignupService signupService() {
        if (signupService == null) {
            signupService = new SignupService(memberRepository());
        }
        return signupService;
    }

    public Sequence sequence() {
        if (sequence == null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/next_sequence")))) {
                String line = reader.readLine();
                long nextSequence = Long.parseLong(line);
                sequence = new Sequence(nextSequence);
            } catch (IOException e) {
                makeDatafile("./data/next_sequence");
            } catch (NumberFormatException e) {
                sequence = new Sequence(1L);
            }
        }
        return sequence;
    }

    public MemberRepository memberRepository() {
        if (memberRepository == null) {
            Map<String, Member> members = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/members")))) {

                String line;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("\t");
                    boolean possible = Boolean.parseBoolean(split[2]);
                    LocalDateTime time;
                    try {
                        time = LocalDateTime.parse(split[3], formatter);
                    } catch (DateTimeParseException e) {
                        time = null;
                    }
                    //날짜 넘었으면 대출가능으로 변경
                    if(!possible) {
                    	 if(LocalDateTime.now().isBefore(time)) {
                         	time=null;
                         	possible=true;
                         }
                    }
                    UserType userType = UserType.valueOf(split[4]);
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
                makeDatafile("./data/members");
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

    public CheckoutRepository checkoutRepository() {
        if (checkoutRepository == null) {
            Map<Long, Checkout> checkouts = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("./data/checkouts")))) {

                String line;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

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
