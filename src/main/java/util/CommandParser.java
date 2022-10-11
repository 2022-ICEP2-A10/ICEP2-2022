package util;

import java.util.Arrays;

public class CommandParser {

    public String[] parse(String commandline) {
         return commandline.split(" ");
    }

    public String getCommand(String[] parsed) {
        return parsed[0];
    }

    public String[] getArguments(String[] parsed) {
        return Arrays.copyOfRange(parsed, 1, parsed.length);
    }
}
