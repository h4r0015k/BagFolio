package Markets.Exchanges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class Bittrex extends Exchange {

    final String NAME = "Bittrex";
    final String URL_PAIR = "https://api.bittrex.com/api/v1.1/public/getmarkets";
    final String TICKER_URL = "https://api.bittrex.com/api/v1.1/public/getticker?market=$BASE-$QOUTE";

    public Bittrex() {
        super("Bittrex");
    }

    @Override
    public String getPairUrl() {
        return URL_PAIR;
    }

    @Override
    public String getTickerUrl() {
        return TICKER_URL;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public HashMap<String, ArrayList<String>>  processPairData(String data) {

        HashMap<String, ArrayList<String>> coinlist = new HashMap<>();

        try {

            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            JSONArray list = (JSONArray) jsonObject.get("result");

            for (int i = 0; i < list.size(); i++) {
                JSONObject next = (JSONObject) list.get(i);
                String base = next.get("BaseCurrency").toString();
                String market = next.get("MarketCurrency").toString();

                if (!coinlist.containsKey(market)) {
                    coinlist.put(market, new ArrayList<>());

                }

                coinlist.get(market).add(base);
            }

        } catch (Exception e) { return null;}

        return coinlist;
    }

    @Override
    public Double getTicker(String data) {
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));

            JSONObject result = (JSONObject) jsonObject.get("result");
            return  (Double) result.get("Last");
        } catch (Exception e) {}

        return null;

    }

}
