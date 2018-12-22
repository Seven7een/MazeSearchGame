package mazegame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Explorer {

    private MazeGUI gui;

    Explorer(MazeGUI gui){

        this.gui = gui;

    }

    private int evaluateNode(Node current){

        return current.getDepth() + manhattanDist(current);

    }

    private int manhattanDist(Node current){

        int total = 0;

        total += Math.abs(current.getState().getCurrent().fst() - current.getState().getGoal().fst());
        total += Math.abs(current.getState().getCurrent().snd() - current.getState().getGoal().snd());

        return total;

    }

    Node search(){

        PriorityQueue<Node> fringe = new PriorityQueue<>(Comparator.comparingInt(this::evaluateNode));
        fringe.add(new Node(new MazeState(gui.getStart(), gui.getGoal(), gui.getSize(), gui.getPanels())));

        while(true){

          if(fringe.isEmpty()){
              return null;
          }

          Node beingChecked = fringe.poll();

          gui.getPanels()[beingChecked.getState().getCurrent().fst()][beingChecked.getState().getCurrent().snd()].setInConsideration(false);
          gui.getMainFrame().repaint();


          if(beingChecked.getState().getCurrent().eq(gui.getGoal())){
              return beingChecked;
          }

          ArrayList<Node> successors = beingChecked.generateSuccessors();

          fringe.addAll(successors);


        }

    }

}
