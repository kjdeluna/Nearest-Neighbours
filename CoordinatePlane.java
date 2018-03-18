import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;

// TODO: implement max
public class CoordinatePlane extends JPanel{
    private final static int MARGIN = 30;
    private static boolean drawable = false;
    private static int max = 10;
    private LinkedList<Point> points;
    private HashMap<String, Color> classificationColorMap;
    private int count = 0;
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
            for(Point point : points){
                g2d.setColor(dynamicChangeColor(Integer.toString(point.getClassification())));
                g2d.fillOval((int) (MARGIN + point.getCoordinates().get(0) * portion), (int) (this.getHeight() - MARGIN - point.getCoordinates().get(1) * portion) - 5, 10, 10);
            }            
            g2d.setColor(Color.BLACK);
            // Drawing y-axis anchors
            for(int i = 1; i < max; i++){
                g2d.drawLine((int) MARGIN - MARGIN / 4, this.getHeight() - MARGIN - (i * portion) - 4 , MARGIN + MARGIN / 4, this.getHeight() - MARGIN - (i  * portion) - 4);
                g2d.drawString(Integer.toString(i), (int) MARGIN / 2 - 7, (int) this.getHeight() - MARGIN - (portion * i));
            }
            // Drawing x-axis anchors
            for(int i = 1; i < max; i++){
                g2d.drawLine((int) MARGIN + (i * portion) + 4, this.getHeight() - MARGIN - MARGIN / 4, (int) MARGIN + (i * portion) + 4, this.getHeight() - MARGIN + MARGIN / 4);
                g2d.drawString(Integer.toString(i), (int) (MARGIN + (portion * i)), (int) (this.getHeight() - 5) );
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