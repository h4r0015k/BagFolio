package Markets;

import Markets.Exchanges.*;

import java.util.ArrayList;

public class ExchangeList {

    private ArrayList<Exchange> exlist;

    public ExchangeList() {
        exlist = new ArrayList<>();
        exlist.add(new Bittrex());
        exlist.add(new Binance());
    }

    public ArrayList<Exchange> getExlist() {
        return exlist;
    }
}
