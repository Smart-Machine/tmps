package lab2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import lab2.enums.Option;
import lab2.factory.TermEditorCommandExecutor;
import lab2.factory.TermEditorCreateCommandExecutor;
import lab2.factory.TermEditorDeleteCommandExecutor;
import lab2.factory.TermEditorEditCommandExecutor;

public final class TermEditorCLI {
    private static TermEditorCLI instance;
    private static Scanner scanner;
    private static String[] args;
    private static String helpMessage;
    private static TermEditorCommandExecutor termEditorCommandExecutor;

    private TermEditorCLI() {
        TermEditorCLI.helpMessage = "TermEditor is the simplest terminal editor that will help you write, edit, and delete files.\n"
                +
                "Options:\n" +
                "\t 1. Create :: This option creates a new file with the content that you provided.\n" +
                "\t 2. Edit   :: This option prints the content of the provided file and rewrites its content.\n" +
                "\t 3. Delete :: This option deletes the provided file.\n";
    }

    public static TermEditorCLI getInstance() {
        if (instance == null) {
            instance = new TermEditorCLI();
        }
        return instance;
    }

    public void init(Scanner scanner, String[] args) {
        if (TermEditorCLI.scanner == null) {
            TermEditorCLI.scanner = scanner;
        }
        if (TermEditorCLI.args == null) {
            TermEditorCLI.args = args;
        }
    }

    public void start() {
        checkHelpFlag();
        Option option = getUserInputOption();
        String filename = getUserInputFilename();

        try {
            termEditorCommandExecutor = getTermEditorCommandExecutorInstance(option, filename);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(-1);
        }
        termEditorCommandExecutor.executeCommand();
    }

    public void checkHelpFlag() {
        for (String arg : args) {
            if (arg.equals("-h") || arg.equals("--help")) {
                System.out.println(helpMessage);
                System.exit(0);
            }
        }
    }

    public Option getUserInputOption() {
        String userOption = "";
        System.out.print("Options:\n\t1. Create\n\t2. Edit\n\t3. Delete\n");
        System.out.print("Option := ");
        userOption = scanner.nextLine();
        ArrayList<String> optionArray = new ArrayList<String>(Arrays.asList("1", "2", "3"));
        while (!optionArray.contains(userOption)) {
            System.out.println("Wrong option, please choose one from the list.");
            System.out.print("Option := ");
            userOption = scanner.nextLine();
        }

        switch (userOption) {
            case "1":
                return Option.CREATE;
            case "2":
                return Option.EDIT;
            case "3":
                return Option.DELETE;
        }
        return Option.INVALID;
    }

    public String getUserInputFilename() {
        String filename = "";
        System.out.print("Filename := ");
        filename = scanner.nextLine();
        while (filename.equals("")) {
            System.out.println("Provide a name for the file.");
            System.out.print("Filename := ");
            filename = scanner.nextLine();
        }

        return filename;
    }

    public TermEditorCommandExecutor getTermEditorCommandExecutorInstance(Option option, String fileName) throws Exception {
        switch (option) {
            case CREATE:
                return new TermEditorCreateCommandExecutor(fileName);
            case EDIT:
                return new TermEditorEditCommandExecutor(fileName);
            case DELETE:
                return new TermEditorDeleteCommandExecutor(fileName);
            case INVALID:
                throw new Exception("Invalid option for TermEditorCommand instance was given");
            default:
                throw new Exception("No option for TermEditorCommand instance was matched");
        }
    }
}
