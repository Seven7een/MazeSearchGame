package mazegame;

public class Coordinate {

    private int x;
    private int y;

    Coordinate(int x, int y){

        this.x = x;
        this.y = y;

    }

    int fst() {
        return x;
    }

    int snd(){
        return y;
    }

    public void setFst(int x){
        this.x = x;
    }

    public void setSnd(int y){
        this.y = y;
    }

    boolean eq(Coordinate that){

        return this.fst() == that.fst() && this.snd() == that.snd();

    }
}
