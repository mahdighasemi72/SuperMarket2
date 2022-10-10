package View;

import Controller.SupermarketController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SupermarketController controller = SupermarketController.getInstance();

    public static void start(){
        while (true){
            String command = scanner.nextLine().trim();
            if(ConsoleCommand.EXIT.getStringMatcher(command).matches()) {
                break;
            } else if(ConsoleCommand.ADD_GOOD.getStringMatcher(command).matches()){
                //TODO
            }else if(ConsoleCommand.NEW_ORDER.getStringMatcher(command).matches()){
                //TODO
            }else if(ConsoleCommand.GOODS_LIST.getStringMatcher(command).matches()){
                //TODO
            }else if(ConsoleCommand.TOTAL_SALES.getStringMatcher(command).matches()){
                //TODO
            }else if(ConsoleCommand.TOTAL_PROFIT.getStringMatcher(command).matches()){
                //TODO
            }else {
                System.out.println("Invalid Command");
            }
        }
    }

    private static String getInputInFormatWithError (String helpText, String regex, String error){
        Pattern pattern = Pattern.compile(regex);
        boolean inputIsInvalid;
        String line;
        do {
            System.out.println(helpText);
            line = scanner.nextLine().trim();
            Matcher matcher = pattern.matcher(line);
            inputIsInvalid = !matcher.find();
            if(inputIsInvalid)
                System.out.println(error);
        }while (inputIsInvalid);
        return line;
    }
    private static String getInputInFormat(String helpText, String regex){
        return getInputInFormatWithError(helpText,regex,"");
    }
}
