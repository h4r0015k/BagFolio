package Listeners;


import Files.Files;
import Markets.ExchangeList;
import Markets.Exchanges.Exchange;
import Markets.FetchData;
import Markets.PairInfo;
import Markets.TotalValue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

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

    public void refresh() {
        new Thread() {

            @Override
            public void run() {
                Double total = 0.0;
                totall.setText("Refreshing...");

                if(tablem.getRowCount() < 1) {
                    totall.setText("0.0");
                    tabledata.get("total").put("total", "0.0");
                    Files.writeDataJson(tabledata);
                    return;
                }

                for(int i = 0; i < tablem.getRowCount(); i++) {

                    Exchange exchange = getExchange(tablem.getValueAt(i,0).toString());
                    Double bought = Double.valueOf(tablem.getValueAt(i, 4).toString());
                    Double amount = Double.valueOf(tablem.getValueAt(i, 2).toString());
                    String ticker = tablem.getValueAt(i,1).toString();
                    String url = exchange.getTickerUrl().replace("$BASE",ticker.split("-")[1]).replace("$QOUTE",ticker.split("-")[0]);
                    String base = ticker.split("-")[1];
                    String qoute = ticker.split("-")[0];

                    Double price = exchange.getTicker(FetchData.pull(url), new PairInfo(base, qoute));
                    String newPrice;

                    if(price != null)
                        newPrice = String.format("%.08f", price);
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


                NumberFormat curFormat = NumberFormat.getCurrencyInstance(new Locale("en","US"));
                String ftotal = curFormat.format(total);

                totall.setText(ftotal);
                tabledata.get("total").put("total", ftotal);
                Files.writeDataJson(tabledata);
            }
        }.start();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        refresh();
    }
}
