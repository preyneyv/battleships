class Missile {
    int x;
    int y;
    private boolean hit = false;

    Missile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void hitShip() {hit = true;}
    boolean hit() {return hit;}
}