package socketServer.messages;

import java.time.LocalDate;

public class RealTimePrice {

    private String symbol;

    private long date;

    private double price;

    private double change;

    public RealTimePrice(String symbol, long date, double price, double change) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }
}
