import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
// TODO: implement max
//      : convert to switch case
public class CoordinatePlane extends JPanel{
    private final static int MARGIN = 30;
    private static boolean drawable = false;
    private static int maxX = 0; // 0 degree
    private static int maxY = 0; // 90 degrees
    private static int minX = 0;
    private static int minY = 0;
    private LinkedList<Point> points;
    private HashMap<String, Color> classificationColorMap;
    private int count = 0;
    private int max = 30;
    private static Color[] colors = {
        Color.RED,
        Color.BLUE,
        Color.MAGENTA,
        Color.ORANGE,
        Color.CYAN,
        Color.GREEN,
        Color.YELLOW,
        Color.PINK
    };

    public CoordinatePlane(){
        this.classificationColorMap = new HashMap<String, Color>();
        this.setLayout(null);
    }
    public void setMinMax(){
        LinkedList<Double> x = new LinkedList<Double>();
        LinkedList<Double> y = new LinkedList<Double>();
        for(Point point : points){
            LinkedList<Double> coordinates = point.getCoordinates();
            x.add(coordinates.get(0));
            y.add(coordinates.get(1));
        }
        if(x.size() != 0){
            maxX = (int) Math.ceil((double) Collections.max(x));
            minX = (int) Math.ceil((double) Collections.min(x));
        }
        if(y.size() != 0){
            maxY = (int) Math.ceil((double) Collections.max(y));
            minY = (int) Math.ceil((double) Collections.min(y));
        }
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
        int drawingComponentWidth = this.getWidth();
        int drawingComponentHeight = this.getHeight();
        // Drawing Cartesian Plane
        g2d.drawLine(this.getWidth() / 2, 0,  this.getWidth()/2, this.getHeight());
        g2d.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2 );
        if(drawable){
            setMinMax();
            int xPortion = 0, yPortion = 0;
            int x = (maxX > Math.abs(minX)) ? maxX : Math.abs(minX);
            xPortion = (int) (drawingComponentWidth / 2) / x;
            int y = (maxY > Math.abs(minY)) ? maxY : Math.abs(minY);
            yPortion = (int) (drawingComponentHeight / 2) / y;
            for(Point point : points){
                g2d.setColor(dynamicChangeColor(Integer.toString(point.getClassification())));
                g2d.fillOval((int) ((point.getCoordinates().get(0) * xPortion) + this.getWidth()/2) - 2, (int) (this.getHeight() / 2 - point.getCoordinates().get(1) * yPortion), 5, 5);
            }
            g2d.setColor(Color.BLACK);
            // Drawing y-axis anchors
            for(int i = y * 2; i > -(y * 2); i--){
                if(i == 0) continue;
                g2d.drawLine((int) this.getWidth() / 2 - MARGIN / 4, (i * yPortion) , MARGIN / 4 + this.getWidth() / 2, i  * yPortion);
                g2d.drawString(Integer.toString(i), (int) this.getWidth() / 2 + 7, (int) yPortion * (y - i));
            }
            // // Drawing x-axis anchors
            for(int i = x * 2; i > -(x * 2); i--){
                if(i == 0) continue;
                g2d.drawLine((int) (i * xPortion) + 1, this.getHeight() / 2 - MARGIN/4, (int) (i * xPortion) + 1, this.getHeight() / 2 + MARGIN/4);
                g2d.drawString(Integer.toString(-i), (int) xPortion * (x - i), (int) this.getHeight() / 2 + MARGIN);
            }
        }
    }

    private Color dynamicChangeColor(String classification){
        if(!classificationColorMap.containsKey(classification)){
            classificationColorMap.put(classification,colors[count++]);
            return colors[count-1];
        } else {
            return classificationColorMap.get(classification);
        }
    }
}