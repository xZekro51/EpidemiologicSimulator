package Form;
import javax.swing.*;
import java.awt.event.*;

class MyWindowListener implements WindowListener {

    public void windowClosing(WindowEvent arg0) {
        System.exit(0);
    }

    public void windowOpened(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowActivated(WindowEvent arg0) {
        System.out.println(arg0.getWindow().getName());
        arg0.getWindow().setEnabled(true);
        arg0.getWindow().requestFocus();
        System.out.println(arg0.getWindow().isActive());
        System.out.println(arg0.getWindow().isEnabled());
    }
    public void windowDeactivated(WindowEvent arg0) {
        System.out.println(arg0.getWindow().isActive());
        System.out.println(arg0.getWindow().isEnabled());
        //arg0.getWindow().setEnabled(false);
    }


}
