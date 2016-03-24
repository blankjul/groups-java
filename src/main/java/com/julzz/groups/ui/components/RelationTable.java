/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups.ui.components;

import com.julzz.groups.model.Member;
import com.julzz.groups.ui.Storage;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RelationTable extends JTable {

    

    public RelationTable() {
        super();   
    }
    

    
    public void update(Member m) {
        
        DefaultTableModel model = new DefaultTableModel();
        setModel(model);
        
        model.setColumnIdentifiers(new Object[] {"Beziehung", "Name"});
        
        TableColumn cRelation = getColumn("Beziehung");
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("");
        comboBox.addItem("+");
        comboBox.addItem("-");
        cRelation.setCellEditor(new DefaultCellEditor(comboBox));
        
        
        for(Member other : Storage.desc.getMembers()) {
            if (m.equals(other)) continue;
            else if (m.getPreferences().contains(other)) model.addRow(new Object[] {"+", other.getName()});
            else if (m.getRejections().contains(other)) model.addRow(new Object[] {"-", other.getName()});
            else model.addRow(new Object[] {"", other.getName()});
        }

        model.fireTableDataChanged();
        
    }

}
