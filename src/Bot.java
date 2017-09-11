import java.util.ArrayList;

class Bot {
    static Board board = new Board();
    static void initialize() {
        Ship carrier = new Carrier(random(10),random(10), random(2));
        while (!board.validShip(carrier))
            carrier = new Carrier(random(10),random(10), random(2));

        Ship cruiser = new Cruiser(random(10),random(10), random(2));
        while (!board.validShip(cruiser))
            cruiser = new Cruiser(random(10),random(10), random(2));

        Ship destroyer = new Destroyer(random(10),random(10), random(2));
        while (!board.validShip(destroyer))
            destroyer = new Destroyer(random(10),random(10), random(2));

        Ship battleship = new Battleship(random(10),random(10), random(2));
        while (!board.validShip(battleship))
            battleship = new Battleship(random(10),random(10), random(2));

        Ship submarine = new Submarine(random(10),random(10), random(2));
        while (!board.validShip(submarine))
            submarine = new Submarine(random(10),random(10), random(2));

        board.addShip(carrier);
        board.addShip(cruiser);
        board.addShip(destroyer);
        board.addShip(battleship);
        board.addShip(submarine);
    }
    static void nextMove() {
        int x = random(10);
        int y = random(10);

        while (Player.board.missles.contains(x, y)) {
            x = random(10);
            y = random(10);
        }
        Missile missile = new Missile(x, y);
        Player.board.missles.addMissile(missile);
        System.out.println(Game.combineBoards(Player.board, Bot.board));

        if (missile.hit())
            System.out.println("Bot HIT your ship!");
        else
            System.out.println("Bot MISSED!");
        Game.waitForEnter();
    }
    private static int random(int max) {
        return (int) Math.floor(Math.random() * max);

    }
}
