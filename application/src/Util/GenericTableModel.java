/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.beans.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author user
 */
public class GenericTableModel<T> extends AbstractTableModel {

    private ArrayList<T> rows;

    public GenericTableModel(ArrayList<T> rows) {
        this.rows = rows;
    }

    public int getColumnCount() {
        int columnCount = 0;
        if (rows.size() != 0) {
            try {
                Class c = rows.get(0).getClass();
                BeanInfo testBeanInfo = Introspector.getBeanInfo(c, Object.class);
                PropertyDescriptor[] props = testBeanInfo.getPropertyDescriptors();
                columnCount = props.length;
            } catch (IntrospectionException ex) {
                Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return columnCount;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (rows.size() != 0) {
            if (getValueAt(0, column) != null) {
                return getValueAt(0, column).getClass();
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (rows.size() != 0) {
            try {
                Class c = rows.get(0).getClass();
                BeanInfo testBeanInfo = Introspector.getBeanInfo(c, Object.class);
                PropertyDescriptor[] props = testBeanInfo.getPropertyDescriptors();
                return testBeanInfo.getPropertyDescriptors()[column].getName();
            } catch (IntrospectionException ex) {
                Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "no column";
    }

    public static void removeColumn(JTable table, String columnNames) {
        for (TableColumn col : Collections.list(table.getColumnModel().getColumns())) {
            if (col.getHeaderValue().toString().toLowerCase().equals(columnNames.toLowerCase())) {
                table.removeColumn(col);
            }
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public T getRow(int row) {
        return rows.get(row);
    }

    public void removeRow(T row) {
        rows.remove(row);
        this.fireTableDataChanged();
    }

    public void addRow(T row) {
        rows.add(row);
        this.fireTableDataChanged();
    }

    public Object getValueAt(int row, int column) {
        try {
            Class c = rows.get(row).getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(c, Object.class);
            PropertyDescriptor pd = beanInfo.getPropertyDescriptors()[column];
            Method m = pd.getReadMethod();
            if (m != null) {
                Object objVal = m.invoke(rows.get(row), null);
                return (objVal != null) ? objVal : "";
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IntrospectionException ex) {
            Logger.getLogger(GenericTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
