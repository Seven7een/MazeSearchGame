package mazegame;

import java.awt.*;

class MazeState {

    private Coordinate current;
    private Coordinate goal;
    private Coordinate mazeSize;
    private MPanel[][] panels;

    MazeState(Coordinate current, Coordinate goal, Coordinate mazeSize, MPanel[][] panels){

        this.current = current;
        this.goal = goal;
        this.mazeSize = mazeSize;
        this.panels = panels;

    }

    MazeState moveDown(){

        if(current.fst() < mazeSize.fst() - 1){
            if(!panels[current.fst() + 1][current.snd()].getBackground().equals(Color.black) && panels[current.fst() + 1][current.snd()].inConsideration()){
                return new MazeState(new Coordinate(current.fst() + 1, current.snd()), goal, mazeSize, panels);
            }
        }

        return this;

    }

    MazeState moveUp(){

        if(current.fst() > 0){
            if(!panels[current.fst() - 1][current.snd()].getBackground().equals(Color.black) && panels[current.fst() - 1][current.snd()].inConsideration()){
                return new MazeState(new Coordinate(current.fst() - 1, current.snd()), goal, mazeSize, panels);
            }
        }

        return this;

    }

    MazeState moveRight(){

        if(current.snd() < mazeSize.snd() - 1){
            if(!panels[current.fst()][current.snd() + 1].getBackground().equals(Color.black) && panels[current.fst()][current.snd() + 1].inConsideration()){
                return new MazeState(new Coordinate(current.fst(), current.snd() + 1), goal, mazeSize, panels);
            }
        }

        return this;

    }

    MazeState moveLeft(){

        if(current.snd() > 0){
            if(!panels[current.fst()][current.snd() - 1].getBackground().equals(Color.black) && panels[current.fst()][current.snd() - 1].inConsideration()){
                return new MazeState(new Coordinate(current.fst(), current.snd() - 1), goal, mazeSize, panels);
            }
        }

        return this;

    }

    Coordinate getCurrent() {
        return current;
    }

    Coordinate getGoal() {
        return goal;
    }


}
