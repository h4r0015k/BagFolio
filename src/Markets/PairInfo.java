package Markets;

public class PairInfo {
    private String base;
    private String qoute;

    public PairInfo(String base, String qoute) {
        this.base = base;
        this.qoute = qoute;
    }

    public String getBase() {
        return base;
    }

    public String getQoute() {
        return qoute;
    }
}
