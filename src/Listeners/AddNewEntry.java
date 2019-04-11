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

public class AddNewEntry implements ActionListener {

    private JComboBox exchange ,qoute, base;
    private JTextField amount, bprice;
    private DefaultTableModel tablem;
    private JLabel totall;
    private JDialog addBag;
    private HashMap<String, HashMap<String,String>>  tabledata;


    public AddNewEntry(JComboBox exchange, JComboBox qoute, JComboBox base, JTextField amount, JTextField bprice, DefaultTableModel tablem, JDialog addBag, HashMap<String, HashMap<String,String>>  tabledata, JLabel totall
    ) {
        this.exchange = exchange;
        this.qoute = qoute;
        this.base = base;
        this.amount = amount;
        this.bprice = bprice;
        this.tablem = tablem;
        this.addBag = addBag;
        this.tabledata = tabledata;
        this.totall = totall;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

            new Thread(new loadData()).start();
            addBag.dispose();
    }

    public static double calPL(double current, double bought, double amount) {
        double spent = amount * bought;
        double now = amount * current;
        double percent = ((now - spent) / spent) * 100;

        return percent;
    }


    private class loadData implements Runnable {

        @Override
        public void run() {
            try {
                Double total = Double.valueOf(totall.getText().replace(" USD",""));

                totall.setText("Adding bag...");
                String baseCur = base.getSelectedItem().toString();
                String qouteCur = qoute.getSelectedItem().toString();
                String ticker = qouteCur + "-" + baseCur;

                String amountN = amount.getText().isEmpty() ? "0" : amount.getText();
                String bpriceN =  bprice.getText().isEmpty() ? "0" : bprice.getText();

                Exchange ex = (Exchange) exchange.getSelectedItem();

                String tickerUrl = ex.getTickerUrl().replace("$BASE", baseCur).replace("$QOUTE", qouteCur);

                String data = FetchData.pull(tickerUrl);
                String curPrice;

                if(data != null)
                     curPrice = String.format("%.8f",ex.getTicker(data));
                else
                    curPrice = "0";

                String pl = String.format("%.2f", calPL(Double.valueOf(curPrice), Double.valueOf(bpriceN) ,Double.valueOf(amountN))) + "%";

                String[] tmp = {ex.toString() ,ticker, amountN, curPrice, bpriceN, pl};

                tablem.addRow(tmp);
                tablem.fireTableDataChanged();

                ArrayList<Exchange> exList = new ExchangeList().getExlist();
                int last = tabledata.size() + 1;
                tabledata.put(String.valueOf(last), new HashMap<>());
                HashMap<String,String> current = tabledata.get(String.valueOf(last));

                current.put("exchange", ex.toString());
                current.put("ticker", ticker);
                current.put("current", curPrice);
                current.put("amount", amount.getText());
                current.put("bought", bprice.getText());
                current.put("pl", pl);

                if(!tabledata.containsKey("total"))
                    tabledata.put("total", new HashMap<>());

                Double totalValue = 0.0;

                if(!baseCur.contains("USD"))
                    totalValue = total + ((Double.valueOf(curPrice) * Double.valueOf(amountN)) * TotalValue.getPrice(baseCur));
                else
                    totalValue = total + (Double.valueOf(curPrice) * Double.valueOf(amountN));

                String ftotal = String.format("%.2f", totalValue) + " USD";

                tabledata.get("total").put("total", ftotal);
                totall.setText(ftotal);

                Files.writeDataJson(tabledata);

            } catch (Exception e) {e.getMessage();}

        }
    }
}
