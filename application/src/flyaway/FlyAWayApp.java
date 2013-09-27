/*
 * FlyAWayApp.java
 */

package flyaway;

import View.LoginView;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class FlyAWayApp extends SingleFrameApplication {

    private FlyAWayView flyAwayView = null;
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        LoginView lv = new LoginView();
        show(lv);       
    }

    public void showMainApp(){
        flyAwayView = new FlyAWayView(this);        
        show(flyAwayView);        
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of FlyAWayApp
     */
    public static FlyAWayApp getApplication() {
        return Application.getInstance(FlyAWayApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(FlyAWayApp.class, args);
    }

    public FlyAWayView getFlyAwayView() {
        return flyAwayView;
    }

    

    
}
