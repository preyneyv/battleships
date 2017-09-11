import java.util.ArrayList;

class MissileCollection {
    private ArrayList<Missile> missiles = new ArrayList<>();
    private Board board;

    MissileCollection(Board board) {
        this.board = board;
    }

    ArrayList<Missile> all() {
        return missiles;
    }

    void addMissile(Missile missile) {
        if (board.isHit(missile)) missile.hitShip();
        missiles.add(missile);
    }
    boolean contains(int x, int y) {
        boolean flag = false;
        for (Missile missile : missiles) {
            if (x == missile.x && y == missile.y) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}