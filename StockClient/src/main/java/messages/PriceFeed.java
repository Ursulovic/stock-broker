package messages;

import java.util.ArrayList;
import java.util.List;

public class PriceFeed {

    private List<RealTimePrice> realTimePrices;

    public PriceFeed() {
        this.realTimePrices = new ArrayList<>();
    }


    public List<RealTimePrice> getRealTimePrices() {
        return realTimePrices;
    }

    public void setRealTimePrices(List<RealTimePrice> realTimePrices) {
        this.realTimePrices = realTimePrices;
    }

    @Override
    public String toString() {
        return "PriceFeed{" +
                "realTimePrices=" + realTimePrices +
                '}';
    }
}
