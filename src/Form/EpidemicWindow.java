package Form;

import javax.swing.*;
import java.awt.*;

public class EpidemicWindow extends JFrame {

    int counter = 0;
    JLabel testLabel;
    public JLabel validLabel;
    //parameters fields
    public LabelField TestField;
    public LabelField ResourceField;
    public LabelField PopulationField;
    public LabelField SwabCostField;
    public int maxResourceValue;

    //current values label
    public JLabel CurrentResources = new JLabel("Current Resources: 0");
    public JLabel CurrentPopulation = new JLabel("Current Population: 0");
    public JLabel CurrentSwabCost = new JLabel("Current Swab Cost: 0");

    public EpidemicWindow(){
        setName("EpidemicWindow");
        setTitle("Epidemic Simulator - Metodologie");
        setSize(800,600);
        setVisible(true);
        addWindowListener(new MyWindowListener());
        setEnabled(true);
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        JPanel variableContainer = new JPanel();
        variableContainer.setLayout(new FlowLayout(FlowLayout.LEFT));//(new BoxLayout(variableContainer,BoxLayout.Y_AXIS));
        PopulationField = new LabelField("Population: ",new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1)));
        SwabCostField = new LabelField("Swab Cost: ",new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,0.1)));
        ResourceField = new LabelField("Resources: ",new JSpinner(new SpinnerNumberModel(0,0,maxResourceValue,1)),new Dimension(200,20));
        //Adding panels
        testLabel = new JLabel();
        validLabel = new JLabel();
        testLabel.setText("Aahah hello!");
        JPanel labelContainer = new JPanel();
        labelContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelContainer.add(testLabel);
        labelContainer.add(validLabel);
        container.add(labelContainer,BorderLayout.NORTH);
        //Variables setting
        variableContainer.setPreferredSize(new Dimension(300,30));
        TestField = new LabelField("Test field: ",new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1)));
        //variableContainer.add(TestField);
        variableContainer.add(PopulationField);
        variableContainer.add(SwabCostField);
        variableContainer.add(ResourceField);
        container.add(variableContainer,BorderLayout.WEST);
        //Adding labels below to show the current values.
        JPanel curValuesPanel = new JPanel();
        curValuesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        curValuesPanel.add(CurrentPopulation);
        curValuesPanel.add(CurrentSwabCost);
        curValuesPanel.add(CurrentResources);
        container.add(curValuesPanel,BorderLayout.SOUTH);
        container.add(new JPanel());
        add(container);
    }

    public void UpdateValid(boolean isValid,int value){
        if (!isValid)
            validLabel.setText("ATTENTION! The given input is not valid, therefore the simulation will not start. ");
        else
            validLabel.setText("The given input is valid. ");
    }

    public void Update(){
        counter++;
        ResourceField.Value();
        JSpinner spinner = (JSpinner)ResourceField.Component;
        SpinnerNumberModel model = (SpinnerNumberModel)spinner.getModel();
        model.setMaximum(maxResourceValue);
        if (testLabel!=null)
            testLabel.setText(String.valueOf(counter));
        repaint();
    }
}
