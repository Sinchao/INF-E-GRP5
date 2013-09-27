/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.awt.Component;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jeroen
 */
public class CustomTableCellRenderer extends JLabel implements TableCellRenderer {
        private int minHeight = 1;
        private int currHeight = 1;
    
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        /* If what we're displaying isn't an array of values we
        return the normal renderer*/
        
            if (value.getClass().isArray()) {           

                final Object[] passed = (Object[]) value;

                //Calculate the height for this row.
                minHeight = table.getRowHeight();


                if(passed.length * minHeight > currHeight)
                    currHeight = passed.length * minHeight;

                table.setRowHeight(row, currHeight);

                /* We create the table that will hold the multivalue
                 *fields and that will be embedded in the main table */
                return new JTable(
                        new AbstractTableModel() {

                            public int getColumnCount() {
                                return 1;
                            }

                            public int getRowCount() {
                                return passed.length;
                            }

                            public Object getValueAt(int rowIndex, int columnIndex) {
                                return passed[rowIndex];
                            }

                    @Override
                            public boolean isCellEditable(int row, int col) {
                                return false;
                            }
                        });
            
        }
            
        if(value instanceof Collection){        

            Collection al = (Collection)value;

            final Object[] passed = al.toArray();

            //Calculate the height for this row.
            minHeight = table.getRowHeight();

            if(passed.length * minHeight > currHeight)
                currHeight = passed.length * minHeight;

            table.setRowHeight(row, currHeight);

            /* We create the table that will hold the multivalue
             *fields and that will be embedded in the main table */
            return new JTable(
                    new AbstractTableModel() {

                        public int getColumnCount() {
                            return 1;
                        }

                        public int getRowCount() {
                            return passed.length;
                        }

                        public Object getValueAt(int rowIndex, int columnIndex) {
                            return passed[rowIndex];
                        }

                @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    });
        }
        
        return table.getDefaultRenderer(
                    value.getClass()).getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
    }

}
