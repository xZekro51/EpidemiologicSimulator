package Form;

import javax.swing.*;
import java.awt.*;

public class EpidemicWindow extends JFrame {

    int counter = 3;
    JLabel testLabel;

    public EpidemicWindow(){
        setTitle("Epidemic Simulator - Metodologie");
        setSize(800,600);
        setVisible(true);
        addWindowListener(new MyWindowListener());
        setEnabled(true);
        //Adding panels
        testLabel = new JLabel();
        testLabel.setText("Aahah hello!");
        add(testLabel);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        counter++;
        if (testLabel!=null)
            testLabel.setText(String.valueOf(counter));
    }

    public void Update(){
        counter++;
        if (testLabel!=null)
            testLabel.setText(String.valueOf(counter));
    }
}
