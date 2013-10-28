/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Frank
 */
public class JMarqueeLabel extends JLabel implements ActionListener {

    private int speed;
    private Timer timer;
    private String marqLabelContent;

    public void Assign(Container container) {
        this.speed = 50;
        this.timer = new Timer(1000 / speed, this);
        this.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.setSize(640,25);
        container.add(this);
        timer.start();
    }

    public void updateLabel(String text) {
        this.marqLabelContent = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.getLocation().getX() > getRootPane().getWidth()) {
            this.setLocation(0, (int) this.getLocation().getY());
        }
        setText(marqLabelContent);
        setLocation((int) (this.getLocation().getX() +1), (int) this.getLocation().getY() );

    }
}
