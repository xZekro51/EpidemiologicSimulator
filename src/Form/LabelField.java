package Form;

import javax.swing.*;
import java.awt.*;
//An helper class that adds a label near a JComponent
public class LabelField extends JPanel {

    public JComponent Component;

    public LabelField(String label,JComponent component){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel(label));
        add(component);
        setMaximumSize(new Dimension(300,30));
        Component = component;
    }

    public LabelField(String label,JComponent component,Dimension dim){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel(label));
        add(component);
        setMaximumSize(new Dimension(300,30));
        component.setPreferredSize(dim);
        Component = component;
    }

    public Object Value(){
        try {
            return Component.getClass().getMethod("getValue").invoke((Object)Component);
        }
        catch(Exception e){
            return null;
        }
    }

}
