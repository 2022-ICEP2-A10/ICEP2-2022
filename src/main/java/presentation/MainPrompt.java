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
        try (BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (Status.getCurStatus() != StatusType.EXIT) {
                try {
                    String commandline = commandReader.readLine();
                    String[] parsed = commandParser.parse(commandline);
                    String command = commandParser.getCommand(parsed);
                    String[] args = commandParser.getArguments(parsed);
                    controllerFacade.execute(command, args);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
