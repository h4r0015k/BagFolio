package Listeners;

import Files.Files;
import Markets.Exchanges.Exchange;
import Markets.FilterPairs;
import Markets.FetchData;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class RefreshPairs implements ActionListener {
    private JComboBox exchanges, qoute;
    private HashMap<String, ArrayList<String>> pairData;
    private JLabel status;

    public RefreshPairs(JComboBox exchanges, JComboBox qoute, HashMap<String, ArrayList<String>> pairData, JLabel status) {
        this.exchanges = exchanges;
        this.pairData = pairData;
        this.qoute = qoute;
        this.status = status;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {

                try {

                    Exchange exchange = (Exchange) exchanges.getSelectedItem();

                    status.setVisible(true);
                    status.setText("Refreshing");

                    String pairs = FetchData.pull(exchange.getPairUrl());

                    if(pairs == null)
                        status.setText("Error");

                    HashMap<String, ArrayList<String>> list = exchange.processPairData(pairs);
                    Files.writeMarketJson(list,exchange.getName());

                    pairData.clear();
                    FilterPairs.filter(new JSONObject(list), qoute, pairData, 1);

                    status.setVisible(false);

                } catch (Exception e) {
                    status.setText("Error");

                }
            }
        }.start();
    }
}
