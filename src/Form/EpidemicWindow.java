package Form;

import SimulationInfo.Outcomes;
import SimulationInfo.Strategies.NoActionGovernment;
import SimulationInfo.Strategy;

import javax.swing.*;
import java.awt.*;
import org.reflections.*;
import java.util.ArrayList;
import java.util.Set;

public class EpidemicWindow extends JFrame {

    public JLabel validLabel;

    //Parameters fields
    public LabelField ResourceField;
    public LabelField PopulationField;
    public LabelField SwabCostField;
    public LabelField InfectivityField;
    public LabelField SymptomaticChanceField;
    public LabelField LethalityField;
    public LabelField DurationField;
    public LabelField VField;
    public int maxResourceValue;

    //Buttons
    public JButton RunSingleDay;
    public JButton RunSimulation;
    public JButton ResetSimulation;
    public JComboBox ChosenStrategy;

    //Progress Bars
    public JProgressBar AliveBar;
    public JProgressBar ResourcesBar;

    //Current values label
    public JLabel CurrentValues = new JLabel();
    public JLabel CurrentSimulationValues = new JLabel();
    public JOptionPane SimulationEnd = new JOptionPane();

    public EpidemicWindow(){
        //Setting up all the basic window settings
        setName("EpidemicWindow");
        setTitle("Epidemic Simulator - Metodologie");
        setSize(600,600);
        setResizable(false);
        setVisible(true);
        addWindowListener(new MyWindowListener());
        setEnabled(true);
        //Creating containers
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        JPanel variableContainer = new JPanel();
        variableContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel progressBarsContainer = new JPanel();
        progressBarsContainer.setLayout(new BoxLayout(progressBarsContainer,BoxLayout.Y_AXIS));
        progressBarsContainer.setPreferredSize(new Dimension(300,500));
        JPanel simValuesContainer = new JPanel();
        simValuesContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        simValuesContainer.setPreferredSize(new Dimension(300,500));
        JPanel westContainer = new JPanel();
        westContainer.setLayout(new BoxLayout(westContainer,BoxLayout.Y_AXIS));
        JPanel eastContainer = new JPanel();
        eastContainer.setLayout(new BoxLayout(eastContainer,BoxLayout.Y_AXIS));
        JPanel centerContainer = new JPanel();
        JPanel labelContainer = new JPanel();
        labelContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Creating the various fields
        PopulationField = new LabelField("Population: ",new JSpinner(new SpinnerNumberModel(10000,0,Integer.MAX_VALUE,1)));
        SwabCostField = new LabelField("Swab Cost: ",new JSpinner(new SpinnerNumberModel(5,0,Integer.MAX_VALUE,0.1)));
        ResourceField = new LabelField("Resources: ",new JSpinner(new SpinnerNumberModel(maxResourceValue,0,maxResourceValue,1)),new Dimension(150,20));
        InfectivityField = new LabelField("Infectivity: ",new JSpinner(new SpinnerNumberModel(70,0,100,0.1)));
        SymptomaticChanceField = new LabelField("Symptomatic Chance: ",new JSpinner(new SpinnerNumberModel(20,0,100,0.1)));
        LethalityField = new LabelField("Lethality: ",new JSpinner(new SpinnerNumberModel(5,0,100,0.1)));
        DurationField = new LabelField("Duration: ", new JSpinner(new SpinnerNumberModel(45,0,Integer.MAX_VALUE,1)));
        VField = new LabelField("Encounter Rate (Vd): ", new JSpinner(new SpinnerNumberModel(1,0,100,0.1)));
        //Using reflection to handle the combobox dynamically
        ArrayList<String> strategies = new ArrayList<>();
        Reflections ref = new Reflections("SimulationInfo.Strategies");
        Set<Class<? extends Strategy>> stratClasses = ref.getSubTypesOf(Strategy.class);//Strategy.class.getClasses();
        for (Class c:stratClasses){
            strategies.add(c.getName().replace("SimulationInfo.Strategies.",""));
        }
        ChosenStrategy = new JComboBox(strategies.toArray());
        ChosenStrategy.setMaximumSize(new Dimension(200,40));
        //Adding panels
        validLabel = new JLabel();
        AliveBar = new JProgressBar(0,(int)PopulationField.Value());
        AliveBar.setStringPainted(true);
        ResourcesBar = new JProgressBar(0,(int)ResourceField.Value());
        ResourcesBar.setStringPainted(true);
        labelContainer.add(validLabel);
        //Setting up the containers
        container.add(labelContainer,BorderLayout.NORTH);
        container.add(centerContainer,BorderLayout.CENTER);
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
        simValuesContainer.add(CurrentSimulationValues);
        eastContainer.add(progressBarsContainer);
        eastContainer.add(simValuesContainer);
        container.add(eastContainer,BorderLayout.EAST);
        RunSingleDay = new JButton("Run Single day");
        RunSingleDay.setPreferredSize(new Dimension(200,30));
        RunSingleDay.setMaximumSize(new Dimension(200,40));
        RunSingleDay.setAlignmentX(0.5f);
        RunSimulation = new JButton("Run Simulation");
        RunSimulation.setPreferredSize(new Dimension(200,30));
        RunSimulation.setMaximumSize(new Dimension(200,40));
        RunSimulation.setAlignmentX(0.5f);
        ResetSimulation = new JButton("Reset Simulation");
        ResetSimulation.setPreferredSize(new Dimension(200,30));
        ResetSimulation.setMaximumSize(new Dimension(200,40));
        ResetSimulation.setAlignmentX(0.5f);
        westContainer.add(RunSingleDay);
        westContainer.add(RunSimulation);
        westContainer.add(ResetSimulation);
        westContainer.add(ChosenStrategy);
        //Adding labels below to show the current values.
        JPanel curValuesPanel = new JPanel();
        curValuesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        curValuesPanel.setPreferredSize(new Dimension(600,40));
        curValuesPanel.add(CurrentValues);
        container.add(curValuesPanel,BorderLayout.SOUTH);
        add(container);
    }

    public void UpdateValid(boolean isValid){
        //Checking what text to show for the given input
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

    public void UpdateSimulationValues(String values){
        if(CurrentSimulationValues!=null)
            CurrentSimulationValues.setText(values);
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
        ResourceField.Value();
        JSpinner spinner = (JSpinner)ResourceField.Component;
        SpinnerNumberModel model = (SpinnerNumberModel)spinner.getModel();
        model.setMaximum(maxResourceValue);
        repaint();
    }
}
