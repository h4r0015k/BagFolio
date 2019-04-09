package Dialogs;

import Buttons.OptionButton;
import Listeners.AddNewEntry;
import Listeners.ExchangeItem;
import Listeners.PairItem;
import Listeners.RefreshPairs;
import Markets.ExchangeList;
import Markets.Exchanges.Exchange;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AddBagDialog extends JDialog {

    private JFrame abframe;
    private DefaultTableModel tablem;
    private JDialog abdialog;
    private HashMap<String, ArrayList<String>> pairData = new HashMap<>();
    private HashMap<String, HashMap<String,String>>  tabledata;

    public AddBagDialog(JFrame abframe, DefaultTableModel tablem, HashMap<String, HashMap<String,String>>  tabledata
    ) {
        super(abframe, "New bag");
        super.setSize(new Dimension(400,300));
        super.setResizable(false);
        this.tablem = tablem;
        this.tabledata = tabledata;
        addComponents(super.getContentPane());
    }


    private JComboBox exchangeCombobox() {

        JComboBox exchangeBox = new JComboBox();
        ArrayList<Exchange> list = new ExchangeList().getExlist();

        for (Exchange e: list) {
            exchangeBox.addItem(e);
        }

        return exchangeBox;
    }

    private void addComponents(Container container) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);

        GridBagLayout gl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setLayout(gl);

        JComboBox qoute = new JComboBox();
        qoute.setEnabled(false);

        JLabel qouteLabel = new JLabel("Qoute: ");
        qouteLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        qouteLabel.setForeground(Color.white);

        JLabel baseLabel = new JLabel("Base: ");
        baseLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        baseLabel.setForeground(Color.white);


        JComboBox base = new JComboBox();
        base.setEnabled(false);

        qoute.addItemListener(new PairItem(pairData, base));

        JLabel exchangeLabel = new JLabel("Exchange: ");
        exchangeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        exchangeLabel.setForeground(Color.white);

        JComboBox exchangeBox = exchangeCombobox();
        exchangeBox.setSelectedIndex(-1);
        exchangeBox.setMaximumSize(new Dimension(10,10));
        exchangeBox.addActionListener(new ExchangeItem(pairData, qoute, base));


        JLabel status = new JLabel();
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        status.setForeground(Color.orange);
        status.setVisible(false);


        OptionButton refreshPairs = new OptionButton("Refresh Pairs");
        refreshPairs.addActionListener(new RefreshPairs(exchangeBox, qoute, pairData, status));

        JTextField amount = new JTextField();
        amount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        JLabel amountLabel = new JLabel("Amount: ");
        amountLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        amountLabel.setForeground(Color.white);

        JTextField bprice = new JTextField();
        bprice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        JLabel bpriceLabel = new JLabel("Bought Price: ");
        bpriceLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        bpriceLabel.setForeground(Color.white);

        OptionButton add = new OptionButton("Add Bag");
        add.addActionListener(new AddNewEntry(exchangeBox, qoute, base, amount, bprice, tablem, this, tabledata));


        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0 , 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(exchangeLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(exchangeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(status, gbc);

        gbc.gridx = 1;
        mainPanel.add(refreshPairs, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(qouteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(qoute, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(baseLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(base, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(amount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(bpriceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(bprice, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(add, gbc);


        container.add(mainPanel);
    }


}
