package presentation;

import controller.ControllerFacade;
import domain.Status;
import domain.StatusType;
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
        BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));

        while (Status.getCurStatus() != StatusType.EXIT) {
            try {
                String commandline = commandReader.readLine();
                String[] parsed = commandParser.parse(commandline);
                String command = commandParser.getCommand(parsed);
                String[] args = commandParser.getArguments(parsed);
                controllerFacade.execute(command, args);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CommandException e) {
                System.out.println("명령어 잘못됐다는 메시지");
            } catch (ArgumentException e) {
                System.out.println("인자 잘못됐다는 메시지");
            }

        }
    }
}
