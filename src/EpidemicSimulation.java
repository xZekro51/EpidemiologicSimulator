import Form.EpidemicWindow;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class EpidemicSimulation {

    public static Population Population;
    public static Economy Economy;
    public static HealthCare HealthCare;



    public static int Resources;
    public static int PopulationSize;
    public static double SwabCost;

    public static void main(String[] args) {
        //Population = new Population();
        //Economy = new Economy(1000);
        //HealthCare = new HealthCare();

        EpidemicWindow window = new EpidemicWindow();
        while(window.isEnabled()) {
            if(window.isActive()){
                window.Update();
                window.UpdateValid(ValidInput(window),Resources);
            }

        }
    }

    public static boolean ValidInput(EpidemicWindow window){
        try {
            Resources = Integer.parseInt(window.ResourceField.Value().toString());
            PopulationSize = Integer.parseInt(window.PopulationField.Value().toString());
            SwabCost = Double.parseDouble(window.SwabCostField.Value().toString());
            window.maxResourceValue = (int)(PopulationSize * SwabCost*10)-1;
            window.CurrentPopulation.setText("Current Population: "+String.valueOf(PopulationSize) + " |");
            window.CurrentResources.setText("Current Resources: "+String.valueOf(Resources));
            window.CurrentSwabCost.setText("Current Swab Cost: "+String.valueOf(SwabCost) + " |");
            return true;
        }
        catch(Exception e){
            System.out.println("not valid input" + " " + window.ResourceField.Value().toString());
            System.out.println("not valid input" + " " + window.PopulationField.Value().toString());
            System.out.println("not valid input" + " " + window.SwabCostField.Value().toString());
            return false;
        }
    }
}
