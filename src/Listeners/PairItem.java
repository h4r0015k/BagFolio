package Listeners;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PairItem implements ItemListener {
    private HashMap<String, ArrayList<String>> pairData;
    private JComboBox base;

    public PairItem(HashMap<String, ArrayList<String>> pairData, JComboBox base) {
        this.pairData = pairData;
        this.base = base;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if( itemEvent.getStateChange() == 2) {
            String selectedPair = ((JComboBox) itemEvent.getSource()).getSelectedItem().toString();
            base.removeAllItems();

            ArrayList<String> tmp = new ArrayList<>(pairData.get(selectedPair));
            Collections.sort(tmp);

            for (String s : tmp)  {
                base.addItem(s);
            }

            base.setEnabled(true);
        }


    }
}
