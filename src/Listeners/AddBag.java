package Listeners;

import Dialogs.AddBagDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddBag implements ActionListener {

    private JFrame mframe;
    private DefaultTableModel tablem;
    private HashMap<String, HashMap<String,String>>  tabledata;
    private JLabel totall;


    public AddBag(JFrame mframe, DefaultTableModel tablem, HashMap<String, HashMap<String,String>>  tabledata, JLabel totall
    ) {
        this.mframe = mframe;
        this.tablem = tablem;
        this.tabledata = tabledata;
        this.totall = totall;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        AddBagDialog abd = new AddBagDialog(mframe, tablem, tabledata, totall);
        abd.setVisible(true);
    }
}
