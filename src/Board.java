import java.util.Arrays;

public class Board {
    private static final int BOARD_SIZE = 10;
    private static final int MAX_SHIPS = 5;
    private int shipIndex = 0;
    Ship[] ships = new Ship[MAX_SHIPS];
    MissileCollection missles;

    Board() {
        missles = new MissileCollection(this);
    }

    void addShip(Ship ship) {
        if (shipIndex == 5)
            return;
        ships[shipIndex++] = ship;
    }

    void removeLastShip() {
        ships[--shipIndex] = null;
    }

    boolean validShip(Ship checkShip) {
        int[][] checkCoords = checkShip.getCoordinates();
        boolean retVal = true;
        for (int[] checkCoord : checkCoords) {
            retVal = (checkCoord[0] < BOARD_SIZE && checkCoord[1] < BOARD_SIZE && checkCoord[0] > -1 && checkCoord[1] > -1);
            if (!retVal) break;
            for (Ship ship : ships) {
                if (ship == null) break; // last ship reached
                int[][] coords = ship.getCoordinates();
                for (int[] coord : coords) {
                    if (Arrays.equals(coord, checkCoord)) {
                        retVal = false;
                        break;
                    }
                }
            }
            if (!retVal) break;
        }
        return retVal;
    }

    boolean isHit(Missile missile) {
        boolean hit = false;
        for (Ship ship : ships) {
            hit = ship.isHit(missile);
            if (hit) {
                break;
            }
        }
        return hit;
    }

    @Override
    public String toString() {
        String alphabet = "ABCDEFGHIJ";
        StringBuilder string = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int y = 0; y < BOARD_SIZE; y++) {
            string.append(alphabet.charAt(y));
            for (int x = 0; x < BOARD_SIZE; x++) {
                string.append(" ");
                boolean flag = true;
                for (int i = 0; i < ships.length; i++) {
                    Ship ship = ships[i];
                    if (ship == null)
                        continue;
                    if (ship.checkPrint(x, y)) {
                        string.append(i + 1);
                        flag = false;
                    }
                }
                if (flag)
                    string.append("~");
            }
            string.append("\n");
        }
        return string.toString();
    }
    String enemyBoard() {
        String alphabet = "ABCDEFGHIJ";
        StringBuilder string = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int y = 0; y < BOARD_SIZE; y++) {
            string.append(alphabet.charAt(y));
            for (int x = 0; x < BOARD_SIZE; x++) {
                string.append(" ");

                boolean flag = true;

                for (Missile missile : missles.all()) {
                    if (missile.x == x && missile.y == y) {
                        flag = false;
                        string.append(missile.hit() ? 'X' : 'M');
                        break;
                    }
                }

                if (!flag)
                    continue;

                string.append("~");
            }
            string.append("\n");
        }
        return string.toString();
    }

    String playerBoard() {
        String alphabet = "ABCDEFGHIJ";
        StringBuilder string = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int y = 0; y < BOARD_SIZE; y++) {
            string.append(alphabet.charAt(y));
            for (int x = 0; x < BOARD_SIZE; x++) {
                string.append(" ");

                boolean flag = true;

                for (Missile missile : missles.all()) {
                    if (missile.x == x && missile.y == y) {
                        flag = false;
                        string.append(missile.hit() ? 'X' : 'M');
                        break;
                    }
                }

                if (flag) {
                    // print ship
                    for (Ship ship : ships) {
                        if (ship == null)
                            continue;
                        if (ship.checkPrint(x, y)) {
                            string.append("#");
                            flag = false;
                            break;
                        }
                    }
                }

                if (!flag)
                    continue;

                string.append("~");
            }
            string.append("\n");
        }
        return string.toString();
    }

}