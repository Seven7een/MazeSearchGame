package mazegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

class MazeGUI {

    private JFrame mainFrame;
    private MPanel maze;
    private MPanel[][] panels;
    private Coordinate start;
    private Coordinate goal;
    private Coordinate size;

    MazeGUI(){

        maze = new MPanel();
        init();

    }

    private void init(){

        //general setup
        mainFrame = new JFrame("Maze Game");
        mainFrame.setSize(new Dimension(800, 800));

        Container contentPane = mainFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());


        //top options setup
        Container topOptions = new Container();
        topOptions.setLayout(new FlowLayout());

        JButton make = new JButton("Make Maze");

        JLabel mazeInfo = new JLabel("Enter dimensions: (rows x columns from top left) ");
        JTextField height = new JTextField("5", 2);
        JTextField width = new JTextField("5", 2);

        Container creationSubContainer = new Container();
        creationSubContainer.setLayout(new FlowLayout());

        creationSubContainer.add(mazeInfo);
        creationSubContainer.add(height);
        creationSubContainer.add(width);

        topOptions.add(creationSubContainer);
        creationSubContainer.setBackground(Color.darkGray);
        topOptions.add(make);


        contentPane.add(topOptions, BorderLayout.NORTH);

        // bottom options setup

        Container bottomOptions = new Container();
        bottomOptions.setLayout(new BorderLayout());

        Container bottomWest = new Container();
        bottomWest.setLayout(new FlowLayout());

        JLabel start = new JLabel("Set start: ");
        JTextField startHeight = new JTextField("0", 2);
        JTextField startWidth = new JTextField("0", 2);
        JButton setStart = new JButton("Set");
        setStart.setEnabled(false);

        Container bottomEast = new Container();
        bottomEast.setLayout(new FlowLayout());

        JLabel end = new JLabel("Set goal");
        JTextField endHeight = new JTextField("4", 2);
        JTextField endWidth = new JTextField("4", 2);
        JButton setEnd = new JButton("Set");
        setEnd.setEnabled(false);

        bottomWest.add(start);
        bottomWest.add(startHeight);
        bottomWest.add(startWidth);
        bottomWest.add(setStart);

        bottomEast.add(end);
        bottomEast.add(endHeight);
        bottomEast.add(endWidth);
        bottomEast.add(setEnd);

        bottomOptions.add(bottomWest, BorderLayout.WEST);
        bottomEast.setBackground(Color.darkGray);
        bottomOptions.add(bottomEast, BorderLayout.EAST);
        bottomWest.setBackground(Color.darkGray);

        Container bottomContainer = new Container();
        bottomContainer.setLayout(new FlowLayout());
        bottomContainer.add(bottomOptions);
        bottomOptions.setBackground(Color.darkGray);

        JButton search = new JButton("A* Search!");
        bottomContainer.add(search);
        search.setEnabled(false);

        contentPane.add(bottomContainer, BorderLayout.SOUTH);

        //mazeSetup(5, 5);

        contentPane.add(maze, BorderLayout.CENTER);
        maze.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));

        //listeners

        make.addActionListener(e -> {
            try {

                maze.setBorder(BorderFactory.createEmptyBorder());
                mazeSetup(Integer.parseInt(height.getText()), Integer.parseInt(width.getText()));
                setMazeSize(new Coordinate(Integer.parseInt(height.getText()), Integer.parseInt(width.getText())));
                setStart.setEnabled(true);

            } catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(mainFrame, "Numbers only please!");
                setStart.setEnabled(false);
            } catch (ArrayIndexOutOfBoundsException ai){
                JOptionPane.showMessageDialog(mainFrame, "Yikes! Keep it in bounds...");
                setStart.setEnabled(false);
            } catch (NegativeArraySizeException na){
                JOptionPane.showMessageDialog(mainFrame, "Uhh, just positively sized mazes, thanks.");
                setStart.setEnabled(false);
            }

        });

        setStart.addActionListener(e -> {
            try {

                panels[Integer.parseInt(startHeight.getText())][Integer.parseInt(startWidth.getText())].setBackground(Color.white);
                setStartCoord(new Coordinate(Integer.parseInt(startHeight.getText()), Integer.parseInt(startWidth.getText())));
                setEnd.setEnabled(true);

            } catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(mainFrame, "Numbers only please!");
                setEnd.setEnabled(false);
            } catch (ArrayIndexOutOfBoundsException ai){
                JOptionPane.showMessageDialog(mainFrame, "Yikes! Keep it in bounds...");
                setEnd.setEnabled(false);
            }

        });

        setEnd.addActionListener(e -> {
            try {

                panels[Integer.parseInt(endHeight.getText())][Integer.parseInt(endWidth.getText())].setBackground(Color.yellow);
                setGoalCoord(new Coordinate(Integer.parseInt(endHeight.getText()), Integer.parseInt(endWidth.getText())));
                search.setEnabled(true);

            } catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(mainFrame, "Numbers only please!");
                search.setEnabled(false);
            } catch (ArrayIndexOutOfBoundsException ai){
                JOptionPane.showMessageDialog(mainFrame, "Yikes! Keep it in bounds...");
                search.setEnabled(false);
            }

        });

        search.addActionListener(e -> {
            doSearch();
            setStart.setEnabled(false);
            setEnd.setEnabled(false);
            search.setEnabled(false);
        });

        //mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void mazeSetup(int height, int width){

        panels = new MPanel[height][width];
        maze.removeAll();

        maze.setLayout(new GridLayout(height, width, 4, 4));
        maze.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){

                panels[i][j] = new MPanel();
                panels[i][j].setBackground(Color.pink);

                panels[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        flipColor(e.getComponent());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                            e.getComponent().setBackground(Color.black);
                        }

                        if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                            e.getComponent().setBackground(Color.pink);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                            e.getComponent().setBackground(Color.black);
                        }

                        if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                            e.getComponent().setBackground(Color.pink);
                        }
                    }
                });


                maze.add(panels[i][j]);

            }
        }

        maze.revalidate();
        mainFrame.revalidate();

        maze.repaint();
        mainFrame.repaint();

    }

    private void doSearch(){

        Explorer ex = new Explorer(this);
        Node goal = ex.search();

        if(goal != null){
            JOptionPane.showMessageDialog(mainFrame, "Great! Your puzzle has a solution!");

            ArrayList<Node> path = new ArrayList<>();
            while(goal.getParent() != null){
                path.add(goal.getParent());
                goal = goal.getParent();
            }
            Collections.reverse(path);
            for(Node n : path){
                MPanel currentPanel = panels[n.getState().getCurrent().fst()][n.getState().getCurrent().snd()];
                currentPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 8));
            }

        } else {
            JOptionPane.showMessageDialog(mainFrame, "Whoops, unsolvable maze!");
        };




    }

    private void flipColor(Component jp){
        if(jp.getBackground().equals(Color.pink)){
            jp.setBackground(Color.black);
        } else {
            jp.setBackground(Color.pink);
        }

        jp.revalidate();
        jp.repaint();
    }

    MPanel[][] getPanels() { return this.panels; }

    private void setStartCoord(Coordinate start) {
        this.start = start;
    }

    private void setGoalCoord(Coordinate goal) {
        this.goal = goal;
    }

    Coordinate getStart() {
        return start;
    }

    Coordinate getGoal() {
        return goal;
    }

    private void setMazeSize(Coordinate size){
        this.size = size;
    }

    Coordinate getSize() {
        return size;
    }

    JFrame getMainFrame() {
        return mainFrame;
    }
}
