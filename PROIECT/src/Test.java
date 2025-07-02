import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game;

        StartPage startPage = new StartPage();

        String input = scanner.next();
        game = Game.getGameInstance();
        game.run();
        game.gameLogic();
    }
}