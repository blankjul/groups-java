/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.julzz.groups.ui.components;

import com.julzz.groups.model.Member;
import com.julzz.groups.ui.Storage;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NameTable extends JTable {

    

    public NameTable() {
        
        super();
 
       update(); 
       

    }
    
    public Member get(int idx) {
        return (Member) getValueAt(idx, 0);
    }
    
    public Set<Member> getSelected() {
        Set<Member> result = new HashSet<>();
        DefaultTableModel model = (DefaultTableModel) getModel();
        for (int idx : getSelectedRows()) {
            final Member m = (Member) model.getValueAt(idx, 0);
            result.add(m);
        }
        return result;
    }
    
    public void update() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        setModel(model);
        
        model.setColumnIdentifiers(new Object[] {"Name"});

        for(Member m : Storage.desc.getMembers()) {
            model.addRow(new Object[] {m});
        }

        model.fireTableDataChanged();
        
    }

}
