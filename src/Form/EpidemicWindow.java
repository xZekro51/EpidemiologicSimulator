package Form;

import SimulationInfo.Outcomes;

import javax.swing.*;
import java.awt.*;

public class EpidemicWindow extends JFrame {

    int counter = 0;
    JLabel testLabel;
    public JLabel validLabel;

    //parameters fields
    public LabelField ResourceField;
    public LabelField PopulationField;
    public LabelField SwabCostField;
    public LabelField InfectivityField;
    public LabelField SymptomaticChanceField;
    public LabelField LethalityField;
    public LabelField DurationField;
    public LabelField VField;
    public int maxResourceValue;

    public JButton RunSingleDay;
    public JButton RunSimulation;
    public JButton ResetSimulation;

    public JProgressBar AliveBar;
    public JProgressBar ResourcesBar;
    //Current values label
    public JLabel CurrentValues = new JLabel();

    public JOptionPane SimulationEnd = new JOptionPane();

    public EpidemicWindow(){
        setName("EpidemicWindow");
        setTitle("Epidemic Simulator - Metodologie");
        setSize(600,600);
        setResizable(false);
        setVisible(true);
        addWindowListener(new MyWindowListener());
        setEnabled(true);
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        JPanel variableContainer = new JPanel();
        variableContainer.setLayout(new FlowLayout(FlowLayout.LEFT));//(new BoxLayout(variableContainer,BoxLayout.Y_AXIS));
        JPanel progressBarsContainer = new JPanel();
        progressBarsContainer.setLayout(new BoxLayout(progressBarsContainer,BoxLayout.Y_AXIS));
        progressBarsContainer.setPreferredSize(new Dimension(300,500));
        JPanel westContainer = new JPanel();
        westContainer.setLayout(new BoxLayout(westContainer,BoxLayout.Y_AXIS));
        JPanel centerContainer = new JPanel();
        //Creating the various fields
        PopulationField = new LabelField("Population: ",new JSpinner(new SpinnerNumberModel(100,0,Integer.MAX_VALUE,1)));
        SwabCostField = new LabelField("Swab Cost: ",new JSpinner(new SpinnerNumberModel(30,0,Integer.MAX_VALUE,0.1)));
        ResourceField = new LabelField("Resources: ",new JSpinner(new SpinnerNumberModel(0,0,maxResourceValue,1)),new Dimension(150,20));
        InfectivityField = new LabelField("Infectivity: ",new JSpinner(new SpinnerNumberModel(70,0,100,0.1)));
        SymptomaticChanceField = new LabelField("Symptomatic Chance: ",new JSpinner(new SpinnerNumberModel(20,0,100,0.1)));
        LethalityField = new LabelField("Lethality: ",new JSpinner(new SpinnerNumberModel(5,0,100,0.1)));
        DurationField = new LabelField("Duration: ", new JSpinner(new SpinnerNumberModel(45,0,Integer.MAX_VALUE,1)));
        VField = new LabelField("Encounter Rate (Vd): ", new JSpinner(new SpinnerNumberModel(13,0,100,0.1)));
        //Adding panels
        testLabel = new JLabel();
        validLabel = new JLabel();
        testLabel.setText("Aahah hello!");
        AliveBar = new JProgressBar(0,(int)PopulationField.Value());
        AliveBar.setStringPainted(true);
        ResourcesBar = new JProgressBar(0,(int)ResourceField.Value());
        ResourcesBar.setStringPainted(true);
        JPanel labelContainer = new JPanel();
        labelContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelContainer.add(testLabel);
        labelContainer.add(validLabel);
        container.add(labelContainer,BorderLayout.NORTH);
        container.add(centerContainer,BorderLayout.CENTER);
        JViewport viewport = new JViewport();
        centerContainer.add(viewport);
        //Variables setting
        variableContainer.setPreferredSize(new Dimension(250,30));
        variableContainer.setSize(300,30);
        variableContainer.setMaximumSize(new Dimension(270,300));
        variableContainer.add(PopulationField);
        variableContainer.add(SwabCostField);
        variableContainer.add(ResourceField);
        variableContainer.add(new JLabel("Sickness information:"));
        variableContainer.add(InfectivityField);
        variableContainer.add(SymptomaticChanceField);
        variableContainer.add(LethalityField);
        variableContainer.add(DurationField);
        variableContainer.add(VField);
        westContainer.add(variableContainer);
        container.add(westContainer,BorderLayout.WEST);
        AliveBar.setString("Alive:");
        AliveBar.setMaximumSize(new Dimension(250,24));
        ResourcesBar.setMaximumSize(new Dimension(250,24));
        progressBarsContainer.add(AliveBar);
        progressBarsContainer.add(ResourcesBar);
        container.add(progressBarsContainer,BorderLayout.EAST);
        RunSingleDay = new JButton("Run Single day");
        RunSingleDay.setPreferredSize(new Dimension(200,30));
        RunSingleDay.setMaximumSize(new Dimension(200,40));
        RunSingleDay.setAlignmentX(0.5f);
        RunSimulation = new JButton("Run Simulation.Simulation");
        RunSimulation.setPreferredSize(new Dimension(200,30));
        RunSimulation.setMaximumSize(new Dimension(200,40));
        RunSimulation.setAlignmentX(0.5f);
        ResetSimulation = new JButton("Reset Simulation.Simulation");
        ResetSimulation.setPreferredSize(new Dimension(200,30));
        ResetSimulation.setMaximumSize(new Dimension(200,40));
        ResetSimulation.setAlignmentX(0.5f);
        westContainer.add(RunSingleDay);
        westContainer.add(RunSimulation);
        westContainer.add(ResetSimulation);
        //Adding labels below to show the current values.
        JPanel curValuesPanel = new JPanel();
        curValuesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //CurrentValues.setPreferredSize(new Dimension(600,40));
        curValuesPanel.setPreferredSize(new Dimension(600,40));
        curValuesPanel.add(CurrentValues);
        container.add(curValuesPanel,BorderLayout.SOUTH);
        add(container);
    }

    public void UpdateValid(boolean isValid){
        if (!isValid)
            validLabel.setText("ATTENTION! The given input is not valid, therefore the simulation will not start. ");
        else
            validLabel.setText("The given input is valid. ");
    }

    public void UpdateBar(JProgressBar bar,int max,int val,String type){
        if (bar!=null)
        {
            bar.setMaximum(max);
            bar.setValue(val);
            bar.setString(type + String.valueOf(val));
        }
    }

    public void ShowEndSimulation(SimulationInfo.Outcomes outcome){
        if (outcome== Outcomes.ECONOMY_COLLAPSE)
            JOptionPane.showMessageDialog(SimulationEnd,"The economy collapsed.","Economy Collapse",JOptionPane.ERROR_MESSAGE);
        else if(outcome == Outcomes.ERADICATED)
            JOptionPane.showMessageDialog(SimulationEnd,"The virus was successfully eradicated.","We saved the day",JOptionPane.INFORMATION_MESSAGE);
        else if(outcome == Outcomes.POPULATION_DEAD)
            JOptionPane.showMessageDialog(SimulationEnd,"The whole population is dead.","Population is dead",JOptionPane.ERROR_MESSAGE);
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
