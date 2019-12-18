/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * This is a model to display the movies in a jTable
 * 
 * @author "Daniel Mic"
 */
public class Tabel extends AbstractTableModel{

    private String[] columnName;
    private Object[][] data;
    
    public Tabel(Object[][] data, String[] columnName) {
        this.columnName = columnName;
        this.data = data;
    }
    
    public Class getColumnClass(int column){
        if(column == 7) {
            return Icon.class;
        }
        else {
            return getValueAt(0,column).getClass();
        }
    }
    
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    
    public String getColumnName(int col){
        return this.columnName[col];
    }
    
}
