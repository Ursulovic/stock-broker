package gRpcServer.model;

import gRpc.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradeLog {

    private String symbol;

    private double price;





    public TradeLog(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public TradeLog() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "TradeLog{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
