import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class Game {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String alphabet = "ABCDEFGHIJ";
    private static String winner;
    static String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    static String readLine(String prompt) {
        System.out.print(prompt);
        return readLine();
    }
    public static void main(String[] args) {

        clrscr();
        System.out.println("Hi!");
        System.out.println("Welcome to BATTLESHIPS!");
        waitForEnter("Press enter to start.");

        Player.initialize();
        Bot.initialize();

        while (!gameOver()) {
            Player.nextMove();
            clrscr();
            if (gameOver()) {
                break;
            }
            Bot.nextMove();
            clrscr();
        }
        System.out.println(combineBoards(Player.board, Bot.board));
        System.out.printf("%s won!\n\n", winner);
        waitForEnter();
        clrscr();
        System.out.println("THANKS FOR\nPLAYING!\n\n");
        System.out.println("By Pranav Nutalapati");
        waitForEnter("Press enter to exit the game.");
        clrscr();
        System.exit(0);
    }
    private static boolean gameOver() {
        // check for game over

        boolean gameWon = true;
        // check if player won
        for (Ship ship : Bot.board.ships) {
            if (!ship.isSunk()) {
                gameWon = false;
                break;
            }
        }
        if (gameWon) {
            winner = "Player";
            return true;
        }

        // check if bot won
        gameWon = true;
        for (Ship ship : Player.board.ships) {
            if (!ship.isSunk()) {
                gameWon = false;
                break;
            }
        }
        if (gameWon) {
            winner = "Bot";
            return true;
        }
        return false;
    }

    static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033\143");
        } catch (IOException | InterruptedException ex) {ex.printStackTrace();}
    }

    static void waitForEnter(String message) {
        System.out.println(message);
        readLine();
    }
    static void waitForEnter() {
        System.out.println("Press enter to continue.");
        readLine();
    }
    static String combineBoards(Board board1, Board board2) {
        String[] board1Lines = board1.playerBoard().split("\n");
        String[] board2Lines = board2.enemyBoard().split("\n");
        String[] merged = new String[board1Lines.length + 1];
        merged[0] = "        PLAYER                          BOT         ";
        merged[1] = board1Lines[0] + "       " + board2Lines[0];
        for (int i = 1; i < board1Lines.length; i++) {
            merged[i+1] = board1Lines[i] + "        " + board2Lines[i];
        }
        return String.join("\n", merged) + "\n";
    }
}