package mazegame;

import java.util.ArrayList;

class Node {

    private Node parent;
    private MazeState state;
    private int depth;

    Node(MazeState initial) {
        parent = null;
        this.state = initial;
        depth = 0;
    }

    private Node(Node parent, MazeState state) {
        this.parent = parent;
        this.state = state;
        depth = parent.getDepth() + 1;
    }

    ArrayList<Node> generateSuccessors() {

        ArrayList<Node> returnList = new ArrayList<>();

        MazeState down = state.moveDown();
        MazeState up = state.moveUp();
        MazeState right = state.moveRight();
        MazeState left = state.moveLeft();

        if (!down.equals(this.state)) returnList.add(new Node(this, down));

        if (!up.equals(this.state)) returnList.add(new Node(this, up));

        if (!right.equals(this.state)) returnList.add(new Node(this, right));

        if (!left.equals(this.state)) returnList.add(new Node(this, left));


        return returnList;

    }

    MazeState getState() {
        return state;
    }

    Node getParent() {
        return parent;
    }

    int getDepth() {
        return depth;
    }
}