package Markets.Exchanges;

import Markets.PairInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class Kucoin extends Exchange {

    final String NAME = "Kucoin";
    final String URL_PAIR = "https://api.kucoin.com/api/v1/market/allTickers";
    final String TICKER_URL = "https://api.kucoin.com/api/v1/market/orderbook/level1?symbol=$QOUTE-$BASE";

    public Kucoin() {
        super("Kucoin");
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
    public Double getTicker(String data, PairInfo pair) {
        try {

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            JSONObject tickerData = (JSONObject) jsonObject.get("data");
            return Double.valueOf(tickerData.get("price").toString());

        } catch (Exception e) {return null;}
    }

    @Override
    public HashMap<String, ArrayList<String>> processPairData(String data) {
        HashMap<String, ArrayList<String>> pairs = new HashMap<>();

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
            JSONObject pdata = (JSONObject) jsonObject.get("data");
            JSONArray list = (JSONArray) pdata.get("ticker");

            for(Object pair: list) {
                JSONObject details = (JSONObject) pair;
                String symbol = (String) details.get("symbol");
                String base = symbol.split("-")[1];
                String qoute = symbol.split("-")[0];

                if(!pairs.containsKey(qoute)) {
                    pairs.put(qoute, new ArrayList<>());
                }

                pairs.get(qoute).add(base);
            }

        } catch(Exception e) {return null;}

        return pairs;
    }
}
