import java.util.LinkedList;

public class Point{
    private LinkedList<Double> coordinates;
    private int classification;
    private double dynamicDistance;
    public Point(LinkedList<Double> coordinates, int classification){
        this.coordinates = coordinates;
        this.classification = classification;
        this.dynamicDistance = 0;
        for(double i : coordinates){
            System.out.print(i + " ");
        }
        System.out.println("Classification: " + classification);
    }

    public LinkedList<Double> getCoordinates(){
        return this.coordinates;
    }

    public double getDynamicDistance(){
        return this.dynamicDistance;
    }
    public void setDynamicDistance(double distance){
        this.dynamicDistance = distance;
    }
    public int getClassification(){
        return this.classification;
    }
}