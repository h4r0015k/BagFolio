package Files;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Files {

    public static void writeDataJson(HashMap<String, HashMap<String,String>> data) {
        try {
            String userHome = System.getProperty("user.home");
            File file = new File(userHome + "/bagfolio/data.json");

            FileWriter fw = new FileWriter(file);
            fw.write(new JSONObject(data).toJSONString());
            fw.flush();
            fw.close();
        } catch (Exception e) {}
    }

    public static void writeMarketJson(HashMap<String, ArrayList<String>> list, String exchangeName) {

        try {

            String filepath = System.getProperty("user.home") + "/bagfolio/markets/" + exchangeName + ".json";
            File file = new File(filepath);
            if (file.createNewFile()) {
                FileWriter fw = new FileWriter(file);
                fw.write((new JSONObject(list)).toJSONString());
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {}
    }

    public static void loadDataFile(HashMap<String, HashMap<String,String>> tabledata, DefaultTableModel tablem, JLabel totall) {

        String filepath = System.getProperty("user.home") + "/bagfolio/data.json";
        File file = new File(filepath);

        try {
            if (file.exists()) {

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder str = new StringBuilder();
                String tmp;

                while((tmp = br.readLine()) != null) {
                str.append(tmp);
                }
                JSONObject data = (JSONObject) new JSONParser().parse(str.toString());

                for(Object key : data.keySet()) {
                    if(!tabledata.containsKey(key))
                        tabledata.put((String) key, new HashMap<>());
                    JSONObject inner = (JSONObject) data.get(key);

                    for(Object innerKey: inner.keySet())
                         tabledata.get(key).put((String) innerKey, (String) inner.get(innerKey));
                }

                fr.close();

                for(String key: tabledata.keySet()) {

                    if(key.equals("total"))
                        continue;

                    HashMap<String,String> mp = tabledata.get(key);

                    String[] tmpdata = new String[6];
                    tmpdata[0] =  mp.get("exchange");
                    tmpdata[1] =  mp.get("ticker");
                    tmpdata[2] =  mp.get("amount");
                    tmpdata[3] =  mp.get("current");
                    tmpdata[4] =  mp.get("bought");
                    tmpdata[5] =  mp.get("pl");

                    tablem.addRow(tmpdata);
                }

                totall.setText(tabledata.get("total").get("total"));
                tablem.fireTableDataChanged();

            } else {
                file.createNewFile();
            }

        } catch (Exception e) {}
    }

    public static void setupMainFolder() {
        String userHome = System.getProperty("user.home");
        File folioFolder = new File(userHome+"/bagfolio/markets");

        if(!folioFolder.exists()) {
            folioFolder.mkdirs();
        }
    }

}
