package Markets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TotalValue {

    // Fetch average coin-USD price
    public static Double getPrice(String coin) {
        final String LINK = "https://chasing-coins.com/api/v1/std/coin/$COIN";

        String data = FetchData.pull(LINK.replace("$COIN", coin));

        if(data != null) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);
                return Double.valueOf(jsonObject.get("price").toString());
            } catch (Exception e) {return 0.0;}
        }

        return 0.0;
    }
}
