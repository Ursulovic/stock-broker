package messages;

import java.time.LocalDate;

public class RealTimePrice {

    private String symbol;

    private LocalDate date;

    private int hour;

    private double price;

    private double change;

    public RealTimePrice(String symbol, LocalDate date, int hour, double price, double change) {
        this.symbol = symbol;
        this.date = date;
        this.hour = hour;
        this.price = price;
        this.change = change;
    }

    public RealTimePrice() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "RealTimePrice{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", hour=" + hour +
                ", price=" + price +
                ", change=" + change +
                '}';
    }
}
