/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ScheduleView.java
 *
 * Created on May 23, 2013, 11:41:18 PM
 */
package View;

import Util.GenericTableModel;
import Controller.Controller;
import Model.*;
import java.util.*;

/**
 *
 * @author justin
 */
public class ScheduleView extends javax.swing.JInternalFrame implements Observer {

    /** Creates new form ScheduleView */
    public ScheduleView() {
        initComponents();
        Controller.Instance().addObserver(this);
        renderSchedule();
    }

    private void renderSchedule() {
        try {
            ArrayList<ScheduleInfo> info = new ArrayList<ScheduleInfo>();
            Staff stf = new Staff();
            stf.setId(Integer.valueOf(Controller.Instance().getLoggedIn().getId()));
            for (Flight f : Controller.Instance().getFlights()) {
                if (f.getPilot().getId() == stf.getId()) {
                    info.add(new ScheduleInfo(f, "Pilot", false));
                    info.add(new ScheduleInfo(f, "Pilot", true));
                }
                if (f.getCopilot().getId() == stf.getId()) {
                    info.add(new ScheduleInfo(f, "Copilot", false));
                    info.add(new ScheduleInfo(f, "Copilot", true));
                }
                if (f.findOtherPersonal(stf)) {
                    info.add(new ScheduleInfo(f, "Attendant", false));
                    info.add(new ScheduleInfo(f, "Attendant", true));
                }
                if (f.findAirmarshalls(stf)){
                    info.add(new ScheduleInfo(f, "Airmarshall", false));
                    info.add(new ScheduleInfo(f, "Airmarshall", true));
                }
            }
            
            if(!info.isEmpty()){
                GenericTableModel<ScheduleInfo> model = new GenericTableModel<ScheduleInfo>(info);
                tblSchedule.setModel(model);
            }else{
                tblSchedule.setVisible(false);
                lblMessage.setText("<html><b> No schedule available for \"" + Controller.Instance().getLoggedIn().getUsername() + "\"</b> </html>");
            }
        } catch (Exception ex) {
            
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSchedule = new javax.swing.JTable();
        lblMessage = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSchedule.setName("tblSchedule"); // NOI18N
        tblSchedule.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tblSchedule);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(flyaway.FlyAWayApp.class).getContext().getResourceMap(ScheduleView.class);
        lblMessage.setText(resourceMap.getString("lblMessage.text")); // NOI18N
        lblMessage.setName("lblMessage"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JTable tblSchedule;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object arg) {
        if (arg instanceof Flight) {
            renderSchedule();
        }
    }
}
