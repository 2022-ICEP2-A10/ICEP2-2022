import config.AppConfig;
import presentation.MainPrompt;

public class ICEP2A10Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MainPrompt mainPrompt = appConfig.mainPrompt();
        mainPrompt.start();
    }
}
