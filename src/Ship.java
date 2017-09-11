abstract class Ship {
    private int size;
    private int posX;
    private int posY;
    private int direction;
    private int numHit;

    Ship(int size_, int posX_, int posY_, int direction_) {
        size = size_;
        posX = posX_;
        posY = posY_;
        direction = direction_;
        numHit = 0;
    }

    int[][] getCoordinates() {
        int[][] coords = new int[size][2];
        for (int i = 0; i < size; i++) {
            if (direction == 0)
                coords[i] = new int[] {posX+i,posY};
            else
                coords[i] = new int[] {posX,posY+i};
        }
        return coords;
    }

    boolean isHit(Missile missile) {
        int x = missile.x;
        int y = missile.y;
        boolean hit;
        if (direction == 1) {
            // vertical
            hit = x == posX && y < posY + size && y >= posY;
        } else {
            // horizontal
            hit = y == posY && x < posX + size && x >= posX;
        }
        if (hit) numHit++;
        return hit;
    }
    boolean checkPrint(int x, int y) {
        if (direction == 1) {
            // vertical
            return x == posX && y < posY + size && y >= posY;
        } else {
            // horizontal
            return y == posY && x < posX + size && x >= posX;
        }
    }
    boolean isSunk() {
        return numHit == size;
    }
}