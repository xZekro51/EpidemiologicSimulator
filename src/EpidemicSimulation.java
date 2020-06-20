import Form.EpidemicWindow;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class EpidemicSimulation {

    public static Population Population;
    public static Economy Economy;
    public static HealthCare HealthCare;

    public static void main(String[] args) {
        //Population = new Population();
        //Economy = new Economy(1000);
        //HealthCare = new HealthCare();

        EpidemicWindow window = new EpidemicWindow();
        while(window.isActive()) {
            window.Update();
        }
    }
}
