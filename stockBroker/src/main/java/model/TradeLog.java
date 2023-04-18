package model;

import gRpc.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradeLog {

    private String symbol;

    private double price;



    public TradeLog(String symbol, double price, LocalDate date) {
        this.symbol = symbol;
        this.price = price;
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


    public static void main(String[] args) {

        LocalDate localDate = LocalDate.parse("17/04/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(localDate);
    }

    @Override
    public String toString() {
        return "TradeLog{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
