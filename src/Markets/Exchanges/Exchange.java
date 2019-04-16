package Markets.Exchanges;

import Markets.PairInfo;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Exchange {

     private String name;

     public Exchange(String name) {
          this.name = name;
     }

     @Override
     public String toString() {
          return name;
     }

     abstract public String getPairUrl();
     abstract public String getName();
     abstract public String getTickerUrl();
     abstract public Double getTicker(String data, PairInfo pair);
     abstract public HashMap<String, ArrayList<String>> processPairData(String data);

}
