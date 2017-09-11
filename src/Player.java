class Player {
    static Board board = new Board();
    static void initialize() {
        int shipCounter = 0;
        while (shipCounter <= 5) {
            Game.clrscr();

            System.out.println("BOARD SETUP");
            System.out.println(board);
            Ship ship;
            if (shipCounter > 0)
                System.out.println("Enter \"00\" to go back and change your mind.");
            else
                System.out.println("Enter \"00\" to exit");

            if (shipCounter < 5) {
                System.out.print("Enter coordinates (ex: 5F) of your ");
                switch (shipCounter) {
                    case 0:
                        System.out.print("carrier(5): ");
                        break;
                    case 1:
                        System.out.print("battleship(4): ");
                        break;
                    case 2:
                        System.out.print("cruiser(3): ");
                        break;
                    case 3:
                        System.out.print("submarine(3): ");
                        break;
                    case 4:
                        System.out.print("destroyer(2): ");
                        break;
                }
                String line = Game.readLine();
                if (line.equals("00")) {
                    if (shipCounter == 0) System.exit(0);
                    // go back
                    shipCounter--;
                    board.removeLastShip();
                    continue;
                }
                int posX, posY, direction;
                try {
                    char[] lineChars = line.toCharArray();

                    posX = Character.getNumericValue(lineChars[0]) - 1;
                    posY = Game.alphabet.indexOf(String.valueOf(lineChars[1]));

                    if (line.length() == 3) {
                        posY = Game.alphabet.indexOf(String.valueOf(lineChars[2]));
                        posX = 9;
                    }

                    System.out.print("Enter its direction (0 for horizontal, 1 for vertical): ");
                    direction = Integer.parseInt(Game.readLine());
                } catch (Exception e) {
                    System.out.println("Invalid values entered!");
                    Game.waitForEnter("Press enter to try again.");
                    continue;
                }
                switch (shipCounter) {
                    case 0:
                        ship = new Carrier(posX, posY, direction);
                        break;
                    case 1:
                        ship = new Battleship(posX, posY, direction);
                        break;
                    case 2:
                        ship = new Cruiser(posX, posY, direction);
                        break;
                    case 3:
                        ship = new Submarine(posX, posY, direction);
                        break;
                    default:
                        ship = new Destroyer(posX, posY, direction);
                        break;
                }
                if (board.validShip(ship))
                    board.addShip(ship);
                else {
                    System.out.println("This ship is in an invalid location!");
                    System.out.print("Press enter to try again.");
                    Game.readLine();
                    continue;
                }
            } else {
                System.out.println("Or, press enter to continue.");
                String line = Game.readLine("Input: ");
                if (line.equals("00")) {
                    shipCounter--;
                    board.removeLastShip();
                    continue;
                }
            }
            shipCounter++;
        }
    }
    static void nextMove() {
        Game.clrscr();

        System.out.println(Game.combineBoards(Player.board, Bot.board));

        System.out.print("Enter coordinates to fire your missile: ");
        char[] line = Game.readLine().toCharArray();
        boolean hasCoords = false;
        int x = 0;
        int y = 0;
        while (!hasCoords) {
            try {
                x = Character.getNumericValue(line[0]) - 1;
                y = Game.alphabet.indexOf(String.valueOf(line[1]));
                if (line.length == 3) {
                    y = Game.alphabet.indexOf(String.valueOf(line[2]));
                    if (line[0] != '1' || line[1] != '0')
                        throw new Exception();
                    x = 9;
                }
                if (y > 9 || y < 0 || x > 9 || x < 0)
                    throw new Exception();
                if (Bot.board.missles.contains(x, y))
                    throw new Exception();
                hasCoords = true;
            } catch (Exception e) {
                System.out.println("Invalid coordinates! Try again.");
                System.out.print("Enter coordinates to fire your missile: ");
                line = Game.readLine().toCharArray();
            }
        }
        Missile missile = new Missile(x, y);
        Game.clrscr();
        Bot.board.missles.addMissile(missile);
        System.out.println(Game.combineBoards(Player.board, Bot.board));

        if (missile.hit())
            System.out.println("HIT!");
        else
            System.out.println("MISS!");
        Game.waitForEnter();
    }
}
