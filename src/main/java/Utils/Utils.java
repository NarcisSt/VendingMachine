package Utils;

import Shell.*;

import java.util.*;

/**
 * Class Utils contains methods that will help with the shell commands and the logic of the machine.
 */
public class Utils {
    /**
     * Method that checks if a string given as parameter is a positive integer
     * @param str
     * @return
     */
    public static boolean stringIsPositiveNat(String str) {
        if (str.length() > 9 || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method that is used by the shells and reads a line from the standard input
     * @param scanner
     * @param message
     * @return the line
     */
    public static String readLine(Scanner scanner, String message) {
        String line;
        while (true) {
            if (message != null) {
                System.out.print(message);
            }
            line = scanner.nextLine();
            if (line.length() == 0) {
                continue;
            }
            break;
        }
        return line.trim();
    }

    /**
     * Method that reads an option from the standard input, and prints a message
     * @param scanner
     * @param message
     * @param options
     * @return the option
     */
    public static String readStringOption(Scanner scanner, String message, String[] options) {
        while (true) {
            System.out.print("Options: {");
            for (int i = 0; i < options.length; i++) {
                System.out.print(" " + options[i]);
                if (i + 1 < options.length) {
                    System.out.print(",");
                }
            }
            System.out.println(" }");
            System.out.print(message);
            String line = scanner.nextLine();
            for (String option : options) {
                if (option.equals(line)) {
                    return option;
                }
            }
            System.out.println("You must put a valid option!");
        }
    }

    /**
     * Method that reads a positive integer from the stdin, and prints a message
     * @param scanner
     * @param message
     * @return the number
     */
    public static int readIntOption(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String line = scanner.nextLine();
            try {
                int nr =Integer.parseInt(line);
                if (nr < 0) {
                    System.out.println("You must put a positive number!");
                } else {
                    return nr;
                }
            } catch (Exception e) {
                System.out.println("You must put a positive number!");
            }
        }
    }

    /**
     * Method that will execute the wanted command
     * @param shell
     * @param cmd
     * @return true if the command was successfully executed, false otherwise
     */
    public static boolean executeCommand(Shell shell, Command cmd) {
        switch (cmd.getCommandEnum()) {
            case LIST: {
                System.out.println(shell);
                break;
            }
            case COKE: {
                //TODO logic for coke
                break;
            }
            case HELL: {
                //TODO logic for hell
                break;
            }
            case LAYS: {
                //TODO logic for lays
                break;
            }
            case NUTLINE: {
                //TODO logic for nutline
                break;
            }
            case SNICKERS: {
                //TODO logic for snickers
                break;
            }
            case MARS: {
                //TODO logic for Mars
                break;
            }
            case MASK: {
                //TODO logic for mask
                break;
            }
            case SANDWICH: {
                //TODO logic for sandwich
                break;
            }
            case QUIT: {
                return false;
            }
            default: {
                System.out.println("Invalid command!");
            }
        }
        return true;
    }

    /**
     * Method that initializes the shell.
     * @return the shell
     */
    public static Shell getShell() {
        Shell shell = new Shell();

        shell.addCommand(new Command(CommandEnum.LIST, "list"));
        shell.addCommand(new Command(CommandEnum.COKE, "coke"));
        shell.addCommand(new Command(CommandEnum.HELL, "hell"));
        shell.addCommand(new Command(CommandEnum.LAYS, "lays"));
        shell.addCommand(new Command(CommandEnum.NUTLINE, "nutline"));
        shell.addCommand(new Command(CommandEnum.SNICKERS, "snickers"));
        shell.addCommand(new Command(CommandEnum.MARS, "mars"));
        shell.addCommand(new Command(CommandEnum.MASK, "mask"));
        shell.addCommand(new Command(CommandEnum.SANDWICH, "sandwich"));
        shell.addCommand(new Command(CommandEnum.QUIT, "quit"));

        return shell;
    }

}
