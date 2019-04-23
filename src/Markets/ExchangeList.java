package Markets;

import Markets.Exchanges.*;

import java.util.ArrayList;

public class ExchangeList {

    private ArrayList<Exchange> exlist;

    public ExchangeList() {
        exlist = new ArrayList<>();
        exlist.add(new Bittrex());
        exlist.add(new Binance());
        exlist.add(new Poloniex());
        exlist.add(new Kucoin());

    }

    public ArrayList<Exchange> getExlist() {
        return exlist;
    }
}
