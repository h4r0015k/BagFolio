package Markets.Exchanges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class Binance extends Exchange {

    final String NAME = "Binance";
    final String URL_PAIR = "https://api.binance.com/api/v3/ticker/price";
    final String TICKER_URL = "https://api.binance.com/api/v3/ticker/price?symbol=$QOUTE$BASE";

    public Binance() {
        super("Binance");
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

            JSONArray jsonArray = (JSONArray) new JSONParser().parse(data);

            ArrayList<String> BaseList = new ArrayList<>();
            BaseList.add("BTC");
            BaseList.add("ETH");
            BaseList.add("XRP");
            BaseList.add("BNB");
            BaseList.add("USDT");
            BaseList.add("PAX");
            BaseList.add("TUSD");
            BaseList.add("USDC");



            for (Object obj: jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String pair = (String) jsonObject.get("symbol");

                for(String base: BaseList) {
                    if(pair.endsWith(base)) {
                        String qoute = pair.replace(base,"");
                        if(!coinlist.containsKey(qoute))
                            coinlist.put(qoute, new ArrayList<>());

                        coinlist.get(qoute).add(base);
                    }
                }
            }

        } catch (Exception e) { return null;}
        return coinlist;
    }

    @Override
    public Double getTicker(String data) {
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            return  Double.valueOf(jsonObject.get("price").toString());
        } catch (Exception e) {}

        return null;
    }

}
