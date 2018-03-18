import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class CoordinatePlane extends JPanel{
    public CoordinatePlane(){
        this.setLayout(null);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillOval(0, 0, 50, 50);
    }
}