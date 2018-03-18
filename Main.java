import java.awt.Dimension;
import javax.swing.JFrame;

public class Main{
    public static final int WIDTH = 1020;
    public static final int HEIGHT = 720;
    private static final String TITLE = "Nearest Neighbours";
    public static void main(String[] args){
        NearestNeighbours nn = new NearestNeighbours();
        JFrame frame = new JFrame(TITLE);
        // frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instantiate SolutionPanel here
        SolutionPanel sp = new SolutionPanel(nn);
        // frame.setContentPane(sp);
        frame.add(sp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } 
}