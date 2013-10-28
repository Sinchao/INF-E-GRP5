/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateChangeAirportView.java
 *
 * Created on 19-jan-2010, 14:13:19
 */
package View;

import Controller.Controller;
import Model.Airport;
import Model.Country;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeroen
 */
public class CreateChangeAirportView extends javax.swing.JInternalFrame implements KeyListener {

    private Airport airport = null;
    private Country country = null;

    /** Creates new form CreateChangeAirportView */
    public CreateChangeAirportView(Airport airport) {
        initComponents();
        this.airport = airport;
        if (airport != null) {
            fillFields();
        }
    }

    private void fillFields() {
        this.txtFieldCity.setText(airport.getCity());
        this.txtFieldCountry.setText(airport.getCountry().getCountry());
        this.txtFieldName.setText(airport.getName());
        this.txtFieldCode.setText(airport.getCode());
        
    }
    
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFieldName = new javax.swing.JTextField();
        txtFieldCountry = new javax.swing.JTextField();
        txtFieldCity = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblErrorMessage = new javax.swing.JLabel();
        txtFieldCode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        listSearchResults = new javax.swing.JScrollPane();
        listSearchResults1 = new javax.swing.JList();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(flyaway.FlyAWayApp.class).getContext().getResourceMap(CreateChangeAirportView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        txtFieldName.setText(resourceMap.getString("txtFieldName.text")); // NOI18N
        txtFieldName.setName("txtFieldName"); // NOI18N

        txtFieldCountry.setText(resourceMap.getString("txtFieldCountry.text")); // NOI18N
        txtFieldCountry.setName("txtFieldCountry"); // NOI18N
        txtFieldCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldCountryActionPerformed(evt);
            }
        });
        txtFieldCountry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFieldCountryFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFieldCountryFocusLost(evt);
            }
        });

        txtFieldCity.setText(resourceMap.getString("txtFieldCity.text")); // NOI18N
        txtFieldCity.setName("txtFieldCity"); // NOI18N

        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblErrorMessage.setText(resourceMap.getString("lblErrorMessage.text")); // NOI18N
        lblErrorMessage.setName("lblErrorMessage"); // NOI18N

        txtFieldCode.setText(resourceMap.getString("txtFieldCode.text")); // NOI18N
        txtFieldCode.setName("txtFieldCode"); // NOI18N

        jLabel4.setLabelFor(txtFieldCode);
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        listSearchResults.setBackground(resourceMap.getColor("listSearchResults.background")); // NOI18N
        listSearchResults.setName("listSearchResults"); // NOI18N

        listSearchResults1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listSearchResults1.setEnabled(false);
        listSearchResults1.setName("listSearchResults1"); // NOI18N
        listSearchResults1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSearchResults1ValueChanged(evt);
            }
        });
        listSearchResults.setViewportView(listSearchResults1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtFieldCountry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtFieldCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtFieldName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtFieldCity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                                .addGap(38, 38, 38))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel)
                                .addGap(144, 144, 144)))
                        .addComponent(listSearchResults, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtFieldCountry, txtFieldName});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtFieldCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnCancel)))
                    .addComponent(listSearchResults, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtFieldCity, txtFieldCountry, txtFieldName});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String name = txtFieldName.getText();
        String city = txtFieldCity.getText();
        String code = txtFieldCode.getText();
       

        String errorMessage = "";
        name = name.trim();
        if (name.isEmpty()) {
            errorMessage += "Please fill in a name\n";
        }
        
        if(country == null){
            errorMessage += "Please select a country";
        }
       

        city = city.trim();
        if (city.isEmpty()) {
            errorMessage += "please fill in a city \n";
        }

        code = code.trim();
        if (code.isEmpty()) {
            errorMessage += "please fill in a airport code \n";
        }

        if (errorMessage.isEmpty()) {
            if (airport == null) {
                 airport = new Airport();
                airport.setCity(city);
                airport.setCountry(country);
                airport.setName(name);
                airport.setCode(code);
                

                if (Controller.Instance().save(airport)) {
                    JOptionPane.showMessageDialog(this, "Airport saved");
                    this.dispose();
                } else {
                    errorMessage = "Unable to save Airport";
                }
            } else {
                airport.setCity(city);
                airport.setCountry(country);
                airport.setName(name);
                airport.setCode(code);
               

                if (Controller.Instance().update(airport)) {
                    JOptionPane.showMessageDialog(this, "Airport saved");
                    this.dispose();
                } else {
                    errorMessage = "Unable to change Airport";
                }
            }
        }

        lblErrorMessage.setText("" + errorMessage);

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtFieldCountryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFieldCountryFocusGained
        txtFieldCountry.setBorder(BorderFactory.createLineBorder(Color.orange));
        listSearchResults1.setEnabled(true);
        listSearchResults1.setListData(Controller.Instance().getCountries().toArray());
    }//GEN-LAST:event_txtFieldCountryFocusGained

    private void txtFieldCountryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFieldCountryFocusLost
        txtFieldCountry.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        listSearchResults1.setEnabled(false);
    }//GEN-LAST:event_txtFieldCountryFocusLost

    private void txtFieldCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldCountryActionPerformed

    private void listSearchResults1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSearchResults1ValueChanged
        
        Country tmpCountry = (Country) listSearchResults1.getSelectedValue();
        
        if (tmpCountry != null) {
            country = tmpCountry;
            txtFieldCountry.setText(country.toString());
            
        }
}//GEN-LAST:event_listSearchResults1ValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JScrollPane listSearchResults;
    private javax.swing.JList listSearchResults1;
    private javax.swing.JTextField txtFieldCity;
    private javax.swing.JTextField txtFieldCode;
    private javax.swing.JTextField txtFieldCountry;
    private javax.swing.JTextField txtFieldName;
    // End of variables declaration//GEN-END:variables

  
}