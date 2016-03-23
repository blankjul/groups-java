package com.julzz.groups.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Util {

    public static String join(Collection<String> c, String delemiter) {
        StringBuilder builder = new StringBuilder();
        for (String str : c) {
            builder.append(str).append(delemiter);
        }
        return builder.toString();
    }

    public static List<String> getSelected(JTable tbl) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        List<String> group = new ArrayList<>();
        for (int idx : tbl.getSelectedRows()) {
            final String name = (String) model.getValueAt(idx, 0);
            group.add(name);
        }
        return group;
    }
    
    public static List<String> getAllFromTable(JTable tbl, int col) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        List<String> group = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            final String name = (String) model.getValueAt(i, col);
            group.add(name);
        }
        return group;
    }

}
