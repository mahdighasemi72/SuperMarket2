package View;

import java.util.Scanner;

public class ConsoleView {
    private static Scanner scanner = new Scanner(System.in);

    public static void start(){
        while (true){
            String command = scanner.nextLine();
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
}
