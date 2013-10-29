/*
 * FlyAWayView.java
 */
package flyaway;

import Controller.Controller;
import Model.Flight;
import Model.Rank;
import Model.User;
import View.AirmarshallView;
import View.AirportView;
import View.CountryView;
import View.FlightView;
import View.JMarqueeLabel;
import View.PlaneView;
import View.ScheduleView;
import View.StaffView;
import View.UserView;
import java.beans.PropertyVetoException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observer;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 * The application's main frame.
 */
public final class FlyAWayView extends FrameView implements Observer{
        private List<Flight> flights;
    
    public FlyAWayView(SingleFrameApplication app) {
        super(app);

        initComponents();
        Controller.Instance().addObserver(this);
        this.renderMarqueeText();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        this.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getFrame().addWindowListener(new FlyAwayWindowListener());
        btnSchedule.setVisible(false);

        User u = Controller.Instance().getLoggedIn();
        lblWelkom.setText("Welcome: " + u.getUsername());
        if (u.getRank() == Rank.ADMIN) {
            btnUsers.setEnabled(true);
        } else if (u.getRank() == Rank.STAFF) {
            //Als user een staff member is(voor rooster)
            JButton[] btnArray = {
                btnAirport, btnFlight, btnPlane, btnStaff, btnUsers, btnCountry, btnAirmarshall
            };
            butonVisible(btnArray, false);
            btnSchedule.setVisible(true);
        }
    }
    
    
    public void update(Observable o, Object arg) {
        if (arg instanceof Flight) {
            this.updateFlightList();

        }
    }
    
      public void updateFlightList() {
       Date today;

        try {
            DateFormat df = new SimpleDateFormat(Flight.FLIGHTDATAFORMAT);
            today = df.parse(df.format(new Date()));
        } catch (ParseException ex) {
            today = new Date();
        }

        this.flights = Controller.Instance().searchFlight(today);
    }

    
    
    
    public void renderMarqueeText() {
      this.updateFlightList();
        try {
    
            Thread marqueeThread = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    
                    
                    JMarqueeLabel marqLabel = new JMarqueeLabel();
                     marqLabel.Assign(desktopPane);
                   
                    for (;;) {
                        try {
                            Thread.sleep(150); 
                            marqLabel.updateLabel(renderFlights());
                            
                           
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FlyAWayView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }
                }
            });
            marqueeThread.start();
        }catch (Exception ex) {
            System.out.printf("%s: %s", ex.getMessage(), ex.getStackTrace());
              
        }     
}

        private  String renderFlights() {
                    String flightsString = "";
                   
                    StringBuilder sb = new StringBuilder();
                    for (Flight flight : flights) {
                        sb.append(String.format("Vlucht: %s", flight.getNumber()));
                        sb.append(String.format(" [Vliegtuig: %s", flight.getPlane().getNumber()));
                        sb.append(String.format(" - Vertrek: %s", flight.getFrom().getName()));
                        sb.append(String.format(" - Aankomst: %s", flight.getDestination().getName()));
                        sb.append(String.format(" - Piloten: %s", flight.getPilot().getName()));
                        sb.append(String.format(",  %s] ", flight.getCopilot().getName()));

                        flightsString = String.format("%s \n %s ", flightsString, sb.toString());
                    }
                       
                    if (flights.size() < 1) {
                        return "Geen vluchten vandaag.";
                    }
                    return flightsString;
                }
           

    public void addFrame(JInternalFrame frame) {
        frame.show();
        this.desktopPane.add(frame);
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FlyAWayView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void butonVisible(JButton[] btns, boolean vis) {
        for (JButton btn : btns) {
            btn.setVisible(vis);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        desktopPane = new javax.swing.JDesktopPane();
        lblWelkom = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPlane = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        btnAirport = new javax.swing.JButton();
        btnFlight = new javax.swing.JButton();
        btnUsers = new javax.swing.JButton();
        btnSchedule = new javax.swing.JButton();
        btnCountry = new javax.swing.JButton();
        btnAirmarshall = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        miLogout = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();

        mainPanel.setMaximumSize(new java.awt.Dimension(700, 550));
        mainPanel.setMinimumSize(new java.awt.Dimension(700, 550));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(700, 550));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(flyaway.FlyAWayApp.class).getContext().getResourceMap(FlyAWayView.class);
        desktopPane.setBackground(resourceMap.getColor("desktopPane.background")); // NOI18N
        desktopPane.setName("desktopPane"); // NOI18N

        lblWelkom.setText(resourceMap.getString("lblWelkom.text")); // NOI18N
        lblWelkom.setName("lblWelkom"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        btnPlane.setText(resourceMap.getString("btnPlane.text")); // NOI18N
        btnPlane.setName("btnPlane"); // NOI18N
        btnPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaneActionPerformed(evt);
            }
        });

        btnStaff.setText(resourceMap.getString("btnStaff.text")); // NOI18N
        btnStaff.setName("btnStaff"); // NOI18N
        btnStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffActionPerformed(evt);
            }
        });

        btnAirport.setText(resourceMap.getString("btnAirport.text")); // NOI18N
        btnAirport.setName("btnAirport"); // NOI18N
        btnAirport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAirportActionPerformed(evt);
            }
        });

        btnFlight.setText(resourceMap.getString("btnFlight.text")); // NOI18N
        btnFlight.setName("btnFlight"); // NOI18N
        btnFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlightActionPerformed(evt);
            }
        });

        btnUsers.setText(resourceMap.getString("btnUsers.text")); // NOI18N
        btnUsers.setEnabled(false);
        btnUsers.setName("btnUsers"); // NOI18N
        btnUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsersActionPerformed(evt);
            }
        });

        btnSchedule.setText(resourceMap.getString("btnSchedule.text")); // NOI18N
        btnSchedule.setName("btnSchedule"); // NOI18N
        btnSchedule.setOpaque(false);
        btnSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScheduleActionPerformed(evt);
            }
        });

        btnCountry.setText(resourceMap.getString("btnCountry.text")); // NOI18N
        btnCountry.setName("btnCountry"); // NOI18N
        btnCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCountryActionPerformed(evt);
            }
        });

        btnAirmarshall.setText(resourceMap.getString("btnAirmarshall.text")); // NOI18N
        btnAirmarshall.setName("btnAirmarshall"); // NOI18N
        btnAirmarshall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAirmarshallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAirport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnFlight, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnUsers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnPlane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCountry, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSchedule, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnAirmarshall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnPlane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStaff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAirport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFlight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCountry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAirmarshall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSchedule)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAirport, btnFlight, btnPlane, btnStaff});

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWelkom, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(678, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblWelkom, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        miLogout.setText(resourceMap.getString("miLogout.text")); // NOI18N
        miLogout.setName("miLogout"); // NOI18N
        miLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLogoutActionPerformed(evt);
            }
        });
        fileMenu.add(miLogout);

        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 717, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaneActionPerformed
        PlaneView pv = new PlaneView();
        addFrame(pv);
    }//GEN-LAST:event_btnPlaneActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed
        StaffView sv = new StaffView();
        addFrame(sv);
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnAirportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAirportActionPerformed
        AirportView av = new AirportView();
        addFrame(av);
    }//GEN-LAST:event_btnAirportActionPerformed

    private void btnFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlightActionPerformed
        FlightView fv = new FlightView();
        fv.setParentView(this);
        addFrame(fv);
    }//GEN-LAST:event_btnFlightActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed
        UserView uv = new UserView();
        flyaway.FlyAWayApp app = (flyaway.FlyAWayApp) flyaway.FlyAWayApp.getApplication();
        app.getFlyAwayView().addFrame(uv);
       
    }//GEN-LAST:event_btnUsersActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void btnScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScheduleActionPerformed
        ScheduleView sv = new ScheduleView();
        addFrame(sv);
    }//GEN-LAST:event_btnScheduleActionPerformed

    private void miLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLogoutActionPerformed
        Controller.Instance().Logout();
        FlyAWayApp.getApplication().startup();
        this.getFrame().dispose();
    }//GEN-LAST:event_miLogoutActionPerformed

    private void btnCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCountryActionPerformed
        CountryView cv = new CountryView();
        addFrame(cv);
    }//GEN-LAST:event_btnCountryActionPerformed

    private void btnAirmarshallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAirmarshallActionPerformed
        AirmarshallView amv = new AirmarshallView();
        addFrame(amv);
    }//GEN-LAST:event_btnAirmarshallActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAirmarshall;
    private javax.swing.JButton btnAirport;
    private javax.swing.JButton btnCountry;
    private javax.swing.JButton btnFlight;
    private javax.swing.JButton btnPlane;
    private javax.swing.JButton btnSchedule;
    private javax.swing.JButton btnStaff;
    private javax.swing.JButton btnUsers;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblWelkom;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miLogout;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

}