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
    public LabelField InfectivityField;
    public LabelField SymptomaticChanceField;
    public LabelField LethalityField;
    public LabelField DurationField;
    public int maxResourceValue;

    //current values label
    public JLabel CurrentValues = new JLabel();
    //public JLabel CurrentResources = new JLabel("Current Resources: 0");
    //public JLabel CurrentPopulation = new JLabel("Current Population: 0");
    //public JLabel CurrentSwabCost = new JLabel("Current Swab Cost: 0");

    public EpidemicWindow(){
        setName("EpidemicWindow");
        setTitle("Epidemic Simulator - Metodologie");
        setSize(900,600);
        setVisible(true);
        addWindowListener(new MyWindowListener());
        setEnabled(true);
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        JPanel variableContainer = new JPanel();
        variableContainer.setLayout(new FlowLayout(FlowLayout.LEFT));//(new BoxLayout(variableContainer,BoxLayout.Y_AXIS));
        //Creating the various fields
        PopulationField = new LabelField("Population: ",new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1)));
        SwabCostField = new LabelField("Swab Cost: ",new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,0.1)));
        ResourceField = new LabelField("Resources: ",new JSpinner(new SpinnerNumberModel(0,0,maxResourceValue,1)),new Dimension(150,20));
        InfectivityField = new LabelField("Infectivity: ",new JSpinner(new SpinnerNumberModel(0,0,100,0.1)));
        SymptomaticChanceField = new LabelField("Symptomatic Chance: ",new JSpinner(new SpinnerNumberModel(0,0,100,0.1)));
        LethalityField = new LabelField("Lethality: ",new JSpinner(new SpinnerNumberModel(0,0,100,0.1)));
        DurationField = new LabelField("Duration: ", new JSpinner(new SpinnerNumberModel(0,0,100,1)));
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
        variableContainer.setBackground(Color.CYAN);
        variableContainer.setPreferredSize(new Dimension(250,30));
        variableContainer.setSize(300,30);
        variableContainer.setMaximumSize(new Dimension(300,200));
        //variableContainer.add(TestField);
        variableContainer.add(PopulationField);
        variableContainer.add(SwabCostField);
        variableContainer.add(ResourceField);
        variableContainer.add(new JLabel("Sickness information:"));
        variableContainer.add(InfectivityField);
        variableContainer.add(SymptomaticChanceField);
        variableContainer.add(LethalityField);
        variableContainer.add(DurationField);
        container.add(new JPanel());
        container.add(variableContainer,BorderLayout.WEST);
        //Adding labels below to show the current values.
        JPanel curValuesPanel = new JPanel();
        curValuesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        curValuesPanel.add(CurrentValues);
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
