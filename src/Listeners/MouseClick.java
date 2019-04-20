package Listeners;


import Files.Files;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;


public class MouseClick extends MouseAdapter {

    private DefaultTableModel dtm;
    private JLabel total;
    private HashMap<String, HashMap<String,String >> tabledata;

    public MouseClick(DefaultTableModel dtm, HashMap<String, HashMap<String,String >> tabledata, JLabel total) {
        this.dtm = dtm;
        this.tabledata = tabledata;
        this.total = total;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        JTable tmp = (JTable) mouseEvent.getSource();

        if(mouseEvent.getClickCount() == 2 && tmp.getRowCount() > 0) {

            int row = tmp.rowAtPoint(mouseEvent.getPoint());

            tabledata.remove(String.valueOf(row + 1));

            // Alter keys in tabledata according to the one removed.
            for(String key: tabledata.keySet()) {
                if(!key.equals("total")) {
                    int keyV = Integer.parseInt(key);
                    if (keyV >= row+1) {
                        HashMap<String,String> value = tabledata.get(key);
                        tabledata.remove(key);
                        tabledata.put(String.valueOf(keyV -1), value);
                    }
                }
            }

            dtm.removeRow(row);
            dtm.fireTableDataChanged();
            Files.writeDataJson(tabledata);

            new RefreshPrice(dtm, tabledata, total).refresh();
        }
    }


}
