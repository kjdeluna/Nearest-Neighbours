import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JViewport;
import java.util.LinkedList;

public class SolutionPanel extends JPanel{
    private JButton selectTrainingFileButton;
    private JButton classifyInputButton;
    private JTable inputClassTable;
    private JScrollPane inputClassTableScrollPane;
    private JTextArea inputTextArea;
    private JScrollPane inputTextAreaScrollPane;
    private CoordinatePlane plane;
    private JFileChooser trainingDataFileChooser;
    private NearestNeighbours nn;
    public SolutionPanel(NearestNeighbours nn){
        this.nn = nn;
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Initializing trainingDataFileChooser
        this.trainingDataFileChooser = new JFileChooser();
        this.trainingDataFileChooser.setCurrentDirectory(new File("."));
        this.trainingDataFileChooser.setDialogTitle("Open training_data File");

        // Place selectTrainingFileButton
        this.selectTrainingFileButton = new JButton("Select training.txt");
        this.selectTrainingFileButton.setFocusable(false);
        c.ipady = 10;
        c.ipadx = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weightx = 0;
        c.insets = new Insets(20, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTHWEST;
        this.add(this.selectTrainingFileButton, c);

        // Initializing and placing coordinate plane
        this.plane = new CoordinatePlane();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 800;
        c.ipadx = 800;
        c.gridwidth = 1;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 10, 10, 10);
        this.add(this.plane, c);

        // Initializating and placing inputTextArea, inputTextAreaScrollPane
        this.inputTextArea = new JTextArea();
        this.inputTextAreaScrollPane = new JScrollPane(this.inputTextArea);
        this.inputTextAreaScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.inputTextAreaScrollPane, c);

        // Initializing and placing classifyInputButton
        this.classifyInputButton = new JButton("Classify");
        this.classifyInputButton.setFocusable(false);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.weighty = 0;
        c.ipadx = 0;
        c.ipady = 15;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.add(this.classifyInputButton, c);

        // Initializing and placing inputClassTable
		String[] columnNames = {
			"Point",
			"Class" 
		};
        Object[][] rowData = new Object[0][0];
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        this.inputClassTable = new JTable(model);
        this.inputClassTableScrollPane = new JScrollPane(this.inputClassTable);
        this.inputClassTableScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.inputClassTableScrollPane, c);

        // Adding anonymous action listeners to buttons
        this.selectTrainingFileButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(trainingDataFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    System.out.println(trainingDataFileChooser.getSelectedFile());
                    nn.readTrainingFile(trainingDataFileChooser.getSelectedFile().toString());
                    putDataToInputClassTable();
                }
            }
        });

        this.classifyInputButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(trainingDataFileChooser.getSelectedFile() != null){
                    nn.readInputFile(inputTextArea.getText());
                    plane.setDrawable(true);
                    plane.setPoints(nn.getClassifiedPoints());
                    plane.repaint();
                    putDataToInputClassTable();
                }
            }
        });
    }
    private void putDataToInputClassTable(){
        DefaultTableModel tableModel = (DefaultTableModel) inputClassTable.getModel();
        tableModel.setRowCount(0);
        for(Point point : this.nn.getClassifiedPoints()){
            String[] data = new String[2];
            data[0] = "(";
            for(int i = 0; i < point.getCoordinates().size(); i++){
                data[0] += Double.toString(point.getCoordinates().get(i));
                if(i != point.getCoordinates().size() - 1){
                    data[0] += ", ";
                }
            }
            data[0] += ")";
            data[1] = Integer.toString(point.getClassification()); 
            tableModel.addRow(data);
        }
        this.inputClassTable.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    }
}
