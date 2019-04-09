package Listeners;

import Markets.Exchanges.Exchange;
import Markets.FilterPairs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class ExchangeItem implements ActionListener {

    private JComboBox qoute,base;
    private HashMap<String, ArrayList<String>> pairData;

    public ExchangeItem(HashMap<String, ArrayList<String>> pairData, JComboBox qoute, JComboBox base) {
        this.pairData = pairData;
        this.qoute = qoute;
        this.base = base;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new  Thread() {
            public void run() {

                // Load Pair data into PairData from local file if it exists.

                Exchange exchange = (Exchange) ((JComboBox) actionEvent.getSource()).getSelectedItem();
                String filepath = System.getProperty("user.home") + "/bagfolio/markets/" + exchange + ".json";
                File marketFile = new File(filepath);

                qoute.removeAllItems();
                base.removeAllItems();

                try {
                    if (marketFile.exists()) {

                        FileReader fr = new FileReader(filepath);
                        BufferedReader br = new BufferedReader(fr);
                        StringBuilder str = new StringBuilder();
                        String tmp;

                        while ((tmp = br.readLine()) != null) {
                            str.append(tmp);
                        }

                        fr.close();
                        pairData.clear();

                        JSONObject pairs = (JSONObject) (new JSONParser().parse(str.toString()));
                        FilterPairs.filter(pairs, qoute, pairData, 0);

                    }
                }
                    catch (Exception e) {e.getMessage();}

            }

        }.start();
    }

}
