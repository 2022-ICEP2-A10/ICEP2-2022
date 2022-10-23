package presentation;

import controller.ControllerFacade;
import util.CurrentPrompt;
import domain.PromptStatusType;
import exceptions.ArgumentException;
import exceptions.CommandException;
import lombok.RequiredArgsConstructor;
import util.CommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class MainPrompt {

    private final CommandParser commandParser;
    private final ControllerFacade controllerFacade;

    public void start() {
        try (BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (CurrentPrompt.getCurStatus() != PromptStatusType.EXIT) {
                System.out.print("$ ");
                try {
                    String commandline = commandReader.readLine();
                    String[] parsed = commandParser.parse(commandline);
                    String command = commandParser.getCommand(parsed);
                    String[] args = commandParser.getArguments(parsed);
                    controllerFacade.execute(command, args);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (CommandException e) {
                    System.out.println("비정상적인 입력입니다.\n" +
                            "명령어를 확인하려면 help를 입력하세요.");
                } catch (ArgumentException e) {
                    System.out.println("비정상적인 입력입니다.\n" +
                            "인자의 개수가 잘못되었습니다.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
