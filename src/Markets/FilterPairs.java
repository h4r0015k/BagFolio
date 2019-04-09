package Markets;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FilterPairs {

    // Load data into qoute JCombobox

    public static void filter(JSONObject pairs, JComboBox qoute, HashMap<String, ArrayList<String>> pairData, int type) {

        for (Object key : pairs.keySet()) {
            if (!pairData.containsKey(key)) {
                pairData.put((String) key, new ArrayList<>());
            }

            if(type == 1) {
                for (Object value : (ArrayList) pairs.get(key)) {
                    pairData.get(key).add((String) value);
                }
            } else {
                for (Object value : (ArrayList) pairs.get(key)) {
                    pairData.get(key).add((String) value);
                }
            }

        }

        ArrayList<String> sortPairs = new ArrayList<>();

        for (String s : pairData.keySet()) {
            sortPairs.add(s);
        }

        Collections.sort(sortPairs);

        for (int i = 0; i < sortPairs.size(); i++) {
            qoute.addItem(sortPairs.get(i));
        }

        qoute.setEnabled(true);
    }
}
