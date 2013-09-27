/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flyaway;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/**
 *
 * @author user
 */
public class FlyAwayWindowListener extends WindowAdapter {
    @Override
  public void windowClosing(WindowEvent e)
  {
    System.exit(0);
  }
}
