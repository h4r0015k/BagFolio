package Markets.Exchanges;

import Markets.PairInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class Poloniex extends Exchange {

    final String NAME = "Poloniex";
    final String URL_PAIR = "https://poloniex.com/public?command=returnTicker";
    final String TICKER_URL = "https://poloniex.com/public?command=returnTicker";

    public Poloniex() {
        super("Poloniex");
    }

    @Override
    public String getPairUrl() {
        return URL_PAIR;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTickerUrl() {
        return TICKER_URL;
    }

    @Override
    public Double getTicker(String data, PairInfo pairInfo) {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            JSONObject value =  (JSONObject) jsonObject.get(pairInfo.getBase()+"_"+pairInfo.getQoute());
            return Double.valueOf(value.get("last").toString());
        } catch (Exception e){return 0.0;}
    }

    @Override
    public HashMap<String, ArrayList<String>> processPairData(String data) {

        HashMap<String, ArrayList<String>> list = new HashMap<>();

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);

            jsonObject.keySet().stream().forEach((current) -> {
                String[] pair = ((String) current).split("_");
                String base = pair[0];
                String qoute = pair[1];

                if(!list.containsKey(qoute)) {
                    list.put(qoute, new ArrayList<>());
                }

                list.get(qoute).add(base);

            });

            return list;
        } catch (Exception e) {return null;}
    }
}
