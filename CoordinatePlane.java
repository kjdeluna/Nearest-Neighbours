import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class CoordinatePlane extends JPanel{
    private final static int MARGIN = 30;
    private static boolean drawable = false;
    private static int max = 10;
    private LinkedList<Point> points;

    public CoordinatePlane(){
        this.setLayout(null);
    }
    public void setDrawable(boolean value){
        drawable = true;
    }
    public void setPoints(LinkedList<Point> points){
        this.points = points;
    }
    public void setMax(int max){
        this.max = max;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int drawingComponentWidth = this.getWidth() - (MARGIN * 2);
        // Drawing Cartesian Plane
        g2d.drawLine(MARGIN, MARGIN, MARGIN, this.getHeight() - MARGIN);
        g2d.drawLine(MARGIN, this.getHeight() - MARGIN, this.getWidth() - MARGIN, this.getHeight() - MARGIN);
        if(drawable){
            int portion = (int) drawingComponentWidth / max;
            System.out.println(portion);
            for(Point point : points){
                g2d.fillOval((int) (MARGIN + point.getCoordinates().get(0) * portion), (int) (this.getHeight() - MARGIN - point.getCoordinates().get(1) * portion), 5, 5);
            }            
        }
    }
}