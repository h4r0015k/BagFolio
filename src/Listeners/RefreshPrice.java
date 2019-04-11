package Listeners;


import Files.Files;
import Markets.ExchangeList;
import Markets.Exchanges.Exchange;
import Markets.FetchData;
import Markets.TotalValue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class RefreshPrice implements ActionListener {

    private DefaultTableModel tablem;
    private HashMap<String, HashMap<String,String>> tabledata;
    private JLabel totall;


    public  RefreshPrice(DefaultTableModel tablem, HashMap<String, HashMap<String,String>> tabledata, JLabel totall) {
        this.tablem = tablem;
        this.tabledata = tabledata;
        this.totall = totall;
    }


    private Exchange getExchange(String name) {
        ArrayList<Exchange> exList = new ExchangeList().getExlist();

        for(Exchange ex: exList) {
            if(ex.getName().equals(name))
                return ex;
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        new Thread() {

            @Override
            public void run() {

                if(tablem.getRowCount() < 1)
                    return;

                Double total = 0.0;
                totall.setText("Refreshing...");

                for(int i = 0; i < tablem.getRowCount(); i++) {

                    Exchange exchange = getExchange(tablem.getValueAt(i,0).toString());
                    Double bought = Double.valueOf(tablem.getValueAt(i, 4).toString());
                    Double amount = Double.valueOf(tablem.getValueAt(i, 2).toString());
                    String ticker = tablem.getValueAt(i,1).toString();
                    String url = exchange.getTickerUrl().replace("$BASE",ticker.split("-")[1]).replace("$QOUTE",ticker.split("-")[0]);
                    String base = ticker.split("-")[1];

                    Double price = exchange.getTicker(FetchData.pull(url));
                    String newPrice;

                    if(price != null)
                         newPrice = String.format("%.08f", exchange.getTicker(FetchData.pull(url)));
                    else
                         newPrice = "0";


                    tablem.setValueAt(newPrice, i,3);

                    if(!base.contains("USD")) {
                        total += (amount * Double.valueOf(newPrice)) * TotalValue.getPrice(base);
                    }

                    String pl = String.format("%.2f", AddNewEntry.calPL(Double.valueOf(newPrice),bought,amount)) + "%";

                    tablem.setValueAt(pl ,i,5);
                    tabledata.get(String.valueOf(i+1)).put("current", newPrice);
                    tabledata.get(String.valueOf(i+1)).put("pl", pl);
                    tablem.fireTableDataChanged();
                }



                String ftotal = String.format("%.2f",total) + " USD";

                totall.setText(ftotal);
                tabledata.get("total").put("total", ftotal);
                Files.writeDataJson(tabledata);
                }
        }.start();
    }
}
