/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StaffView.java
 *
 * Created on 19-jan-2010, 10:19:19
 */
package View;

import Util.GenericTableModel;
import Controller.Controller;
import Model.PersonalType;
import Model.Staff;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeroen
 */
public class StaffView extends javax.swing.JInternalFrame implements Observer {

    /**
     * Creates new form StaffView
     */
    public StaffView() {
        initComponents();
        Controller.Instance().addObserver(this);
        fillTable(Controller.Instance().getStaff());
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Staff) {
            fillTable(Controller.Instance().getStaff());
        }
    }

    private void fillTable(ArrayList<Staff> rows) {
        tblPersonal.setModel(new GenericTableModel<Staff>(rows));
        GenericTableModel.removeColumn(tblPersonal, "password");
        GenericTableModel.removeColumn(tblPersonal, "rank");
        GenericTableModel.removeColumn(tblPersonal, "id");


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSearch = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnChange = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        txtFieldSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonal = new javax.swing.JTable();
        lblErrorMessage = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(flyaway.FlyAWayApp.class).getContext().getResourceMap(StaffView.class);
        btnSearch.setText(resourceMap.getString("btnSearch.text")); // NOI18N
        btnSearch.setName("btnSearch"); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnCreate.setText(resourceMap.getString("btnCreate.text")); // NOI18N
        btnCreate.setName("btnCreate"); // NOI18N
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnChange.setText(resourceMap.getString("btnChange.text")); // NOI18N
        btnChange.setName("btnChange"); // NOI18N
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        btnRemove.setText(resourceMap.getString("btnRemove.text")); // NOI18N
        btnRemove.setName("btnRemove"); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        txtFieldSearch.setName("txtFieldSearch"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPersonal.setName("tblPersonal"); // NOI18N
        jScrollPane1.setViewportView(tblPersonal);

        lblErrorMessage.setName("lblErrorMessage"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
                    .addComponent(lblErrorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(txtFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnChange, btnCreate, btnRemove, btnSearch});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        CreateChangeStaffView ccsv = new CreateChangeStaffView(null);
        flyaway.FlyAWayApp app = (flyaway.FlyAWayApp) flyaway.FlyAWayApp.getApplication();
        app.getFlyAwayView().addFrame(ccsv);
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        int selectedIndex = tblPersonal.getSelectedRow();

        if (selectedIndex >= 0) {
            Staff s = ((GenericTableModel<Staff>) tblPersonal.getModel()).getRow(selectedIndex);
            CreateChangeStaffView ccsv = new CreateChangeStaffView(s);
            flyaway.FlyAWayApp app = (flyaway.FlyAWayApp) flyaway.FlyAWayApp.getApplication();
            app.getFlyAwayView().addFrame(ccsv);
        } else {
            lblErrorMessage.setText("Please select a row first");
        }
    }//GEN-LAST:event_btnChangeActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int selectedIndex = tblPersonal.getSelectedRow();
        if (selectedIndex >= 0) {
            Staff s = ((GenericTableModel<Staff>) tblPersonal.getModel()).getRow(selectedIndex);
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove staff \n" + s.getName() + "?", "Remove staff", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                   if(Controller.Instance().removeStaff(s)){
                      ((GenericTableModel<Staff>) tblPersonal.getModel()).removeRow(s);
                       JOptionPane.showMessageDialog(this, "Staff \"" + s.getName() + "\" deleted");
                
                   } else {
                      lblErrorMessage.setText("Please delete this staff from all flights first.");
                   }  
                }


        } else {
            lblErrorMessage.setText("Please select a row first");
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchString = txtFieldSearch.getText();
        ArrayList<Staff> foundStaff = new ArrayList<Staff>();

        if (searchString.isEmpty()) {
            foundStaff = Controller.Instance().getStaff();
        } else {
            int searchId = -1;
            try {
                searchId = Integer.parseInt(searchString);
            } catch (NumberFormatException nfe) {
                Logger.getLogger(GenericTableModel.class.getName()).log(Level.FINER, null, nfe.getMessage());
            }

            if (searchId != -1) {
                foundStaff.addAll(Controller.Instance().searchStaff(searchId));
            }
            PersonalType st = null;
            try {
                st = (PersonalType.valueOf(searchString));
            } catch (IllegalArgumentException iae) {
                Logger.getLogger(GenericTableModel.class.getName()).log(Level.FINER, null, iae.getMessage());
            }
            if (st != null) {
                foundStaff.addAll(Controller.Instance().searchStaff(st));
            }
            foundStaff.addAll(Controller.Instance().searchStaff(searchString));
        }
        fillTable(foundStaff);

    }//GEN-LAST:event_btnSearchActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JTable tblPersonal;
    private javax.swing.JTextField txtFieldSearch;
    // End of variables declaration//GEN-END:variables
}
