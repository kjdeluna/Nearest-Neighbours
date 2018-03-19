import java.lang.Math;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
public class NearestNeighbours{
    private int k;
    private LinkedList<Point> points;
    private static final String INPUT_FILENAME = "input.txt";
    private static final String OUTPUT_FILENAME = "output.txt";
	public static Comparator<Point> distanceComparator = new Comparator<Point>(){	
		@Override
		public int compare(Point p1, Point p2) {
            if(p1.getDynamicDistance() < p2.getDynamicDistance()) return -1;
            else if(p1.getDynamicDistance() > p2.getDynamicDistance()) return 1;
            else return 0;
        }
	};

    public NearestNeighbours(){
        this.points = new LinkedList<Point>();
    }

    public void readInputFile(String allText){
        try{
            FileWriter output = new FileWriter(OUTPUT_FILENAME, true);
            try{
                String[] lines = allText.split("\n");
                String[] lineRead;
                String line;
                // FileReader fr = new FileReader(INPUT_FILENAME);
                // BufferedReader br = new BufferedReader(fr);
                LinkedList<Double> pointCollector;
                int i = 0;
                for(int k = 0; k < lines.length; k++){
                    lineRead = lines[k].split(" ");
                    pointCollector =  new LinkedList<Double>();
                    for(i = 0; i < lineRead.length; i++){
                        pointCollector.add(Double.parseDouble(lineRead[i]));
                    }
                    Point input = new Point(pointCollector, -1);
                    for(Point point : this.points){
                        computeEuclideanDistance(point, input);
                    }
                    Collections.sort(this.points, distanceComparator);
                    // Create hashmap to count the frequency:
                    // for(i = 0; i < this.k; i++){
                    //     if(!classificationCounter.containsKey())
                    // }
                    output.write("Nearest neighbours of (");
                    for(i = 0; i < input.getCoordinates().size(); i++){
                        output.write(Double.toString(input.getCoordinates().get(i)));
                        if(i != input.getCoordinates().size() - 1){
                            output.write(", ");
                        }
                    }
                    output.write("): ");
                    int x = 0;
                    int max = 0;
                    HashMap<String, Integer> classificationCounter = new HashMap<String, Integer>();
                    for(i = 0; i < this.k; i++){
                        output.write("(");
                        if(classificationCounter.containsKey(Integer.toString(this.points.get(i).getClassification()))){
                            classificationCounter.put(Integer.toString(this.points.get(i).getClassification()), classificationCounter.get((Integer.toString(this.points.get(i).getClassification()))) + 1);
                            if(classificationCounter.get(Integer.toString(this.points.get(i).getClassification())) > max){
                                max = classificationCounter.get(Integer.toString(this.points.get(i).getClassification()));
                            }
                        }
                        else {
                            classificationCounter.put(Integer.toString(this.points.get(i).getClassification()), 1);
                        }
                        // Prints all the coordinates of the point
                        for(int j = 0; j < this.points.get(i).getCoordinates().size(); j++){
                            output.write(Double.toString(this.points.get(i).getCoordinates().get(j)));
                            if(j != this.points.get(i).getCoordinates().size() - 1){
                                output.write(", ");
                            }
                        }
                        output.write(") ");
                    }
                    output.write("\n");
                    output.write("Class of (");
                    for(i = 0; i < input.getCoordinates().size(); i++){
                        output.write(Double.toString(input.getCoordinates().get(i)));
                        if(i != input.getCoordinates().size() - 1){
                            output.write(", ");
                        }
                    }
                    output.write("): ");
                    // Print the classification
                    for(String dummy : classificationCounter.keySet()){
                        if(classificationCounter.get(dummy) == max){
                            this.points.add(new Point(pointCollector, Integer.parseInt(dummy)));
                            output.write(dummy + "\n");
                            break;
                        }
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            output.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void clearPoints(){
        this.points.clear();
    }
    public void readTrainingFile(String trainingFilename){
        try{
            String[] lineRead;
            String line;
            FileReader fr = new FileReader(trainingFilename);
            BufferedReader br = new BufferedReader(fr);
            LinkedList<Double> pointCollector;
            int i = 0;
            while((line = br.readLine()) != null){
                pointCollector =  new LinkedList<Double>();
                lineRead = line.split(" ");
                if(i == 0){
                    k = Integer.parseInt(lineRead[0]);
                    i++; // Dummy increment
                } else {
                    for(i = 0; i < lineRead.length - 1; i++){
                        pointCollector.add(Double.parseDouble(lineRead[i]));
                    }
                    this.points.add(new Point(pointCollector, Integer.parseInt(lineRead[i])));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private double computeEuclideanDistance(Point x, Point y){
        double result = 0;
        LinkedList<Double> xCoordinates = x.getCoordinates();
        LinkedList<Double> yCoordinates = y.getCoordinates();
        // Compare two points together
            // Will only compute the euclidean distance if both of the points' dimensions are correct
        if(xCoordinates.size() == yCoordinates.size()){
            for(int i = 0 ; i < xCoordinates.size(); i++){
                result += Math.pow((xCoordinates.get(i) - yCoordinates.get(i)), 2);
            }
            x.setDynamicDistance(Math.sqrt(result));
            return Math.sqrt(result);
        }
        return result;
    }

    public LinkedList<Point> getClassifiedPoints(){
        return this.points;
    }
}