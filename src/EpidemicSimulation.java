import Form.EpidemicWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class EpidemicSimulation {

    public static int Resources;
    public static int PopulationSize;
    public static double SwabCost;
    public static double Infectivity;
    public static double SymptChance;
    public static double Lethality;
    public static int Duration;
    public static float V;

    public static void main(String[] args) {

        EpidemicWindow window = new EpidemicWindow();
        Simulation sim = new Simulation();
        window.RunSingleDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.RunOneDaySimulation();
            }
        });
        Timer simulation = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim.RunOneDaySimulation();
                if(sim.Economy.Resources<=0){
                    window.ShowEndSimulation("The economy collapsed.");
                    ((Timer)e.getSource()).stop();
                    sim.Running=false;
                }
            }
        });
        window.RunSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!sim.Running) {
                    sim.Running = true;
                    //Setting the text to the opposite needed
                    window.RunSimulation.setText("Stop Simulation");
                    simulation.start();
                }
                else {
                    sim.Running = false;
                    //Setting the text to the opposite needed
                    window.RunSimulation.setText("Run Simulation");
                    simulation.stop();
                }
            }
        });
        window.ResetSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sim.Running) {
                    sim.Running = false;
                    window.RunSimulation.setText("Run Simulation");
                    simulation.stop();
                }
                sim.ResetSimulation();
            }
        });
        Timer update = new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowUpdate(window,sim);
            }
        });
        update.start();
    }

    public static void WindowUpdate(EpidemicWindow window, Simulation sim){
        if (window.isEnabled()) {
            if (window.isActive()) {
                window.Update();
                window.UpdateBar(window.AliveBar,sim.PopSize,sim.alive(),"Alive: ");
                window.UpdateBar(window.ResourcesBar,(int)sim.Resources,(int)sim.currentResources(),"Resources: ");
                if(sim!=null && sim.Running) {
                    //window.UpdateSimulationValues(String.format("<html>Red: %d<br/>Yellow: %d<br/></html>", 3, sim.Population.asymptomatic()));
                }
                window.UpdateValid(ValidInput(window));
                UpdateSimulation(sim);
                sim.UpdateSimulationValues();
            }
        }
    }

    public static boolean ValidInput(EpidemicWindow window){
        try {
            Resources = Integer.parseInt(window.ResourceField.Value().toString());
            PopulationSize = Integer.parseInt(window.PopulationField.Value().toString());
            SwabCost = Float.parseFloat(window.SwabCostField.Value().toString());
            window.maxResourceValue = (int)(PopulationSize * SwabCost * 10);
            Infectivity = Double.parseDouble(window.InfectivityField.Value().toString());
            SymptChance = Double.parseDouble(window.SymptomaticChanceField.Value().toString());
            Lethality = Double.parseDouble(window.LethalityField.Value().toString());
            Duration = Integer.parseInt(window.DurationField.Value().toString());
            V = Float.parseFloat(window.VField.Value().toString());

            String population = "Current Population: "+String.valueOf(PopulationSize) + " | ";
            String resources = "Current Resources: "+String.valueOf(Resources) + " | ";
            String swabcost = "Current Swab Cost: "+String.valueOf(SwabCost) + " | ";
            String infectivity = "Infectivity: "+String.valueOf(Infectivity) + " | ";
            String symptChance = "Symptoms chance: "+String.valueOf(SymptChance) + " | ";
            String lethality = "Lethality: "+String.valueOf(Lethality) + " | ";
            String duration = "Duration: "+String.valueOf(Duration) + " | ";
            String vd = "V: "+ String.valueOf(V) + " | ";
            String fullString = "<html>"+population + swabcost + resources + "<br/>" + infectivity + symptChance + lethality + duration + vd +"</html>";

            window.CurrentValues.setText(fullString);
            return true;
        }
        catch(Exception e){
            System.out.println("not valid input" + " " + window.ResourceField.Value().toString());
            System.out.println("not valid input" + " " + window.PopulationField.Value().toString());
            System.out.println("not valid input" + " " + window.SwabCostField.Value().toString());
            return false;
        }
    }

    public static void UpdateSimulation(Simulation sim){
        sim.PopSize = PopulationSize;
        sim.Resources = Resources;
        sim.Duration = Duration;
        sim.SwabCost = SwabCost;
        sim.Lethality = Lethality;
        sim.Infectivity = Infectivity;
        sim.SymptChance = SymptChance;
        sim.V = V;
    }
}
